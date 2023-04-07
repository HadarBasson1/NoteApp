package com.example.moveoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moveoapp.databinding.FragmentNoteDetailsBinding;



public class NoteDetailsFragment extends Fragment {
    FragmentNoteDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoteDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String title=NoteDetailsFragmentArgs.fromBundle(getArguments()).getTitle();
        String body=NoteDetailsFragmentArgs.fromBundle(getArguments()).getBody();
        String date=NoteDetailsFragmentArgs.fromBundle(getArguments()).getDate();

        binding.noteDetailsTitle.setText(title);
        binding.noteDetailsBody.setText(body);
        binding.noteDetailsDate.setText(date);
        return view;
    }
}