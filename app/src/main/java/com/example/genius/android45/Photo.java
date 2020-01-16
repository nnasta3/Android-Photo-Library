package com.example.genius.android45;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Photo implements Serializable {
    protected String name;
    protected HashMap<String,ArrayList<String>> tags = new HashMap<String,ArrayList<String>>();
    protected File photoFile;
    protected String filePath;
    protected String caption;

	public Photo(String name){
        this.name = name;
    }
    public Photo(File file, String name, String filePath,String caption,HashMap<String,ArrayList<String>> tags){
        this.photoFile = file;
        this.name = name;
        this.filePath = filePath;
        this.caption=caption;

        
        
    }

    public HashMap<String, ArrayList<String>> getTags() {   	
    	return tags;
	}
      
	public void setTags(HashMap<String, ArrayList<String>> tags) {	
		this.tags = tags;
	}
	
	public void removeTag(HashMap<String, ArrayList<String>> tags,String key, String tagVal){
		ArrayList<String> temp = new ArrayList<String>();
		temp.add(tagVal);
		if(tags.isEmpty()){
			return;
		}
			 if(tags.containsKey(key) && tags.get(key).contains(tagVal)){
				tags.get(key).remove(tagVal);	
				if(tags.get(key).size()==0){//remove the key if it has no list
					tags.remove(key);

				}
			}		
		else{
			return;
		}
	}
	
	public void addTag(HashMap<String, ArrayList<String>> tags,String key, String tagVal){
		ArrayList<String> temp = new ArrayList<String>();
		temp.add(tagVal);
		
		if(!tags.isEmpty()){
			if(tags.containsKey(key)){				
				tags.get(key).add(tagVal);	
				return;
			}
			else{
				tags.put(key, temp);
				return;
			}
		}
		else{
			tags.put(key, temp);
			return;

		}
	}
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public void setPhotoName(String name){
        this.name = name;
    }

    public String getPhotoName() {
    	return this.name;
    }

    public File getPhotoFile(){ 
    	return this.photoFile;
    }

    public String getFilePath(){ 
    	return this.filePath;
    }

    private String trimmedName(){
        return this.name.split("\\.")[0];
    }

    @Override
    public String toString(){
        return trimmedName();
    }

}
