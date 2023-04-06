package com.example.moveoapp.Model;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class FirebaseModel {

    static FirebaseAuth mAuth;
    static FirebaseUser user;

    FirebaseModel(){
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
    }

    public static boolean currentUser(){
            return user!=null;
    }

    public static void register(String email, String password, Model.Listener<FirebaseUser> listener){
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((OnCompleteListener<AuthResult>) new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();
                            listener.onComplete(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "createUserWithEmail:failed from model firebase");
                            listener.onComplete(null);

                        }
                    }

                });
    }

    public static void login(String email, String password, Model.Listener<FirebaseUser> listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                             user = mAuth.getCurrentUser();
                            listener.onComplete(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            listener.onComplete(null);
                        }
                    }
                });
    }

    public static void logOut() {
        mAuth.signOut();
        user=null;
    }
}
