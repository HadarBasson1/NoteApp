package com.example.moveoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moveoapp.databinding.FragmentLandingPageBinding;
import com.example.moveoapp.databinding.FragmentLogInBinding;

public class LandingPageFragment extends Fragment {

    FragmentLandingPageBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentLandingPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.landingPageLoginBtn.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_logInFragment));

        binding.landingPageSingupBtn.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_singUpFragment));

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}