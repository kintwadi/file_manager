package com.file.manager.api.model.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Country {

	private String name;
	private String language;
	private String flag;
	private String code;
	private Country activeCountry;
	private List<Country>countries;
	static Map<String, Country>countriesMap;
	
	
	static {
		countriesMap = new HashMap<>();
		Country us = new Country();
		us.setName("USA");
		us.setFlag("img/us.png");
		us.setLanguage("English");
		us.setCode("us");

		Country pt = new Country();
		pt.setName("Portugal");
		pt.setFlag("img/pt.png");
		pt.setLanguage("Portuguese");
		pt.setCode("pt");
		countriesMap.put("English", us);
		countriesMap.put("Portuguese", pt);	
	}

	public Country() {
		
	}

	public List<Country> getCountries() {
		
		return countries;
	}
	
	public void setCountries(String language){
		List<Country> countries = new ArrayList<>();
		
		for(String key: countriesMap.keySet()) {
			
			Country country = countriesMap.get(key);
			
			if(!country.language.equals(language)) {
				countries.add(country);
			}
		}
		this.countries = countries;
	}

	public Country getActiveCountry() {
		return activeCountry;
	}

	public void setActiveCountry(String language) {
		
		this.activeCountry = countriesMap.get(language);
		setCountries(language);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Country [name=");
		builder.append(name);
		builder.append(", language=");
		builder.append(language);
		builder.append(", flag=");
		builder.append(flag);
		builder.append(", code=");
		builder.append(code);
		builder.append(", activeCountry=");
		builder.append(activeCountry);
		builder.append(", countries=");
		builder.append(countries);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
	
}
