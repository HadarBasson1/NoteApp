package com.example.moveoapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.Model.User;
import com.example.moveoapp.databinding.FragmentLogInBinding;
import com.example.moveoapp.databinding.FragmentSingUpBinding;
import com.google.firebase.auth.FirebaseUser;

public class LogInFragment extends Fragment {

    FragmentLogInBinding binding;
    String emailInput;
    String passwordInput;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.loginFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailInput= binding.loginFragmentEmailInput.getText().toString();
                passwordInput= binding.loginFragmentPasswordInput.getText().toString();

                //login verification
                if(TextUtils.isEmpty(emailInput)){
                    Toast.makeText(getContext(),"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordInput)){
                    Toast.makeText(getContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                Model.instance().login(emailInput, passwordInput, new
                        Model.Listener<FirebaseUser>() {
                            @Override
                            public void onComplete(FirebaseUser user) {
                                if(user!=null){
                                    String email = user.getEmail();
                                    Model.instance().getUserByEmail(email, new Model.Listener<User>() {
                                        @Override
                                        public void onComplete(User user1) {
                                            Log.d(TAG, "createUserWithEmail:success From SingUp Page!!!!!!!!");
                                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity2.class);
                                            intent.putExtra("user",user1);
                                            startActivity(intent);
                                            getActivity().finish();
                                        }
                                    });

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