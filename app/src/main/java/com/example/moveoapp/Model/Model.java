package com.example.moveoapp.Model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.moveoapp.MyApplication;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance(){
        return _instance;
    }
    private Model(){
    }

    public void getAllNotes(Listener<List<Note>> callback) {
        executor.execute(()->{
            List<Note> data = localDb.noteDao().getAll();
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }



    public interface Listener<T>{
        void onComplete(T data);
    }


    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }

    public void regiser(String email, String password,String name, Listener<User> listener){
        FirebaseModel.register(email, password, name, new Listener<User>() {
            @Override
            public void onComplete(User user) {
                localDb.userDao().insertAll(user);
            }
        });
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

    public void insertNote(Note note,Listener<Void> callback) {
        executor.execute(()->{
            localDb.noteDao().insertAll(note);
            mainHandler.post(()->{
                callback.onComplete(null);
            });
        });
    }




}
