package com.example.moveoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity2 extends AppCompatActivity {
    NavController navController;
    FloatingActionButton addBtn;
    static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         user = (User) getIntent().getSerializableExtra("user");
        NavHostFragment navHostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host2);
        navController = navHostFragment.getNavController();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.findViewById(R.id.noteMapFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_global_noteMapFragment);
            }
        });
        bottomNavigationView.findViewById(R.id.noteListFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_global_noteListFragment);
            }
        });
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        NavigationUI.setupActionBarWithNavController(this,navController);

        addBtn=findViewById(R.id.floatingActionButton);
//        ActionBar ab=getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                navController.popBackStack();
                navController.navigate(R.id.action_global_addNoteFragment);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            return navController.navigateUp();
        }

        else if(item.getItemId() == R.id.menu_logout) {
            Model.instance().logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
//        else {
//            return NavigationUI.onNavDestinationSelected(item,navController);
//        }


//        else if(item.getItemId()==R.id.noteListFragment){
//            Log.d("tag","hiiiii");
//            navController.navigate(R.id.action_global_noteListFragment);
//        }
//        else if(item.getItemId()==R.id.noteMapFragment){
//            navController.navigate(R.id.action_global_noteMapFragment);
//        }
//


        return super.onOptionsItemSelected(item);
    }




}