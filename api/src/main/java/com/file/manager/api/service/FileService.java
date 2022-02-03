package com.file.manager.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	@Value("${upload.base.dir}")
	private String baseDir;
	private String userDir;
	private String contentDir;

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Paths.get(getTargetDir()));
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload folder!");
		}
	}

	public void save(MultipartFile [] file) {
		try {

			ClassPathResource resource = new ClassPathResource(getTargetDir());
			Path root = Paths.get(resource.getFile().getAbsolutePath());

			if (!Files.exists(root)) {
				init();
			}
			for(int i = 0; i < file.length; i ++) {

				Files.copy(file[i].getInputStream(), root.resolve(file[i].getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	public ResponseEntity<InputStreamResource>  getFile(String fileName)
	{
		ClassPathResource imgFile = new ClassPathResource(getTargetDir()+"/"+fileName);

		try {
			return ResponseEntity
					.ok()
					.contentType(MediaType.IMAGE_JPEG)
					.body(new InputStreamResource(imgFile.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public void deleteAll(String fileName) {

		try {

			ClassPathResource resource = new ClassPathResource(getTargetDir()+"/"+fileName);
			FileSystemUtils.deleteRecursively(Paths.get(resource.getFile().getAbsolutePath()).toFile());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<Path> loadAll() {
		try {

			ClassPathResource resource = new ClassPathResource(getTargetDir());
			Path root = Paths.get(resource.getFile().getAbsolutePath());

			if (Files.exists(root)) {

				return Files.walk(root, 1)
						.filter(path -> !path.equals(root))
						.collect(Collectors.toList());
			}

			return Collections.emptyList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getUserDir() {
		return userDir;
	}

	public void setUserDir(String userDir) {
		this.userDir = userDir;
	}

	public String getContentDir() {
		return contentDir;
	}

	public void setContentDir(String contentDir) {
		this.contentDir = contentDir;
	}

	public String getTargetDir() {

		StringBuilder target = new StringBuilder(baseDir);
		target.append("/");
		target.append(userDir);
		target.append("/");
		target.append(contentDir);
		return target.toString();
	}
	
	public JSONObject stringToJson(String object) {
		

		JSONObject jObject = new JSONObject();
		try {
			
			JSONParser parser = new JSONParser();
			jObject = (JSONObject)parser.parse(object);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("json: "+ jObject.toString());
		
		//JSONObject name = jObject.getJSONObject("name"); 
		//String url = jObject.getString("uri"); 
		
		//System.out.println("name: "+ name);
		//System.out.println("url: "+ url);
		
		
		return jObject;
	}





}
