package com.example.syd;

public class Meal {
    int type;
    String name;
    long calories;


    public  Meal(){
    }

    public Meal(long calories, int type, String name) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
