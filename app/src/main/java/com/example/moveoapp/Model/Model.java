package com.example.moveoapp.Model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
//    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance(){
        return _instance;
    }
    private Model(){
    }


    public interface Listener<T>{
        void onComplete(T data);
    }


    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }

    public void regiser(String email, String password,String name, Listener<User> listener){
        FirebaseModel.register(email,password,name,listener);
    }

    public void login(String email, String password, Listener<FirebaseUser> listener){
        FirebaseModel.login(email,password,listener);
    }

    public void logOut() {
        FirebaseModel.logOut();
    }

    public boolean currentUser(){
        return FirebaseModel.currentUser();
    }


    public void findNameByEmail(String email,Listener<String>listener) {
        FirebaseModel.findNameByEmail(email,listener);
    }



}
