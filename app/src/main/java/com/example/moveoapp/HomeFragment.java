package com.example.moveoapp;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.Model.User;
import com.example.moveoapp.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment {
  FragmentHomeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String imageUrl=null;
        if(MainActivity2.user==null)
            binding.homeWelcomMsg.setText("Welcome");
        else{
            binding.homeWelcomMsg.setText("Welcome "+MainActivity2.user.getName());
            imageUrl = MainActivity2.user.getImgUrl();
        }
        if (imageUrl  != null && imageUrl.length() > 5) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.profile).into(binding.homeImage);
        }else{
            binding.homeImage.setImageResource(R.drawable.profile);
        }

        FloatingActionButton addBtn = requireActivity().findViewById(R.id.floatingActionButton);
        addBtn.setVisibility(View.VISIBLE);

        return view;
    }


}