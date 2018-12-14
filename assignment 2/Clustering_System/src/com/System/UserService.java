package com.System;  

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;  
import javax.servlet.http.HttpServletResponse; 
import javax.ws.rs.Consumes; 
import javax.ws.rs.DELETE; 
import javax.ws.rs.FormParam; 
import javax.ws.rs.GET; 
import javax.ws.rs.OPTIONS; 
import javax.ws.rs.POST; 
import javax.ws.rs.PUT; 
import javax.ws.rs.Path; 
import javax.ws.rs.PathParam; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.Context; 
import javax.ws.rs.core.MediaType;  
@Path("/UserService") 

public class UserService { 
	
	//clusterSystem sys = new clusterSystem("blogdata.txt");
	private String path = "C:\\Users\\Tomas\\Desktop\\web Intelegence\\blogdata.txt";
	List<centroid> Centroid;
	String[][] centroidArray;
	
	public boolean areInteger(String in) {
		try{
			Integer.parseInt(in);
			return true;
		} catch(NumberFormatException e) {
			return false;
		} catch(NullPointerException e) {
			return false;
		}
	}
	
	@GET
	@Path("kMean/{nr}")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public String ini(@PathParam("nr") String nr) {
		
		clusterSystem sys = new clusterSystem(path);	

		
		if(!areInteger(nr))
			return "error";
		
		int number = Integer.parseInt(nr);
		Centroid = sys.KMeans(number);
		
		String out = "";
		
		for(centroid c: Centroid) {
			out += c.allBlogs();
			out += "¤";
		}
		
		out = out.substring(0, out.length() - 1);
		return out;
	}

}