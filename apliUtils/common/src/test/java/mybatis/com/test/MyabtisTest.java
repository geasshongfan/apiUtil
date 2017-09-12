package mybatis.com.test;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyabtisTest {
  public static void main(String[] args) {
    List<String> warnings = new ArrayList<String>();
    boolean overwrite = true;
    String genCfg = "/test/java/mybatis/com/xml/mybatis-generator.xml";
    URL resource = MyabtisTest.class.getResource(genCfg);
    File configFile = new File(MyabtisTest.class.getResource(genCfg).getFile());
    ConfigurationParser cp = new ConfigurationParser(warnings);
    Configuration config = null;
    try {
      config = cp.parseConfiguration(configFile);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (XMLParserException e) {
      e.printStackTrace();
    }
    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
    MyBatisGenerator myBatisGenerator = null;
    try {
      myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
    } catch (InvalidConfigurationException e) {
      e.printStackTrace();
    }
    try {
      myBatisGenerator.generate(null);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
