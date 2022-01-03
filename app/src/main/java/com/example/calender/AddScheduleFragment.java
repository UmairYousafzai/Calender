package com.example.calender;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.calender.databinding.FragmentAddScheduleBinding;

public class AddScheduleFragment extends Fragment {

    private FragmentAddScheduleBinding mBinding;
    private String[] eventTypeArray = {"Call","Meeting","Task","Travel","Reminder"};
    private String[] guestTypeArray = {"Doctor","Farm","Hospital","Customer","User"};
    private String[] reminderArray = {"Call","Meeting","Task","Travel","Reminder"};


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentAddScheduleBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpSpinners();
    }

    @Override
    public void onResume() {
        super.onResume();



    }

    private void setUpSpinners() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,eventTypeArray);

        mBinding.spinnerEvent.setAdapter(adapter);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,guestTypeArray);
        mBinding.spinnerGuestTypeSelection.setAdapter(adapter);
    }
}