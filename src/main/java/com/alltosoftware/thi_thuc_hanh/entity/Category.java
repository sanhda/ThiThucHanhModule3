package com.alltosoftware.thi_thuc_hanh.entity;

public class Category {
    private int id;
    private String name;

    public Category() {

    }

    public Category(int id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}