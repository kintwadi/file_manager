package com.file.manager.api.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Lesson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String lesson;
	private String url;

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    private Topic topic;
	
	public Lesson() {
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLesson() {
		return lesson;
	}
	public void setLesson(String lesson) {
		this.lesson = lesson;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	@Override
	public String toString() {
		return "Lesson [id=" + id + ", lesson=" + lesson + ", url=" + url + ", topic=" + topic + "]";
	}
	
	
	
	

	
	
	
	
}
