package com.example.moveoapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.databinding.ActivityMainBinding;
import com.example.moveoapp.databinding.FragmentLandingPageBinding;
import com.example.moveoapp.databinding.FragmentSingUpBinding;
import com.google.firebase.auth.FirebaseUser;

public class SingUpFragment extends Fragment {
    FragmentSingUpBinding binding;
    String emailInput;
    String passwordInput;


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
                Model.instance().regiser(emailInput, passwordInput, new Model.Listener<FirebaseUser>() {
                    @Override
                    public void onComplete(FirebaseUser data) {
                        if(data!=null){
                            Log.d(TAG, "createUserWithEmail:success From SingUp Page!!!!!!!!");
                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity2.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        return view;
    }
}