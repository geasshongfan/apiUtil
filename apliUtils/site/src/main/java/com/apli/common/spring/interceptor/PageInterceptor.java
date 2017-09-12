package com.apli.common.spring.interceptor;

import com.apli.common.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Intercepts({ @Signature(method = "query", type = Executor.class, args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class PageInterceptor implements Interceptor {
	private Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

	private final String PAGE_BEAN_NAME = "pageInfo";
	private int maxPageSize = 1000 ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.
	 * Invocation)
	 */
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Object paremeterObject = boundSql.getParameterObject();
		Page pageInfo = findPageInfo(paremeterObject);
		String sql = boundSql.getSql() ;
		//*************如果用户设置了pageinfo对象则做分页**************************
		if( pageInfo == null || sql.toLowerCase().indexOf("limit") != -1  || sql.toLowerCase().indexOf("from") == -1 ){
			return invocation.proceed();
		}
		//************判断用户是否需要分页总条数***********************************
		if( pageInfo.isPage() ){
			pageInfo.setAllRows(findAllRows(mappedStatement, boundSql, boundSql.getSql()));
		}
		//************构建limit查询******************************************
		invocation.getArgs()[0] = buildPageLimitMappedStatement( pageInfo, mappedStatement, boundSql );
		return invocation.proceed();
	}

	
	class BoundSqlSqlSource implements SqlSource {
		private BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		@Override
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	private MappedStatement buildPageLimitMappedStatement(Page pageInfo, MappedStatement mappedStatement,
			BoundSql boundSql) {
		String sql = boundSql.getSql();
		int limit = pageInfo.isPage() ? pageInfo.getPageSize() : pageInfo.limit() ;
		sql += " limit " + pageInfo.getRowOffset() + "," + limit ;
		BoundSql fatchSql = buildBoundSQL(mappedStatement, boundSql, sql);
		return buildMappedStatement(mappedStatement, new BoundSqlSqlSource(fatchSql));
	}

	private MappedStatement buildMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.keyProperty(implode(ms.getKeyProperties(), ","));
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	private String implode(String[] ary, String pattern) {
		if (ary == null || ary.length == 0)
			return null;
		String temp = "";
		for (String item : ary) {
			if (StringUtils.isEmpty(item))
				continue;
			temp += item + pattern;
		}
		return temp.substring(0, temp.length() - 1);
	}

	/**
	 * build count sql ...
	 * 
	 * @param boundSql
	 * @return
	 */
	private String buildCountSql(String boundSql) {
		StringBuilder sqlBuilder = new StringBuilder();
		String tempSql = boundSql.toLowerCase().trim() ;
		int grpbyPos = tempSql.indexOf("group by");
		if(grpbyPos != -1){
		    sqlBuilder.append("select count(DISTINCT ");
		    String constGrpStr = "group by";
		    Matcher m = Pattern.compile("group by.*").matcher(tempSql);
		    String grpField = null;
		    if(m.find()){
		        String capturedStr = m.group();
		        grpField = m.group().substring(capturedStr.indexOf(constGrpStr)+constGrpStr.length());
		    }
		    sqlBuilder.append(grpField)
		    .append(") ");
		    tempSql = tempSql.replaceFirst("group by.*", "");//移除group by
		}else{
		    sqlBuilder.append("select count( 1 ) ");
		}
		if(tempSql.indexOf(" from ")>0)
		{
			sqlBuilder.append(tempSql.substring(tempSql.indexOf(" from ") ) );
		}else if(tempSql.indexOf("from ")>0){
			sqlBuilder.append(tempSql.substring(tempSql.indexOf("from ") ) );
		}else{
			sqlBuilder.append(tempSql.substring(tempSql.indexOf("from") ) );
		}
		//sqlBuilder.append(") as alias ");
		return sqlBuilder.toString();
	}

	/**
	 * find row count ...
	 * 
	 * @param ms
	 * @param boundSql
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	private int findAllRows(MappedStatement ms, BoundSql boundSql, String sql) throws SQLException {
		String countSql = buildCountSql(sql);

		BoundSql boundCountSql = buildBoundSQL(ms, boundSql, countSql);
		Connection connection = ms.getConfiguration().getEnvironment().getDataSource().getConnection() ;
		
		PreparedStatement pstmt = connection.prepareStatement(countSql);
		DefaultParameterHandler parameterHandler = new DefaultParameterHandler(ms, boundSql.getParameterObject(),
				boundCountSql);
		parameterHandler.setParameters(pstmt);
		ResultSet rs = null ;
		int tempCount = 0;
		try{
			rs = pstmt.executeQuery();
			if (rs.next()) {
				tempCount = rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace(); 
		}
		finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if( connection != null ){
				try{
					connection.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return tempCount;
	}

	private BoundSql buildBoundSQL(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
				boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	private Page findPageInfo(Object parameterObject) {
		if (parameterObject == null)
			return null;
		BeanWrapperImpl beanWapper = new BeanWrapperImpl(parameterObject);
		Page pageInfo = null;
		try {
			pageInfo = (Page) beanWapper.getPropertyValue(PAGE_BEAN_NAME);
		} catch (Exception e) {
			pageInfo = null;
		}
		return pageInfo ;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		this.maxPageSize = Integer.parseInt( properties.getProperty("defaultMaxPageSize") ) ;
	}

}
