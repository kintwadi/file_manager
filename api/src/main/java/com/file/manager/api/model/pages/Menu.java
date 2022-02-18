package com.file.manager.api.model.pages;

import java.util.List;

public class Menu {

	private MenuItem item;
	private List<MenuItem> menuItems;

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItem) {
		this.menuItems = menuItem;
	}

	public MenuItem getItem() {
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}
	
	
	
	
}
