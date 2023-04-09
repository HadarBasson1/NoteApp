package com.example.moveoapp.Model;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.moveoapp.MyApplication;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
@Entity
public class User implements Serializable {
    @PrimaryKey
    @NonNull
    public String email="";
    public String name="";
    public String imgUrl="";

    public User(){
    }

    public User(String name, @NonNull String email,String imgUrl) {
        this.name = name;
        this.email=email;
        this.imgUrl = imgUrl;
    }


    public void setEmail(@NonNull String email) {this.email = email;}
    public void setName(String name) {
        this.name = name;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getImgUrl() {
        return imgUrl;
    }



}