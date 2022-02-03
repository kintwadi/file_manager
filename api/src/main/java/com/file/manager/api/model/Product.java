package com.file.manager.api.model;

public class Product {
	
	private long id;
	private String name;
	private String uri;
	
	
	
	public Product() {
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", uri=");
		builder.append(uri);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	

}
