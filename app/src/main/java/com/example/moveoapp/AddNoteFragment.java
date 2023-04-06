package com.example.moveoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.Model.Note;
import com.example.moveoapp.databinding.FragmentAddNoteBinding;


public class AddNoteFragment extends Fragment {
FragmentAddNoteBinding binding;

    public AddNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddNoteBinding.inflate(inflater,container, false);
        View view=binding.getRoot();
        binding.addNoteFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.addNoteFragmentTitle.getText().toString();
                String body = binding.addNoteFragmentBody.getText().toString();
                Note note = new Note(title,"",body,"1","","1224","");
                Model.instance().insertNote(note, new Model.Listener<Void>() {
                    @Override
                    public void onComplete(Void data) {
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_global_noteListFragment);
                    }
                });
            }
        });

        return view;
    }
}