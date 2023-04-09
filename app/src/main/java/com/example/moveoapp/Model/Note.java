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
public class Note implements Serializable {
    @PrimaryKey
    @NonNull
    public String key="";
    public String title="";
    public String date="";
    public String body="";
    public String editor="";
    public String latitude="";
    public String longitude="";



    public Note(){
    }

    public Note(String title, String date, String body,String key,String editor,String latitude,String longitude) {
        this.title = title;
        this.date = date;
        this.body = body;
        this.editor = editor;
        this.latitude=latitude;
        this.longitude=longitude;
        this.key=key;
    }


    @NonNull
    public String getKey() {return key;}
    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getBody() {
        return body;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public String getEditor() {return editor;}


    public void setKey(@NonNull String key) {
        this.key = key;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setLatitude(String latitude) {this.latitude = latitude;}
    public void setLongitude(String longitude) {this.longitude = longitude;}
    public void setEditor(String editor) {
        this.editor = editor;
    }





}
