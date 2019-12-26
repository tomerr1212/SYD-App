package com.example.syd.Interface;

import com.example.syd.readyMenu;

import java.util.List;

public interface IFirebaseLoadDoneM {

    void onFirebaseLoadSuccess(List<readyMenu> menusList);
    void onFirebaseLoadFailed(String message);

}
