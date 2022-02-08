package com.file.manager.api.service;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.file.manager.api.model.Course;
import com.file.manager.api.model.Lesson;
import com.file.manager.api.model.Topic;

public class Wrapper {

	public Course courseBuilder(HttpServletRequest request) {

		Course course = new Course();
		try {

			String content = request.getParameter("course");
			JSONParser parser = new JSONParser();
			JSONObject object = new JSONObject();
			object = (JSONObject) parser.parse(content);
			course.setTitle(toString(object.get("title")));
			course.setHighlight(toString(object.get("highlight")));
			course.setRequirement(toString(object.get("requirement")));
			course.setLerningObjective(toString(object.get("lerningObjective")));
			course.setAudience(toString(object.get("audience")));
			course.setPosition(toString(object.get("position")));
			course.setRating(toString(object.get("rating")));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return course;
	}


	public Topic  topicAndLessonBuilder(HttpServletRequest request) {

		Set<Lesson> lessons = new HashSet<Lesson>();
		Topic topic = new Topic();
	
		try {
			
			String content = request.getParameter("topic");
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(content);
			JSONObject jsonTopic = (JSONObject)obj.get("topic");
			topic.setTitle(toString(jsonTopic.get("title")));
			JSONArray jsonLessons = (JSONArray)jsonTopic.get("lessons");

			for(int i = 0; i < jsonLessons.size(); i++) {

				JSONObject object = (JSONObject)jsonLessons.get(i);
				Lesson lesson = new Lesson();
				lesson.setLesson(toString(object.get("lesson")));
				lessons.add(lesson);
			}
			
			topic.setLessons(lessons);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return topic;
	}

	private String toString(Object value) {
		return String.valueOf(value);
	}

}
