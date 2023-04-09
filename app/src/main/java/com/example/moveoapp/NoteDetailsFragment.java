package com.example.moveoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.Model.Note;
import com.example.moveoapp.databinding.FragmentNoteDetailsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class NoteDetailsFragment extends Fragment {
    FragmentNoteDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoteDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        String title=NoteDetailsFragmentArgs.fromBundle(getArguments()).getTitle();
//        String body=NoteDetailsFragmentArgs.fromBundle(getArguments()).getBody();
//        String date=NoteDetailsFragmentArgs.fromBundle(getArguments()).getDate();
        Note note= NoteDetailsFragmentArgs.fromBundle(getArguments()).getNote();
        binding.noteDetailsTitle.setText(note.getTitle());
        binding.noteDetailsBody.setText(note.getBody());
        binding.noteDetailsDate.setText(note.getDate());

        binding.noteDetailsDeleteBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                // Set the message to display in the dialog box
                builder.setMessage("Warning:Remove the note?");
                // Create and show the dialog box

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Model.instance().delete(note,(Void)->{
                            Navigation.findNavController(v).popBackStack();
                        });
                    }
                });

                // Set a negative button and its listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        binding.noteDetailsEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteDetailsFragmentDirections.ActionNoteDetailsFragmentToEditNoteFragment action = NoteDetailsFragmentDirections.actionNoteDetailsFragmentToEditNoteFragment(note);
                Navigation.findNavController(v).navigate(action);
            }
        });

        FloatingActionButton addBtn = requireActivity().findViewById(R.id.floatingActionButton);
        addBtn.setVisibility(View.GONE);

        return view;
    }
}