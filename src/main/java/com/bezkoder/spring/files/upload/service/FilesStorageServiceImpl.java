package com.bezkoder.spring.files.upload.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.files.upload.model.FileInfo;


@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	private final Path root = Paths.get("uploads");
	public static Logger logger = Logger.getLogger(FilesStorageServiceImpl.class);
	static File[] jpejfiles;
	static HashMap<String, String> nameOfFiles = new HashMap<String, String>();
	static boolean flag = true;
	
	
	static List<Path> result;
	

	@Override
	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public void save(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	// Stream<Path>
	public List<FileInfo> loadAll(String folderPath) throws IOException {
		//FileInfo fileInfos =  new FileInfo();
		List<FileInfo> fileInfos =  new ArrayList<FileInfo>();
		//NOTE:yyyy-MM-dd hh:ss-- this was mentioned in the email
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd*hh:mm:ss");
		
			result = findByFileExtension(Paths.get(folderPath), ".jpeg");
		
	
		for (Path path : result) {

			BasicFileAttributes attr;

			attr = Files.readAttributes(path, BasicFileAttributes.class);
		
			Date date = new Date(attr.creationTime().toMillis());
			String format = formatter.format(date);
			// rename the file on confirmation 
			if (flag == true) {
				File initialArchive = new File(folderPath,
						path.toFile().getAbsoluteFile().getName());
				File finalArchive = new File(folderPath , format+".jpeg");
			
                
                	
                 if (initialArchive.renameTo(finalArchive)) {
                     System.out.println("Renamed");
                 } else {
                     System.out.println("Error");
                 }
                 
				
				fileInfos.forEach(fileInfo -> fileInfo.setName(format));
				//fileInfos.setName(format);
				//fileInfos.setUrl(path.toFile().getAbsoluteFile()+format);
				//Files.move(path.getFileName(), path.resolveSibling(format.trim()), StandardCopyOption.REPLACE_EXISTING);

			}
			else {
				logger.info("Did not rename jpeg files");
			}
		}
		
		
		
		return fileInfos;
	// return Files.walk(this.root, 1).filter(path ->
	// !path.equals(this.root)).map(this.root::relativize);
}
	
	
	public static List<Path> findByFileExtension(Path path, String fileExtension) throws IOException {

		if (!Files.isDirectory(path)) {
			throw new IllegalArgumentException("Path must be a directory!");
		}

		List<Path> result;
		try (Stream<Path> walk = Files.walk(path)) {
			result = walk.filter(Files::isRegularFile) // is a file
					.filter(p -> p.getFileName().toString().endsWith(fileExtension)).collect(Collectors.toList());
		}
		return result;

	}
}
