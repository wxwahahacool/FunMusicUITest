package com.ylss.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.ho.yaml.Yaml;

public class YamlReader {
	
	 private HashMap<String, HashMap<String, String>> yml;    
	 
	    @SuppressWarnings("unchecked")
		public HashMap<String, HashMap<String, String>> getYamlFile(String yamlFile) {
	        File f = new File(yamlFile);
	        InputStreamReader reader = null;
	        try {
				reader = new InputStreamReader(new FileInputStream(f.getAbsolutePath()), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
	        this.yml = (HashMap<String, HashMap<String, String>>) Yaml.loadType(reader,
			        HashMap.class);
	        return this.yml;
	    }

}
