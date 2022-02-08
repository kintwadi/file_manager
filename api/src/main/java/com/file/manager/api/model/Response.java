package com.file.manager.api.model;

public class Response {

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Response [course=");
		builder.append(course);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
}
