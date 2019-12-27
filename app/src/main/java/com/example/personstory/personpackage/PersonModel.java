package com.example.personstory.personpackage;

public class PersonModel {
    private int id;
    private String name, state;
    private byte[] image;

    public PersonModel(int id, String name, String state, byte[] image) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.image = image;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
