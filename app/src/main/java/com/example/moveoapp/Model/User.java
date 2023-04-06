package com.example.moveoapp.Model;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.moveoapp.MyApplication;
import com.google.firebase.firestore.FieldValue;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class User {
    public String name="";
    public String email="";
//    public String imgUrl="";
    public Long lastUpdated=0L;



    public User(){
    }

    public User(String name, @NonNull String email) {
        this.name = name;
        this.email=email;
//        this.imgUrl = imgUrl;
    }

//    static final String ID = "id";
    static final String NAME = "name";
    static final String EMAIL = "email";
//    static final String AVATAR = "avatar";
    static final String COLLECTION = "users";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "user_local_last_update";

    public static User fromJson(Map<String,Object> json){
        String name = (String)json.get(NAME);
        String email = (String)json.get(EMAIL);
//        String id = (String)json.get(ID);
//        String avatar = (String)json.get(AVATAR);
        User user = new User(name,email);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            user.setLastUpdated((long) time.getSeconds());

        }catch(Exception e){

        }
        return user;
    }


    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(NAME, getName());
        json.put(EMAIL, getEmail());
//        json.put(ID, getId());
//        json.put(AVATAR, getImgUrl());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

//    public void setId(@NonNull String id) {
//        this.id = id;
//    }

    public void setName(String name) {
        this.name = name;
    }

//
//    public void setImgUrl(String imgUrl) {
//        this.imgUrl = imgUrl;
//    }

    private void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }
    //
    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    public String getEmail() {
        return email;
    }

//    @NonNull
//    public String getId() {
//        return id;
//    }

    public String getName() {
        return name;
    }


//    public String getImgUrl() {
//        return imgUrl;
//    }

    public Long getLastUpdated() {
        return lastUpdated;
    }
}