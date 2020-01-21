package com.example.syd;

public class upload {
    private String url,name;

    public upload (String url1,String name1){
        this.url=url1;
        this.name=name1;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
