package com.apli.common.utils.file;

import java.io.File;

public class FileWindowDelete {

  private static final String fileName = "D:\\apache-tomcat-8.5.20\\webapps\\site";

  public static void main(String[] args) {
    File file = new File(fileName);
    delteWindowFile(file);
  }

  /**
   * 递归删除指定文件
   * @param file
   */
  private static void delteWindowFile(File file) {
    if (file.isDirectory()){
      //文件目录
      File[] files = file.listFiles();
      if (files.length == 0){
        //空文件夹 直接删除
        file.delete();
      }else{
        //进行递归删除
        for (File f : files) {
          delteWindowFile(f);
          f.delete();
        }
      }
    }else{
      //直接删除文件
      file.delete();
    }
  }
}
