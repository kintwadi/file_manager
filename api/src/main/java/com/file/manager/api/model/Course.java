package com.file.manager.api.model;

import java.io.Serializable;

public class Course implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private String highlight;
	private String position; // Best seller
	private String rating;
	private String audience;
	private String requirement;
	private String lerningObjective; // objective1, objective2, etc
	
	
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHighlight() {
		return highlight;
	}
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getLerningObjective() {
		return lerningObjective;
	}
	public void setLerningObjective(String lerningObjective) {
		this.lerningObjective = lerningObjective;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Course [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", highlight=");
		builder.append(highlight);
		builder.append(", position=");
		builder.append(position);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", audience=");
		builder.append(audience);
		builder.append(", requirement=");
		builder.append(requirement);
		builder.append(", lerningObjective=");
		builder.append(lerningObjective);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	

}