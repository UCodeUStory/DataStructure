package com.wangpos.datastructure.java;

import java.util.Map;

/**
 * Created by qiyue on 2017/12/29.
 */

public class FileBean {

    public FileBean(String path, double size) {
        this.path = path;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    private String path;

	public void addPathtoMap(Map<String,String> map){
		map.put("path",this.path);
	}

    private double size;

	public void addSizetoMap(Map<String,String>map){
		map.put("size",this.size+"");
	}


}
