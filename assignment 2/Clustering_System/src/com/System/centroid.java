package com.System;

import java.util.Hashtable;
import java.util.Set;

public class centroid extends data{
	
	private Hashtable<String, data>  blog;
	private Hashtable<String, data> oldblog;

	centroid(String name) {
		super(name);
		blog = new Hashtable<String, data>();
	}
	
	public void assignBlog(String blog, data Data) {
		this.blog.put(blog,Data);
	}
	
	public void clense() {
		oldblog = blog;
		blog = new Hashtable<String, data>();
	}
	
	public boolean isThereAnychange() {
		return !oldblog.equals(blog);
	}
	
	public boolean contains(String blog) {
		return this.blog.contains(blog);
	}
	
	public Set<String> listOfBlogs() {
		return blog.keySet(); 
	}
	
	public boolean beCenterOfBlogs() {
		if(blog.size() == 0)
			return true;
		
		
		data da;
		int counter = 0;
		int sum;
		Set<String> KEYS = words.keySet();
		int[] buffertI = new int[KEYS.size()];
		String[] buffertA = new String[KEYS.size()];
		Set<String> keys = blog.keySet();
		int index = 0;
		
		for(String KEY : KEYS) {
			buffertA[index] = KEY;
			sum = 0;
			for(String key : keys) {
				sum += blog.get(key).getData(KEY); 
			}
			buffertI[index++] = (int) (sum/((double)(blog.size())));
		}
		
		for(int nr = 0; nr < KEYS.size();nr++) {
			if(getData(buffertA[nr]) == buffertI[nr])
				counter++;
			/*if(words.get(buffertA[nr]).intValue() < buffertI[nr])
				setData(buffertA[nr], words.get(buffertA[nr]).intValue() + (int)(buffertI[nr]/2));
			else
				setData(buffertA[nr], words.get(buffertA[nr]).intValue() - (int)(buffertI[nr]/2));*/
			//setData(buffertA[nr], buffertI[nr]);
			setData(buffertA[nr], words.get(buffertA[nr]).intValue() - (int)(buffertI[nr] * 0.75));
		}
		
		if(counter == blog.size())
			return true;
		return false;
	}
	
	public int nrOfBlogs() {
		return blog.size();
	}

}
