package com.example.syd.Interface;

import com.example.syd.Meal;

import java.util.List;

public interface IFirebaseLoadDone {

    void onFirebaseLoadSuccess(List<Meal> mealList);
    void onFirebaseLoadFailed(String message);

}
