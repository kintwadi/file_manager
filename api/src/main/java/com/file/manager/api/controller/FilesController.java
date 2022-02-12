package com.file.manager.api.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.file.manager.api.model.Resource;
import com.file.manager.api.model.UploadResponseMessage;
import com.file.manager.api.service.FileService;

@RestController
public class FilesController {

	private final FileService fileService;

	@Autowired
	public FilesController(FileService fileService) {
		this.fileService = fileService;
	}
	@GetMapping("file/{user_dir}/{content_dir}/{file_name}")
	public ResponseEntity<InputStreamResource> getFile(
			@PathVariable("user_dir") String userDir,
			@PathVariable("content_dir") String contentDir,
			@PathVariable("file_name") String fileName) throws IOException 
	{

		fileService.setUserDir(userDir);
		fileService.setContentDir(contentDir);
		
		ResponseEntity<InputStreamResource> resource = fileService.getFile(fileName);

		return resource;
	
	}

	@GetMapping("files/{user_dir}/{content_dir}")
	public ResponseEntity<List<Resource>> getFiles(
			@PathVariable("user_dir") String userDir,
			@PathVariable("content_dir") String contentDir) {

		fileService.setUserDir(userDir);
		fileService.setContentDir(contentDir);

		List<Resource> fileInfos = fileService.loadAll()
				.stream()
				.map(this::pathToFileInfo)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK)
				.body(fileInfos);
	}

	private Resource pathToFileInfo(Path path) {
		Resource fileInfo = new Resource();

		String userDir = fileService.getUserDir();
		String contentDir = fileService.getContentDir();

		String filename = path.getFileName()
				.toString();
		fileInfo.setFilename(filename);
		fileInfo.setUrl(MvcUriComponentsBuilder
				.fromMethodName(FilesController.class, "getFile", userDir,contentDir,filename)
				.build()
				.toString());
		try {
			fileInfo.setSize(Files.size(path));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error: " + e.getMessage());
		}

		return fileInfo;
	}
	@PostMapping("upload/{user_dir}/{content_dir}")
	public ResponseEntity<UploadResponseMessage> uploadFile(
			@PathVariable("user_dir") String userDir,
			@PathVariable("content_dir") String contentDir,
			@RequestParam("file") MultipartFile [] file, 
			HttpServletRequest request) {

		fileService.setUserDir(userDir);
		fileService.setContentDir(contentDir);

		try {

			fileService.store(request,file);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new UploadResponseMessage("Uploaded the file successfully: "));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new UploadResponseMessage("Could not upload the file: "));
		}


	}
	@DeleteMapping("delete/{user_dir}/{content_dir}/{file_name}")
	public void delete(
			@PathVariable("user_dir") String userDir,
			@PathVariable("content_dir") String contentDir,
			@PathVariable("file_name") String fileName) {

		fileService.setUserDir(userDir);
		fileService.setContentDir(contentDir);
		fileService.deleteAll(fileName);
	}

}
