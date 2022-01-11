package com.example.calender;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.calender.databinding.FragmentAddScheduleBinding;
import com.example.calender.models.Alarm;
import com.example.calender.models.CustomDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class AddScheduleFragment extends Fragment {

    private FragmentAddScheduleBinding mBinding;

    private CustomDate customDate;
    private Calendar calendar;
    private int mMonth = -1, mDay = 0, mYear = 0 , mHour=0, mMinute=0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentAddScheduleBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendar = Calendar.getInstance();
        if (getArguments() != null) {
            customDate = AddScheduleFragmentArgs.fromBundle(getArguments()).getDate();
            setUpDate();
        }

        setUpSpinners();
    }

    @Override
    public void onResume() {
        super.onResume();


        btnListener();

    }

    private void btnListener() {

        mBinding.dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar1 = Calendar.getInstance();
                int year = calendar1.get(Calendar.YEAR);
                int day = calendar1.get(Calendar.DAY_OF_MONTH);
                int month = calendar1.get(Calendar.MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mYear=year;
                        mMonth= month;
                        mDay = dayOfMonth;
//                        int checkMonth = (month+1) % 10, checkday = (dayOfMonth % 10);
//
//                        String mMonth, mDay;
//                        if (checkMonth > 0 && month < 9) {
//                            mMonth = "0" + (month + 1);
//                        } else {
//                            mMonth = String.valueOf(month + 1);
//
//                        }
//
//                        if (checkday > 0 && dayOfMonth < 10) {
//                            mDay = "0" + (dayOfMonth);
//
//                        } else {
//                            mDay = String.valueOf(dayOfMonth);
//
//                        }
                        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");

                        calendar1.set(year, month, dayOfMonth);


                        String date = format.format(calendar1.getTime());


                        mBinding.dateStart.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        mBinding.dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        int checkMonth = (month+1) % 10, checkday = (dayOfMonth % 10);
//
//                        String mMonth, mDay;
//                        if (checkMonth > 0 && month < 9) {
//                            mMonth = "0" + (month + 1);
//                        } else {
//                            mMonth = String.valueOf(month + 1);
//
//                        }
//
//                        if (checkday > 0 && dayOfMonth < 10) {
//                            mDay = "0" + (dayOfMonth);
//
//                        } else {
//                            mDay = String.valueOf(dayOfMonth);
//
//                        }
                        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");

                        calendar.set(year, month, dayOfMonth);


                        String date = format.format(calendar.getTime());


                        mBinding.dateStart.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        mBinding.timeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int Hour = calendar.get(Calendar.HOUR_OF_DAY);
                int Minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour= hourOfDay;
                        mMinute =minute;
                        String openingTime = hourOfDay + " : " + minute;
                        mBinding.timeStart.setText(openingTime);
                    }
                }, Hour, Minute, false);
                timePickerDialog.show();
            }
        });

        mBinding.timeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int Hour = calendar.get(Calendar.HOUR_OF_DAY);
                int Minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour= hourOfDay;
                        mMinute =minute;
                        String openingTime = hourOfDay + " : " + minute;
                        mBinding.timeEnd.setText(openingTime);
                    }
                }, Hour, Minute, false);
                timePickerDialog.show();
            }
        });

        mBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAlarm();
            }
        });

    }

    private void setAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);
        String notificationTime= mBinding.spinnerReminder.getSelectedItem().toString();

        Calendar calendar= Calendar.getInstance();


        if (mBinding.allDaySwitch.isChecked())
        {
            calendar.set(Calendar.HOUR_OF_DAY,8);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);


            int hour = Integer.parseInt(notificationTime.substring(0,2));
            calendar.set(Calendar.HOUR_OF_DAY,hour-8 );
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            hour= hour-2;
            if (hour>=0)
            {

            }

        }

//
//
//        Alarm alarm = new Alarm(alarmId,mHour,mMinute,"Alarm",true,true,
//                true,true,true,true,true,true,true,
//                true,false,false,mDay,mMonth,mYear);
//        alarm.schedule(requireContext());

    }

    private void setUpDate() {

        mMonth = customDate.getMonth();
        mDay = customDate.getDay();
        mYear = customDate.getYear();


        calendar.set(mYear, mMonth, mDay);

        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");
        String date = format.format(calendar.getTime());

        mBinding.dateStart.setText(date);
        mBinding.dateEnd.setText(date);
    }

    private void setUpSpinners() {

         final String[] eventTypeArray = {"Call", "Meeting", "Task", "Travel", "Reminder"};
         final String[] guestTypeArray = {"Doctor", "Farm", "Hospital", "Customer", "User"};
         final String[] reminderArray = {"At time event", "1 minute before", "5 minute before", "10 minutes before", "15 minutes before",
                 "20 minutes before", "25 minutes before", "30 minutes before", "45 minutes before", "1 hour before", "2 hours before",
                 "3 hours before", "12 hours before", "1 day before", "2 days before" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, eventTypeArray);
        mBinding.spinnerEvent.setAdapter(adapter);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, guestTypeArray);
        mBinding.spinnerGuestTypeSelection.setAdapter(adapter);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, reminderArray);
        mBinding.spinnerReminder.setAdapter(adapter);
    }
}