package com.example.moveoapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.Model.Note;
import com.example.moveoapp.databinding.FragmentEditNoteBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditNoteFragment extends Fragment {
    FragmentEditNoteBinding binding;
    FusedLocationProviderClient client;
    Note note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditNoteBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        note= EditNoteFragmentArgs.fromBundle(getArguments()).getNote();
        binding.editNotesDate.setText(note.getDate());
        binding.editNoteTitle.setText(note.getTitle());
        binding.editTextTextMultiLine2.setText(note.getBody());


        FloatingActionButton addBtn = requireActivity().findViewById(R.id.floatingActionButton);
        addBtn.setVisibility(View.GONE);
        binding.editNoteSaveBtn.setOnClickListener((v)->locationPremission());

        return view;
    }


    public void locationPremission(){
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            editNote();
        }
        else {
            // When permission is not granted
            requestPermissions(new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION },100);
        }
    }

    @SuppressLint("MissingPermission")
    private  void editNote()
    {
        // Initialize Location manager
        LocationManager locationManager= (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Check condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled - Get last location
            Task<Location> note_successfully_edited = client.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(
                                @NonNull Task<Location> task) {
                            // Initialize location
                            Location location = task.getResult();
                            if (location != null) {
                                note.setLatitude(String.valueOf(location.getLatitude()));
                                note.setLongitude(String.valueOf(location.getLongitude()));
                                note.setTitle(binding.editNoteTitle.getText().toString());
                                note.setBody(binding.editTextTextMultiLine2.getText().toString());
                                note.setDate(binding.editNoteTitle.getText().toString());
                                String formattedDateTime = "";
                                // current date
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    LocalDateTime currentDateTime = LocalDateTime.now();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    formattedDateTime = currentDateTime.format(formatter);
                                }
                                note.setDate(formattedDateTime);
                                Model.instance().insertNote(note, new Model.Listener<Void>() {
                                    @Override
                                    public void onComplete(Void data) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage("Note successfully edited");
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                NavController navController = Navigation.findNavController(getView());
                                                navController.popBackStack();
                                            }
                                        }, 1000);
                                    }
                                });


                            } else {
                                // When location result is null - initialize location request
                                LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                                // Initialize location call back
                                LocationCallback locationCallback = new LocationCallback() {
                                    @Override
                                    public void
                                    onLocationResult(LocationResult locationResult) {
                                        // Initialize location
                                        Location location1 = locationResult.getLastLocation();
                                        // Set latitude + longitude
                                        note.setLatitude(String.valueOf(location1.getLatitude()));
                                        note.setLongitude(String.valueOf(location1.getLongitude()));
                                    }
                                };
                                // Request location updates
                                client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                            }
                        }

                    });
        }

    }





    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            // When permission are granted
            editNote();
        }
        else {
            // When permission are denied - Display toast
            Toast.makeText(getActivity(),"Permission denied",Toast.LENGTH_SHORT).show();
        }
    }
}