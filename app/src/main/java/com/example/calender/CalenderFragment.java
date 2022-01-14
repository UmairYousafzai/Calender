package com.example.calender;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calender.adapter.CalendarRecyclerAdapter;
import com.example.calender.clickListener.OnCLickListener;
import com.example.calender.databinding.FragmentCalenderBinding;
import com.example.calender.models.CustomDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalenderFragment extends Fragment {


    private FragmentCalenderBinding mBinding;
    private CalendarRecyclerAdapter adapter;
    private NavController navController;
    private int backCount=0 , day =-1, month=-1, year=-1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentCalenderBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         navController = NavHostFragment.findNavController(this);


    }

    @Override
    public void onResume() {
        super.onResume();

        setUpRecyclerView();


        setCalendar(0);
        btnListener();
    }

    private void btnListener() {

        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backCount = backCount-1;
                setCalendar(backCount);

            }
        });

        mBinding.btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backCount = backCount+1;
                setCalendar(backCount);

            }
        });

        adapter.setOnClickListener(new OnCLickListener() {
            @Override
            public void onClick(CustomDate date) {

                CalenderFragmentDirections.ActionCalenderFragmentToAddScheduleFragment action = CalenderFragmentDirections.actionCalenderFragmentToAddScheduleFragment(date);

                navController.navigate(action);
            }
        });
    }

    private void setUpRecyclerView() {

        mBinding.calenderCellRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(),7));
        adapter = new CalendarRecyclerAdapter(requireContext(),this);
        mBinding.calenderCellRecyclerView.setAdapter(adapter);

    }

    public void setCalendar(int key)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");

        

        day = calendar.get(Calendar.DAY_OF_MONTH);
         month =calendar.get(Calendar.MONTH);
         year =calendar.get(Calendar.YEAR);



        calendar.add(Calendar.MONTH,month+key);

        String date = format.format(calendar.getTime());
        mBinding.monthYearTextView.setText(date);

        calendar.set(Calendar.DAY_OF_MONTH,month);
        int startingDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;

        calendar.set(Calendar.DAY_OF_MONTH-1,-startingDayOfWeek);

        int daysInMonth= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<CustomDate> customDateList= new ArrayList<>();

        int counter = 1;
        for (int i=1; i<startingDayOfWeek;i++)
        {
            CustomDate model = new CustomDate(0,0,-1);

                customDateList.add(model);




        }

        for (int i=1; i<=daysInMonth;i++)
        {
            CustomDate model = new CustomDate(i,year,month);
            if (i==day)
            {
                model.setCurrentDate(true);
            }

            customDateList.add(model);
        }
        adapter.setCustomDateList(customDateList);
    }
}