package com.example.syd;

public class nutritionist {

    private String Name,Gender,Education, email;
    private int Age, YearsOfExp;


    public nutritionist(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


}
