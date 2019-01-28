package com.shadow.books.dto;

import java.util.ArrayList;
import java.util.List;

import com.shadow.books.domain.Item;

public class ItemDto {

	private String language;
	private List<Item> list = new ArrayList<Item>();
	
	
	@Override
	public String toString() {
		return "RandonOutput [language=" + language + ", list=" + list + "]";
	}
	public String getLanguage() {
		return language;
	}
	public List<Item> getList() {
		return list;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setList(List<Item> list) {
		this.list = list;
	}
	
	
	
}
