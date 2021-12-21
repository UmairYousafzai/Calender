package com.example.calender;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calender.databinding.FragmentCalenderBinding;

public class CalenderFragment extends Fragment {


    FragmentCalenderBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentCalenderBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }
}