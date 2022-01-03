package com.example.calender;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calender.adapter.CalendarRecyclerAdapter;
import com.example.calender.databinding.FragmentCalenderBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalenderFragment extends Fragment {


    private FragmentCalenderBinding mBinding;
    private CalendarRecyclerAdapter adapter;
    private int backCount=0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentCalenderBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
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
    }

    private void setUpRecyclerView() {

        mBinding.calenderCellRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(),7));
        adapter = new CalendarRecyclerAdapter(this);
        mBinding.calenderCellRecyclerView.setAdapter(adapter);

    }

    public void setCalendar(int key)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");

        calendar.get(Calendar.MONTH+key++);


        calendar.add(Calendar.MONTH,key);

        String date = format.format(calendar.getTime());
        mBinding.monthYearTextView.setText(date);

        calendar.set(Calendar.DAY_OF_MONTH,1);
        int startingDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;

        calendar.set(Calendar.DAY_OF_MONTH-1,-startingDayOfWeek);

        int daysInMonth= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1;

        List<String> dayInMonth= new ArrayList<>();

        int counter = 1;
        for (int i=1; i<startingDayOfWeek;i++)
        {

                dayInMonth.add(" ");




        }

        for (int i=1; i<=daysInMonth;i++)
        {
            dayInMonth.add(String.valueOf(i));
        }
        adapter.setDaysInMonth(dayInMonth);
    }
}