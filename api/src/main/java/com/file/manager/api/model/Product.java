package com.file.manager.api.model;

public class Product {
	
	private long id;
	private String name;
	private String resourceUrl;
	
	
	
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", resourceUrl=");
		builder.append(resourceUrl);
		builder.append("]");
		return builder.toString();
	}
	

	
	
	
	

}
