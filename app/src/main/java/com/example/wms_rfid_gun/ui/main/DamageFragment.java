package com.example.wms_rfid_gun.ui.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wms_rfid_gun.R;

public class DamageFragment extends Fragment {
    public final static String TAG = DepartureFragment.class.getSimpleName();

    public DamageFragment() {

    }

    public static DamageFragment newInstance() {
        DamageFragment fragment = new DamageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_damage, container, false);
    }

}