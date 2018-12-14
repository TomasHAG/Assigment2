package com.System;

import java.awt.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class centroid extends data{
	
	private Hashtable<String, data>  blog;
	private Hashtable<String, data> oldblog;
	private ArrayList<String> blogsContain;

	centroid(String name) {
		super(name);
		blog = new Hashtable<String, data>();
		blogsContain = new ArrayList<String>();
	}
	
	public void assignBlog(String blog, data Data) {
		this.blog.put(blog,Data);;
	}
	
	public void clense() {
		oldblog = blog;
		blog = new Hashtable<String, data>();
		blogsContain.clear();
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
			setData(buffertA[nr],(int)(words.get(buffertA[nr]) + (words.get(buffertA[nr]) - (int)(buffertI[nr]) * 0.75)));
		}
		
		if(counter == blog.size())
			return true;
		return false;
	}
	
	public int nrOfBlogs() {
		return blog.size();
	}
	
	public String allBlogs() {
		String str = getName() + "€";
		
		Set<String> key = blog.keySet();
		
		for(String b : key) {
			str += b + "€";
		}
		
		return str.substring(0, str.length() - 3);
	}
	public String[] arrayOfBlogs() {
		Set<String> key = blog.keySet();
		String ret[] = new String[key.size()];
		
		int index = 0;
		for(String s : key) {
			ret[index++] = s;
		}
		return ret;
	}

}
