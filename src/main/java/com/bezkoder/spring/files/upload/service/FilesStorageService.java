package com.bezkoder.spring.files.upload.service;



import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.files.upload.model.FileInfo;

public interface FilesStorageService {
  public void init();

  public void save(MultipartFile file);

  public Resource load(String filename);

  public void deleteAll();

  public List<FileInfo> loadAll(String folderPath) throws IOException;

   
}
