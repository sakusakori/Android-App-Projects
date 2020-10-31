package com.example.learnjava;

public class Microphone {
    //instance of variables or fields
    String name;
    String color;
    int model;

    public Microphone(String name, String color, int model) {
        this.name = name;
        this.color = color;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }
    //    public Microphone(){
//
//    }

}
