package com.bezkoder.spring.files.upload.model;

import java.nio.file.Path;
import java.util.stream.Stream;

public class FileInfo {
  private String name;
  private String url;

  
  public FileInfo(String name) {
	    this.name = name;
	  
	  }
 
  public FileInfo(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public FileInfo() {
	// TODO Auto-generated constructor stub
}

public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


}
