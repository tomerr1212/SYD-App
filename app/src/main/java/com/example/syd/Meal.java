package com.example.syd;

public class Meal {
    String type;
    String name;
    long calories;


    public  Meal(){
    }

    public Meal(long calories, String type, String name) {
        this.calories = calories;
        this.type = type;
        this.name = name;
    }

    public long getCalories() {
        return calories;
    }

    public void setCalories(long calories) {
        this.calories = calories;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
