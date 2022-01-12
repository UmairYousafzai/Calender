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
import android.widget.Toast;

import com.example.calender.databinding.FragmentAddScheduleBinding;
import com.example.calender.models.Alarm;
import com.example.calender.models.CustomDate;
import com.example.calender.models.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class AddScheduleFragment extends Fragment {

    private FragmentAddScheduleBinding mBinding;

    private CustomDate customDate;
    private Calendar calendar;
    private int mMonth = -1, mDay = 0, mYear = 0, mHour = 0, mMinute = 0;
    private HashMap<String, Double> reminderHashMapForMinutes = new HashMap<>(), reminderHashMapForHours = new HashMap<>();

    boolean dateFlag = false;


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
                int month1 = calendar1.get(Calendar.MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (dayOfMonth < day || month < month1) {
                            Toast.makeText(requireContext(), "Please Valid Date", Toast.LENGTH_SHORT).show();
                            dateFlag = true;
                        } else {
                            dateFlag = false;

                            mYear = year;
                            mMonth = month;
                            mDay = dayOfMonth;

                            SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");

                            calendar1.set(year, month, dayOfMonth);


                            String date = format.format(calendar1.getTime());


                            mBinding.dateStart.setText(date);
                        }


                    }
                }, year, month1, day);
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


                        if (dayOfMonth < mDay || month < mMonth) {
                            dateFlag = true;

                            Toast.makeText(requireContext(), "Please Valid Date", Toast.LENGTH_SHORT).show();

                        } else {
                            dateFlag = false;

                        }

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
                        mHour = hourOfDay;
                        mMinute = minute;
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
//                        mHour = hourOfDay;
//                        mMinute = minute;
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

        mBinding.allDaySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                if(mDay==calendar.get(Calendar.DAY_OF_MONTH)&& mMonth==calendar.get(Calendar.MONTH)&& mYear== calendar.get(Calendar.YEAR))
                {
                    mBinding.allDaySwitch.setChecked(false);
                }
                else if (mBinding.allDaySwitch.isChecked())
                {
                    mBinding.timeEnd.setVisibility(View.GONE);
                    mBinding.timeStart.setVisibility(View.GONE);
                }
                else if (!mBinding.allDaySwitch.isChecked())
                {
                    mBinding.timeEnd.setVisibility(View.VISIBLE);
                    mBinding.timeStart.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void setAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);


        if (!dateFlag) {

            if (mBinding.allDaySwitch.isChecked()) {

                setAllDayAlarm(1);
            }
            else
            {
                setAllDayAlarm(2);
            }

        }

        Alarm alarm = new Alarm(alarmId, mHour, mMinute, "Alarm", true, true,
                true, true, true, true, true, true, true,
                true, false, false, mDay, mMonth, mYear);
        alarm.schedule(requireContext());

    }

    public void saveEvent()
    {
        String address= mBinding.addressEt.getText()!=null?mBinding.addressEt.getText().toString():"";
        String description= mBinding.descriptionEt.getText()!=null?mBinding.descriptionEt.getText().toString():"";
        Event event = new Event();

        event.setAddress(address);
        event.setAllDay(mBinding.allDaySwitch.isChecked());
        event.setCoordinates(" ");
        event.setDescription(description);
        event.setEventType(mBinding.spinnerEvent.getSelectedItem().toString());




    }

    public void setAllDayAlarm(int key) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);

        if (key==1)
        {
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        else
        {
            calendar.set(Calendar.HOUR_OF_DAY, mHour);
            calendar.set(Calendar.MINUTE, mMinute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }





        String reminder = mBinding.spinnerReminder.getSelectedItem().toString();
        if (reminder.equals("1 day before") || reminder.equals("12 hours before")) {

            if (reminder.equals("12 hours before")) {
                calendar.roll(Calendar.HOUR_OF_DAY, 12);
            }

            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH) - 1;
            if (mDay == 31 && mDay == 30 && mDay == 28) {
                calendar.roll(Calendar.MONTH, -1);
                mMonth = calendar.get(Calendar.MONTH);
                if (mMonth == 0) {
                    calendar.roll(Calendar.YEAR, -1);
                    mYear = calendar.get(Calendar.MONTH);
                }
            }

        } else {
            double hour = 0.0, minutes = 0.0;

            if (reminderHashMapForHours.get(mBinding.spinnerReminder.getSelectedItem().toString()) != null) {
                hour = reminderHashMapForHours.get(mBinding.spinnerReminder.getSelectedItem().toString());
            }

            if (reminderHashMapForMinutes.get(mBinding.spinnerReminder.getSelectedItem().toString()) != null) {
                minutes = reminderHashMapForMinutes.get(mBinding.spinnerReminder.getSelectedItem().toString());
            }


            if (hour != 0.0) {
                calendar.roll(Calendar.HOUR_OF_DAY, (int) -hour);
            } else if (minutes != 0.0) {
                calendar.set(Calendar.MINUTE, (int) (calendar.get(Calendar.MINUTE) - minutes));
            }


            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
            mHour = calendar.get(Calendar.HOUR_OF_DAY);

        }
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
                "3 hours before", "12 hours before", "1 day before"};


        double counter = 5.0, counter2 = 1.0;
        reminderHashMapForMinutes.put(reminderArray[1], 0.016);

        for (int i = 0; i < reminderArray.length; i++) {
            if (i < 9 && i > 1) {
                reminderHashMapForMinutes.put(reminderArray[i], counter);
                if (i < 8) {
                    counter += 5;
                } else {
                    counter += 15.0;
                }

            } else if (i > 8 && i < 12) {
                reminderHashMapForHours.put(reminderArray[i], counter2);
                counter2 += 1;
            } else if (i == 12) {
                reminderHashMapForHours.put(reminderArray[i], 12.0);

            } else if (i == 13) {
                reminderHashMapForHours.put(reminderArray[i], 24.0);

            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, eventTypeArray);
        mBinding.spinnerEvent.setAdapter(adapter);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, guestTypeArray);
        mBinding.spinnerGuestTypeSelection.setAdapter(adapter);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, reminderArray);
        mBinding.spinnerReminder.setAdapter(adapter);
    }
}