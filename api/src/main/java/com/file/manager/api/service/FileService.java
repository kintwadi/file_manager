package com.file.manager.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.file.manager.api.model.Course;
import com.file.manager.api.model.Lesson;
import com.file.manager.api.model.Topic;
import com.file.manager.api.repository.CourseRepository;
import com.file.manager.api.repository.LessonRepository;
import com.file.manager.api.repository.TopicRepository;

@Service
public class FileService {

	@Value("${upload.base.dir}")
	private String baseDir;
	private String userDir;
	private String contentDir;

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private LessonRepository lessonRepository;

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Paths.get(getTargetDir()));
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload folder!");
		}
	}

	public void addCourse(Course course) {

		courseRepository.save(course);
	}

	public void store(HttpServletRequest request, MultipartFile[] file) {

		Wrapper wrapper = new Wrapper();
		Course course = wrapper.courseBuilder(request);
		Topic topic = wrapper.topicBuilder(request);
		List<Lesson> lessonList = wrapper.lessonBuilder(request);
		
		Set<Lesson> lessons = new HashSet<Lesson>();
		List<String> filenames = fileStore(file);
	
		for (int i = 0; i < filenames.size(); i++) {

			Lesson lson = new Lesson();
			lson.setUrl(filenames.get(i));
			lson.setLesson(lessonList.get(i).getLesson());
			lson.setTopic(topic);
			lessons.add(lson);
		}
		topic.setLessons(lessons);
		course.setTopic(topic);
		topic.setCourse(course);
		//courseRepository.save(course);
		topicRepository.save(topic);
	}

	public List<String> fileStore(MultipartFile[] file) {

		List<String> filenames = new ArrayList<>();

		try {
			ClassPathResource resource = new ClassPathResource(getTargetDir());
			Path root = Paths.get(resource.getFile().getAbsolutePath());
			if (!Files.exists(root)) {
				init();
			}
			for (int i = 0; i < file.length; i++) {

				String filename = file[i].getOriginalFilename();
				Files.copy(file[i].getInputStream(), root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
				filenames.add(filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		return filenames;

	}

	public ResponseEntity<InputStreamResource> getFile(String fileName) {
		ClassPathResource imgFile = new ClassPathResource(getTargetDir() + "/" + fileName);

		try {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
					.body(new InputStreamResource(imgFile.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public void deleteAll(String fileName) {

		try {

			ClassPathResource resource = new ClassPathResource(getTargetDir() + "/" + fileName);
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

				return Files.walk(root, 1).filter(path -> !path.equals(root)).collect(Collectors.toList());
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

}
