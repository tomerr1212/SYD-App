package com.example.syd;

public class readyMenu {
    String breakfast,lunch,snack,dinner;
    double sum;
    String author;


    public readyMenu(){ }

    public readyMenu(String breakfast, String lunch, String snack, String dinner, double sum, String author) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.snack = snack;
        this.dinner = dinner;
        this.sum = sum;
        this.author = author;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getSnack() {
        return snack;
    }

    public void setSnack(String snack) {
        this.snack = snack;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public double getSum() {
        return sum;
    }

    public static void setSum(double sum) {
        sum = sum;
    }

    public String getAuthor() {
        return author;
    }

    public static void setAuthor(String author) {
        author = author;
    }
}
