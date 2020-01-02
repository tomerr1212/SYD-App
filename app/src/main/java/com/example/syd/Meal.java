package com.example.syd;

public class Meal {
    String type;
    String name;
    double calories;


    public  Meal(){
    }

    public Meal(double calories, String type, String name) {
        this.calories = calories;
        this.type = type;
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
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
