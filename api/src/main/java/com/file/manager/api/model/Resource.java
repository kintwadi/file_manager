package com.file.manager.api.model;

public class Resource {

	private long lessonId;
	private String fileName;
	private String url;
	private Long size;


	public Resource() {

	}

	public Resource(String filename, String url) {
		this.fileName = filename;
		this.url = url;
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public long getLessonId() {
		return lessonId;
	}

	public void setLessonId(long lesson) {
		this.lessonId = lesson;
	}


	
}
