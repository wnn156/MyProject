package com.example.myapplication.Interface;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.myapplication.Data.User;
import com.google.gson.Gson;

public class SharedPreference {

    // 값 저장
    public static void setAttribute(Context context,  User user){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.commit();
    }

    // 값 읽기
    public static User getAttribute(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("user","");
        if(json == null)
            return null;
        User user = gson.fromJson(json, User.class);
        return user;
    }

    // 데이터 삭제
    public static void removeAttribute(Context context, String key){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    // 모든 데이터 삭제
    public static void removeAllAttribute(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}