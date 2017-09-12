package mybatis.com.apli.mapper;

import mybatis.com.apli.vo.ProductCarSkuColorVo;

public interface ProductCarSkuColorVoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductCarSkuColorVo record);

    int insertSelective(ProductCarSkuColorVo record);

    ProductCarSkuColorVo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductCarSkuColorVo record);

    int updateByPrimaryKey(ProductCarSkuColorVo record);
}