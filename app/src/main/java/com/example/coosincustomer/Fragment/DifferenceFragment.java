package com.example.coosincustomer.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coosincustomer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DifferenceFragment extends Fragment {


    public DifferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_difference, container, false);
    }

}
