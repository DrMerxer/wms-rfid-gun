package com.example.wms_rfid_gun.ui.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wms_rfid_gun.R;


public class DepartureFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    public final static String TAG = DepartureFragment.class.getSimpleName();

    public DepartureFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DepartureFragment newInstance() {
        DepartureFragment fragment = new DepartureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_departure, container, false);
    }

}
