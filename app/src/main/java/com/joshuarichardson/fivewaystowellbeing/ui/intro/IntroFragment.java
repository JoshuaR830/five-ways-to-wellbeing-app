package com.joshuarichardson.fivewaystowellbeing.ui.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joshuarichardson.fivewaystowellbeing.R;

import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IntroFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        return view;
    }
}