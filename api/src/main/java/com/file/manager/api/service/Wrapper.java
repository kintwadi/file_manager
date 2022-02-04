package com.file.manager.api.service;

import com.file.manager.api.model.Course;
import com.file.manager.api.model.Lesson;
import com.file.manager.api.model.Topic;
import com.google.gson.Gson;

public class Wrapper {

	
	public Course builder(String content) {
		Course course = new Course();
		
		return course;
	}
	
	
	public  Object fronJson(int type, String content) {
		
		
		try {
			Gson gson = new Gson(); // Or use new GsonBuilder().create();
			
			switch (type) {
			case 1:
			
				Course course = gson.fromJson(content, Course.class);
				return course;
				
			case 2:
				Topic topic = gson.fromJson(content, Topic.class); 
				return topic;
			

			case 3:
				Lesson lesson = gson.fromJson(content, Lesson.class); 
				return lesson;

			default:
				break;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
