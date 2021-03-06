package com.file.manager.api.model;

public class FileInfo {

	private String filename;
	private String url;
	private Long size;


	public FileInfo() {

	}

	public FileInfo(String filename, String url) {
		this.filename = filename;
		this.url = url;
	}


	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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
}
