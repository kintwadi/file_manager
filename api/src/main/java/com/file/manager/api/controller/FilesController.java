package com.file.manager.api.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.file.manager.api.model.FileInfo;
import com.file.manager.api.model.UploadResponseMessage;
import com.file.manager.api.service.FileService;

@RestController
public class FilesController {

    private final FileService fileService;
   
    @Autowired
    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value = "file/{user_dir}/{content_dir}/{fileName}")
    public ResponseEntity<InputStreamResource> getFile(
    		@PathVariable("user_dir") String userDir,
    		@PathVariable("content_dir") String contentDir,
    		@PathVariable("fileName") String fileName) throws IOException {

    	fileService.setUserDir(userDir);
    	fileService.setContentDir(contentDir);
        return fileService.getFile(fileName);
    }

    @GetMapping("files/{user_dir}/{content_dir}")
    public ResponseEntity<List<FileInfo>> getFiles(
    		@PathVariable("user_dir") String userDir,
    		@PathVariable("content_dir") String contentDir) {
    	
    	fileService.setUserDir(userDir);
    	fileService.setContentDir(contentDir);
    	
        List<FileInfo> fileInfos = fileService.loadAll()
                                              .stream()
                                              .map(this::pathToFileInfo)
                                              .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                             .body(fileInfos);
    }
    
    private FileInfo pathToFileInfo(Path path) {
        FileInfo fileInfo = new FileInfo();
        
        String userDir = fileService.getUserDir();
        String contentDir = fileService.getContentDir();
        
        String filename = path.getFileName()
                              .toString();
        fileInfo.setFilename(filename);
        fileInfo.setUrl(MvcUriComponentsBuilder.fromMethodName(FilesController.class, "getFile", userDir,contentDir,filename)
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
    		@RequestParam("file") MultipartFile [] file) {
    	
    	fileService.setUserDir(userDir);
    	fileService.setContentDir(contentDir);
   
        try {
            fileService.save(file);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(new UploadResponseMessage("Uploaded the file successfully: "));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                                 .body(new UploadResponseMessage("Could not upload the file: "));
        }
    }


    @DeleteMapping("delete/{user_dir}/{content_dir}/{fileName}")
    public void delete(
    		@PathVariable("user_dir") String userDir,
    		@PathVariable("content_dir") String contentDir,
    		@PathVariable("fileName") String fileName) {
    		
    	fileService.setUserDir(userDir);
    	fileService.setContentDir(contentDir);
        fileService.deleteAll(fileName);
    }
    
    

  
    
    
}
