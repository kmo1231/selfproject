package com.miok.common;

import java.util.List;

public class TreeVO {
    private String key; 		//그룹번호
    private String title; 		//그룹명
    private String parent;
    private boolean isFolder;
    private List children;
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getParent() {
        return parent;
    }
    
    public void setParent(String parent) {
        this.parent = parent;
    }
    
    public List getChildren() {
        return children;
    }
    
    public void setChildren(List children) {
        this.children = children;
    }
    
    public boolean getIsFolder() {
        return isFolder;
    }
    
    public void setIsFolder(boolean isFolder) {
        this.isFolder = isFolder;
    }
   
}