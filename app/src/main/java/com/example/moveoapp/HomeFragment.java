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
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment {
  FragmentHomeBinding binding;
    String name;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        if(MainActivity2.user==null)
            binding.homeWelcomMsg.setText("Welcome");
        else{
            binding.homeWelcomMsg.setText("Welcome "+MainActivity2.user.getName());
            String imageUrl = MainActivity2.user.getImgUrl();
//                Picasso.get().invalidate(user.getImgUrl()); // clear the cache for the old URL
//                String imageUrl = user.getImgUrl() + System.currentTimeMillis();
            if (imageUrl  != null && imageUrl.length() > 5) {
                Picasso.get().load(imageUrl).placeholder(R.drawable.note).into(binding.homeImage);
            }else{
                binding.homeImage.setImageResource(R.drawable.note);
            }
        }

        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

}