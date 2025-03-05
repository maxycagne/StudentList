package com.example.studentlist.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentlist.Activities.ActivityAdd;
import com.example.studentlist.R;
import com.example.studentlist.databinding.FragmentAddBinding;


public class AddFragment extends Fragment {


    private FragmentAddBinding root;
    public AddFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = FragmentAddBinding.inflate(inflater,container,false);
        root.buttonNext.setOnClickListener(v->{startActivity(new Intent(getActivity(), ActivityAdd.class));});
        return root.getRoot();
    }
}