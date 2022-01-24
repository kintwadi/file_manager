package com.rest.api.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.rest.api.model.FileData;

@Service
public class FileService {

	@Value("${upload.path}")
	private String uploadPath;

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Paths.get(uploadPath));
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload folder!");
		}
	}

	public void save(MultipartFile file) {
		try {
			Path root = Paths.get(uploadPath);
			if (!Files.exists(root)) {
				init();
			}
			Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	public Resource load(String filename) {
		try {
			Path file = Paths.get(uploadPath)
					.resolve(filename);
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

	public  List<FileData> getFile(Object controller,String fileName)
	{
		List<Path> result = new ArrayList<Path>();
		FileData fileInfo = new FileData();

		try {
			Path root = Paths.get(uploadPath);
			if (!Files.isDirectory(root)) {
				throw new IllegalArgumentException("Path must be a directory!");
			}

			// walk file tree, no more recursive loop
			try (Stream<Path> walk = Files.walk(root)) {
				result = walk
						.filter(Files::isReadable)      // read permission
						.filter(Files::isRegularFile)   // is a file
						.filter(p -> p.getFileName().toString().equalsIgnoreCase(fileName))
						.collect(Collectors.toList());
				for( Path path: result) {

					String file = path.getFileName().toString();
					String url = MvcUriComponentsBuilder.fromMethodName(
							controller.getClass(), 
							"getFile", path.getFileName().toString()).build().toString();

					fileInfo = new FileData(file, url);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}


		List<FileData> fileInfos = new ArrayList<FileData>();
		fileInfos.add(fileInfo);
		return fileInfos;

	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(uploadPath)
				.toFile());
	}

	public List<Path> loadAll() {
		try {


			Path root = Paths.get(uploadPath);
			
			if (Files.exists(root)) {
				return Files.walk(root, 1)
						.filter(path -> !path.equals(root))
						.collect(Collectors.toList());
			}

			return Collections.emptyList();
		} catch (IOException e) {
			throw new RuntimeException("Could not list the files!");
		}
	}


	public  List<Path> getFile2(String fileName)
	{
		List<Path> result = new ArrayList<Path>();
		FileData fileInfo = new FileData();

		try {
			Path root = Paths.get(uploadPath);
			if (!Files.isDirectory(root)) {
				throw new IllegalArgumentException("Path must be a directory!");
			}

			// walk file tree, no more recursive loop
			try (Stream<Path> walk = Files.walk(root)) {
				result = walk
						.filter(Files::isReadable)      // read permission
						.filter(Files::isRegularFile)   // is a file
						.filter(p -> p.getFileName().toString().equalsIgnoreCase(fileName))
						.collect(Collectors.toList());

			}
		}catch (Exception e) {
			e.printStackTrace();
		}



		return result;

	}

}
