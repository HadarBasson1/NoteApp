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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executor;

public class FirebaseModel {

    static FirebaseAuth mAuth;
    static FirebaseUser user;
    static FirebaseFirestore db;


    FirebaseModel(){
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
    }



    public static String currentUser(){
        if(user!=null)
            return user.getEmail();
        return null;
    }

    public static void register(String email, String password,String name, Model.Listener<User> listener){
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((OnCompleteListener<AuthResult>) new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();
                            User new_user = new User(name,email);
                            listener.onComplete(new_user);
//                            db.collection(User.COLLECTION).document(new_user.getEmail()).set(new_user.toJson())
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            listener.onComplete(new_user);
//                                        }
//                                    });
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

    public static void findNameByEmail(String email, Model.Listener<String> listener) {
        DocumentReference docRef = db.collection("users").document("nono@gmail.com");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        listener.onComplete(document.get("name").toString());
                        Log.d(TAG, "DocumentSnapshot data:********************** " + document.get("name").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


//    public void addUser(email,name Model.Listener<Void> listener) {
//        db.collection(User.COLLECTION).document(user.getId()).set(user.toJson())
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        listener.onComplete(null);
//                    }
//                });
//    }
}
