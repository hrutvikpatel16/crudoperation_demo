package com;

public class Model {

    private String name,degree,role,url;

    Model()
    {

    }


    public Model(String name, String degree, String role, String url) {
        this.name = name;
        this.degree = degree;
        this.role = role;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
