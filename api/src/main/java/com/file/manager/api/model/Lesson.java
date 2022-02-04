package com.file.manager.api.model;

public class Lesson {

	private long id;
	private String content;
	private String url;
	private Topic topic;
	
	
	public Lesson() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
		StringBuilder builder = new StringBuilder();
		builder.append("Lesson [id=");
		builder.append(id);
		builder.append(", content=");
		builder.append(content);
		builder.append(", url=");
		builder.append(url);
		builder.append(", topic=");
		builder.append(topic);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
}
