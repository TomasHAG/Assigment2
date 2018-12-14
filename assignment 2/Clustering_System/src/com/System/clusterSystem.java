package com.System;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class clusterSystem {
	
	private Hashtable<String, data> Data;
	private String words[];
	private int[] maxValue;
	private List<String> blogs;
	private List<centroid> cen;
	
	clusterSystem(String path){
		Data = new Hashtable<String, data>();
		blogs = new ArrayList<String>();
		
		readDataFromFile(path);
	}
	
	
	
	public void readDataFromFile(String path) {
		String content = null;
		try {
			Scanner scanner = new Scanner(Paths.get(path), StandardCharsets.UTF_8.name());
			content = scanner.useDelimiter("\\A").next();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] contentParts = content.split("\r\n");
		
		words = contentParts[0].split("\t");
		maxValue = new int[words.length];
		
		for(String con: contentParts) {
			if(con.equals(contentParts[0]))
				continue;
			
			
			
			String[] blogData = con.split("\t");
			data D = new data(blogData[0]);
			blogs.add(blogData[0]);
			
			for(int nr = 1; nr < blogData.length;nr++) {
				D.addData(words[nr], Integer.parseInt(blogData[nr]));
				if(Integer.parseInt(blogData[nr]) > maxValue[nr])
					maxValue[nr] = Integer.parseInt(blogData[nr]);
			}
			Data.put(D.getName(),D);
		}
		
		
	}
	
	public int getData(String blog, String word) {
		return ((data) Data.get(blog)).getData(word);
	}
	
	public double pearsonCalc(data d1, data d2) {
		
		double 	n = 0 , 
				d1Data, 
				d2Data, 
				sum1 = 0, 
				sum2 = 0,
				sum1sq = 0,
				sum2sq = 0,
				pSum = 0;
		for(int nr = 1; nr < words.length;nr++) {
			d1Data = d1.getData(words[nr]);
			d2Data = d2.getData(words[nr]);
			
			if(d1Data > 0 && d2Data > 0) {
				n++;
			
				sum1 += d1Data;
				sum2 += d2Data;
			
				sum1sq += d1Data * d1Data;
				sum2sq += d2Data * d2Data;
			
				pSum += d1Data * d2Data;
			}
		}
		
		if(n == 0)
			return 0;
		
		double 	num = pSum - ((sum1*sum2)/n),
				den = Math.sqrt((sum1sq - ((sum1 * sum1) / n )) * (sum2sq - ((sum2 * sum2) / n)));
		double sum = Math.abs(num / den);
		
		if(Double.isNaN(sum))
			return 0;
		return Math.abs(num / den);
	}
	
	public data getBlogData(String key) {
		return Data.get(key);
	}
	
	public int getDataFromBlog(data blog, String key) {
		return blog.getData(key);
	}
	
	public void createCentroids(int quantity) {
		cen = new ArrayList<centroid>();
		
		centroid da;
		for(int nr = 0; nr < quantity;nr++) {
			cen.add( createOneCentroid());
			//System.out.print("");
		}
		
		
	}
	
	public centroid createOneCentroid() {
		int counter;
		centroid da = new centroid("centroid" + cen.size());
		counter = 1;
		for(int ad = 1; ad < words.length;ad++) {
			//System.out.print((int) (Math.random() * maxValue[counter]));
			da.addData(words[ad], (int) (Math.random() * maxValue[counter++]));
		}
		return da;
	}
	
	public List<centroid> KMeans(int nrOfCentroids) {
		createCentroids(nrOfCentroids);
		
		//System.out.println(iterativAlgoritm(0));
		iterativAlgoritm(0);
		
		return cen;
	}
	
	private int iterativAlgoritm(int n) {
		Set<String> set = Data.keySet();
		centroid closest;
		double calc, prevcalc;
		int counter = 0;
		
		for(centroid c : cen) {
			c.clense();
		}
		
		for(String key : set) {
			
			
			//closest = cen.get((int) (Math.random() * cen.size()));
			closest = null;
			prevcalc = 0;
			for(centroid c : cen) {
				calc = pearsonCalc(Data.get(key), c);
				//System.out.print(calc + " ");
				/*if(closest == null) {
					closest = c;
					prevcalc = calc;
				}else */if(Math.abs(calc - 1) < Math.abs(prevcalc - 1)) {
					closest = c;
					prevcalc = calc;
				}
			}
			
			
			
			closest.assignBlog(key, Data.get(key));;
			//System.out.println("");
		}
		
		counter = 0;
		for(centroid c : cen) {
			c.beCenterOfBlogs();
			if(!c.isThereAnychange())
				counter++;
		}
		
		if(counter >= cen.size())
			return ++n;
		
		/*for(centroid c : cen) {
			System.out.print(c.getName() + " have: " + c.nrOfBlogs() + "; ");
		}
		System.out.println("");*/
		
		return iterativAlgoritm(++n);
	}
	
	public int NumbOfCentroid() {
		return cen.size();
	}
	
	public centroid getFromCentroid(int index) {
		return cen.get(index);
	}
	
	public String centroidToList(int index) {
		centroid c = cen.get(index);
		
		String str = "";
		String buffert;
		for(String b : c.listOfBlogs()) {
			buffert = b;
			if(buffert.contains("\"")) {
				//System.out.println("detected " + buffert);
			
			buffert = buffert.replaceAll("\"", "``");
				//System.out.println("change: " + buffert);
			}
			
			//str += "\""+ b + ", " + c.getName() + "\", ";
			str += "\""+ buffert + "\", ";
		}
		
		return str.substring(0, str.length()-2);
	}
}
