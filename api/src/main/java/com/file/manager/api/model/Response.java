package com.file.manager.api.model;

import java.io.Serializable;

public class Response  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Course course;
	
	

	public Response(Course course) {
		super();
		this.course = course;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	
}
