package com.example.moveoapp;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.Model.User;
import com.example.moveoapp.databinding.ActivityMainBinding;
import com.example.moveoapp.databinding.FragmentLandingPageBinding;
import com.example.moveoapp.databinding.FragmentSingUpBinding;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class SingUpFragment extends Fragment {
    FragmentSingUpBinding binding;
    String emailInput;
    String passwordInput;
    String nameInput;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isImgSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.singUpGallery.setImageBitmap(result);
                    isImgSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.singUpGallery.setImageURI(result);
                    isImgSelected = true;
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSingUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailInput= binding.singUpEmailInput.getText().toString();
                passwordInput= binding.singUpPasswordInput.getText().toString();
                nameInput= binding.singUpNameInput.getText().toString();
                Model.instance().regiser(emailInput, passwordInput,nameInput, new Model.Listener<User>() {
                    @Override
                    public void onComplete(User user) {
                        if(user!=null){
                            if (isImgSelected){
                                binding.singUpGallery.setDrawingCacheEnabled(true);
                                binding.singUpGallery.buildDrawingCache();
                                Bitmap bitmap = ((BitmapDrawable) binding.singUpGallery.getDrawable()).getBitmap();
                                Model.instance().uploadImage(user.email, bitmap, url->{
                                    if (url != null){
                                        user.setImgUrl(url);
                                    }
                                    Model.instance().insertUser(user, (Void)->{
                                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity2.class);
                                    intent.putExtra("user",user);
                                    startActivity(intent);
                                    getActivity().finish();
                                    });
                                });
                            }else {
                                Model.instance().insertUser(user, (Void)->{
                                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity2.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                                getActivity().finish();
                                });
                            }


                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        binding.singUpCamera.setOnClickListener(v -> {
            cameraLauncher.launch(null);
        });

        binding.singUpGallery.setOnClickListener(v -> {
            galleryLauncher.launch("image/*");
        });

        return view;
    }
}