package com.bezkoder.spring.files.upload.service;

import java.io.File;
import java.io.IOException;

public class TestRename {

	public static void main(String args[]) {
		File file = new File("C:\\TestRename" , "ABC.txt");   

		File file1 = new File("C:\\TestRename" , "myDoc.txt");          

		try {
			file1.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (file1.exists()){  
			
		  System.out.println(file1.renameTo(file));
		}
	}
	
}
