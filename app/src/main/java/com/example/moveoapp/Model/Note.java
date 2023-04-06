package com.example.moveoapp.Model;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.moveoapp.MyApplication;
import com.google.firebase.firestore.FieldValue;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Note {
    @PrimaryKey
    @NonNull
    public String key="";
    public String title="";
    public String date="";
    public String body="";
    public String editor="";
    public String imgUrl="";
    public String isDeleted="";
    public Long lastUpdated=0L;

    public Note(){
    }

    public Note(String title, String date, String body,String editor, String imgUrl,String key,String isDeleted) {
        this.title = title;
        this.date = date;
        this.body = body;
        this.editor = editor;
        this.imgUrl = imgUrl;
        this.key=key;
        this.isDeleted=isDeleted;
    }

    static final String TITLE = "title";
    static final String DATE = "date";
    static final String BODY = "body";
    static final String EDITOR = "editor";
    static final String AVATAR = "avatar";
    static final String KEY = "key";
    static final String COLLECTION = "notes";
    static final String IS_DELETED = "isDeleted";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "notes_local_last_update";

    public static Note fromJson(Map<String,Object> json){
        String title = (String)json.get(TITLE);
        String date = (String)json.get(DATE);
        String body = (String)json.get(BODY);
        String editor = (String)json.get(EDITOR);
        String avatar = (String)json.get(AVATAR);
        String key=(String)json.get(KEY);
        String isDeleted=(String) json.get(IS_DELETED);
        Note note = new Note(title,date,body,editor,avatar,key,isDeleted);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            note.setLastUpdated((long) time.getSeconds());

        }catch(Exception e){

        }
        return note;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public String getEditor() {
        return editor;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

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

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    //
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

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(TITLE, getTitle());
        json.put(DATE, getDate());
        json.put(BODY, getBody());
        json.put(EDITOR, getEditor());
        json.put(AVATAR, getImgUrl());
        json.put(KEY, getKey());
        json.put(IS_DELETED, getIsDeleted());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }



}
