package com.example.moveoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.Model.Note;
import com.example.moveoapp.Model.UserDao;
import com.example.moveoapp.databinding.FragmentNoteListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class NoteListFragment extends Fragment {

    FragmentNoteListBinding binding;
    NoteRecyclerAdapter adapter;
    List<Note> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoteListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Model.instance().getAllNotes((list) -> {
            data = list;
            adapter.setData(data);
            adapter.setOnItemClickListener(new NoteRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {

                    Log.d("TAG", "Row was clicked " + pos);
                    Note note = data.get(pos);
                    NoteListFragmentDirections.ActionNoteListFragmentToNoteDetailsFragment action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailsFragment(note);
                    Navigation.findNavController(view).navigate(action);
                }
            });

            if (data.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("There are no notes yet");
                // Create and show the dialog box
                builder.setPositiveButton("Add now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(getView()).navigate(R.id.action_global_addNoteFragment);
                    }
                });

                // Set a negative button and its listener
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        binding.notelist.setHasFixedSize(true);
        binding.notelist.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NoteRecyclerAdapter(getLayoutInflater(), data);
        binding.notelist.setAdapter(adapter);

        FloatingActionButton addBtn = requireActivity().findViewById(R.id.floatingActionButton);
        addBtn.setVisibility(View.VISIBLE);


        return view;
    }


}