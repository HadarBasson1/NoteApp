package com.example.moveoapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
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

import java.util.List;


public class NoteListFragment extends Fragment {

    FragmentNoteListBinding binding;
    NoteRecyclerAdapter adapter;
//    NListFragmentViewModel viewModel;
    List<Note> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        reloadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoteListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Model.instance().getAllNotes((list)->{
            data=list;
            adapter.setData(data);
        });
        binding.notelist.setHasFixedSize(true);
        binding.notelist.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NoteRecyclerAdapter(getLayoutInflater(),data);
        binding.notelist.setAdapter(adapter);

        adapter.setOnItemClickListener(new NoteRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {

                Log.d("TAG", "Row was clicked " + pos);
                Note note = data.get(pos);
                NoteListFragmentDirections.ActionNoteListFragmentToNoteDetailsFragment action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailsFragment(note);
                Navigation.findNavController(view).navigate(action);
            }
        });

//        View addButton = view.findViewById(R.id.btnAdd);
//        NavDirections action = StudentsListFragmentDirections.actionGlobalAddStudentFragment();
//        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));

//        binding.progressBar.setVisibility(View.GONE);

//        viewModel.getData().observe(getViewLifecycleOwner(),list->{
//            adapter.setData(list);
//        });

//        Model.instance().EventStudentsListLoadingState.observe(getViewLifecycleOwner(), status->{
//            binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
//        });
//
//        binding.swipeRefresh.setOnRefreshListener(()->{
//            reloadData();
//        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        reloadData();
//        viewModel = new ViewModelProvider(this).get(StudentsListFragmentViewModel.class);
    }

    void reloadData(){
    }
}