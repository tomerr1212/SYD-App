package com.example.syd;

public class nutritionist {

    private String Name,Gender,Education;
    private int Age, YearsOfExp;
    private long NutrID;


    public nutritionist(){

    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getYearsOfExp() {
        return YearsOfExp;
    }

    public void setYearsOfExp(int yearsOfExp) {
        YearsOfExp = yearsOfExp;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }



    public long getNutrID() {
        return NutrID;
    }

    public void setNutrID(long nutrID) {
        NutrID = nutrID;
    }

}
