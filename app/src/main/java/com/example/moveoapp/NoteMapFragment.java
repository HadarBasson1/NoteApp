package com.example.moveoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moveoapp.Model.Model;
import com.example.moveoapp.Model.Note;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class NoteMapFragment extends Fragment {
    List<Note> data;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.clear();
            Model.instance().getAllNotes((list)->{
                data=list;
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
                else {
                    for (Note note : data) {
                        LatLng location=new LatLng(Double.parseDouble(note.getLatitude()), Double.parseDouble(note.getLongitude()));
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(location).title(note.getTitle());
                        googleMap.addMarker(markerOptions);

                        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                NoteMapFragmentDirections.ActionNoteMapFragmentToNoteDetailsFragment action = NoteMapFragmentDirections.actionNoteMapFragmentToNoteDetailsFragment(note);
                                Navigation.findNavController(getView()).navigate(action);
                            }
                        });


                    }
                }

//                LatLng sydney = new LatLng(-34, 151);
//                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            });


        }
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_note_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_note_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Model.instance().getAllNotes((list)->{data=list;
        });
        super.onAttach(context);
    }
}