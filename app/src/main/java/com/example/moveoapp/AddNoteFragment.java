package com.example.moveoapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.Model.Note;
import com.example.moveoapp.databinding.FragmentAddNoteBinding;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;


public class AddNoteFragment extends Fragment {
FragmentAddNoteBinding binding;
FusedLocationProviderClient client;
    String Latitude;
    String Longitude;
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
        client = LocationServices
                .getFusedLocationProviderClient(
                        getActivity());

        binding.addNoteFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // check condition
                    if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                        // When permission is granted
                        // Call method
                        getCurrentLocation(v);
                    }
                    else {
                        // When permission is not granted
                        // Call method
                        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION },100);
                    }

            }
        });

        FloatingActionButton addBtn = requireActivity().findViewById(R.id.floatingActionButton);
        addBtn.setVisibility(View.GONE);

        return view;
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
            // Call  method
            getCurrentLocation(getView());
        }
        else {
            // When permission are denied
            // Display toast
            Toast.makeText(getActivity(),"Permission denied",Toast.LENGTH_SHORT).show();
        }
    }



    @SuppressLint("MissingPermission")
    private void getCurrentLocation(View v)
    {
        // Initialize Location manager
        LocationManager locationManager= (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Check condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            // Get last location
            client.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(
                                @NonNull Task<Location> task)
                        {
                            // Initialize location
                            Location location= task.getResult();
                            // Check condition
                            if (location != null) {
                                // When location result is not
                                // null set latitude
                                Latitude = String.valueOf(location.getLatitude());
                                // set longitude
                                Longitude =String.valueOf(location.getLongitude());
                                Log.d("tag","lati"+ Latitude +"********************");
                                Log.d("tag","long"+ Longitude +"********************");
                                String title = binding.addNoteFragmentTitle.getText().toString();
                                String body = binding.addNoteFragmentBody.getText().toString();
                                String key = RandomKeyGenerator.generateRandomKey();
                                String formattedDateTime="";
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    LocalDateTime currentDateTime = LocalDateTime.now();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    formattedDateTime = currentDateTime.format(formatter);
                                }

                                Note note = new Note(title,formattedDateTime,body,key,MainActivity2.user.getEmail(), Latitude, Longitude);
                                Model.instance().insertNote(note, new Model.Listener<Void>() {
                                    @Override
                                    public void onComplete(Void data) {
                                        // Create an AlertDialog.Builder object
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        // Set the message to display in the dialog box
                                        builder.setMessage("Note successfully added");
                                        // Create and show the dialog box
                                        AlertDialog dialog = builder.create();
                                        dialog.show();

                                        new android.os.Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                NavController navController = Navigation.findNavController(v);
                                                navController.popBackStack();
                                            }
                                        }, 1000);

//                                        NavController navController = Navigation.findNavController(v);
//                                        navController.popBackStack();
                                    }
                                });


                            }
                            else {
                                // When location result is null
                                // initialize location request
                                LocationRequest locationRequest= new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                                // Initialize location call back
                                LocationCallback locationCallback= new LocationCallback() {
                                    @Override
                                    public void
                                    onLocationResult(LocationResult locationResult)
                                    {
                                        // Initialize
                                        // location
                                        Location location1= locationResult.getLastLocation();
                                        // Set latitude
                                        Latitude = String.valueOf(location1.getLatitude());
                                        Longitude =String.valueOf(location1.getLongitude());
                                    }
                                };
                                // Request location updates
                                client.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper());
                            }
                        }
                    });
        }

    }
}