package com.example.syd;

import java.util.ArrayList;

public class Member {

    private String Name, Gender, email,activitygoal, myimages;
    private int Age;
    private double Weight, Height,weeklygoal,targetweight,dailygoalcalories;
    private ArrayList<String> customer_menus;

    public Member() {

    }

    public String getMyimages() {
        return myimages;
    }

    public void setMyimages(String myimages) {
        this.myimages = myimages;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivitygoal() {
        return activitygoal;
    }

    public void setActivitygoal(String activitygoal) {
        this.activitygoal = activitygoal;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public double getWeeklygoal() {
        return weeklygoal;
    }

    public void setWeeklygoal(double weeklygoal) {
        this.weeklygoal = weeklygoal;
    }

    public double getTargetweight() {
        return targetweight;
    }

    public void setTargetweight(double targetweight) {
        this.targetweight = targetweight;
    }

    public double getDailygoalcalories() {
        return dailygoalcalories;
    }

    public void setDailygoalcalories(double dailygoalcalories) {
        this.dailygoalcalories = dailygoalcalories;
    }

    @Override
    public String toString() {
        return "Member{" +
                "Name='" + Name + '\'' +
                ", Gender='" + Gender + '\'' +
                ", email='" + email + '\'' +
                ", activitygoal='" + activitygoal + '\'' +
                ", Age=" + Age +
                ", Weight=" + Weight +
                ", Height=" + Height +
                ", weeklygoal=" + weeklygoal +
                ", targetweight=" + targetweight +
                ", dailygoalcalories=" + dailygoalcalories +
                ", customer_menus=" + customer_menus +
                '}';
    }

    public ArrayList<String> getCustomer_menus() {
        return customer_menus;
    }

    public void setCustomer_menus(ArrayList<String> customer_menus) {
        this.customer_menus = customer_menus;
    }
}


