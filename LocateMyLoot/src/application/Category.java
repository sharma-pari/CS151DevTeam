package application;

import java.util.ArrayList;

public class Category implements Attribute{
    private String name;
    private String desc;
    private ArrayList<String> list;

    public Category(String name) {
        this.name = name;
    }
    
    @Override
    public void setName(String name) {
    	this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void setDesc(String desc) {
    	this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
