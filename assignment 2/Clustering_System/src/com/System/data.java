package com.System;

import java.util.Hashtable;

public class data {
	
	protected Hashtable<String, Integer> words;
	protected String name;
	
	data(String name){
		words = new Hashtable<String, Integer>();
		this.name = name;
	}
	
	public void addData(String name, int number) {
		words.put(name, number);
	}
	
	public int getData(String key) {
		return (int) words.get(key);
	}
	
	public String getName() {
		return name;
	}
	
	protected void setData(String key, int data) {
		words.remove(key);
		addData(key, data);
	}

}
