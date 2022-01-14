package com.example.calender;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.calender.adapter.SelectedGuestAdapter;
import com.example.calender.databinding.CustomGuestDialogBinding;
import com.example.calender.databinding.FragmentAddScheduleBinding;
import com.example.calender.models.Alarm;
import com.example.calender.models.CustomDate;
import com.example.calender.models.Event;
import com.example.calender.models.EventGuest;
import com.example.calender.models.GetSupplierModel;
import com.example.calender.models.Time;
import com.example.calender.network.ApiClient;
import com.example.calender.util.DataRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddScheduleFragment extends Fragment {

    private FragmentAddScheduleBinding mBinding;

    private CustomDate customDate;
    private Calendar calendar;
    private int mMonthStart = -1, mDayStart = 0, mYearStart = 0, mMonthEnd = -1, mDayEnd = 0, mYearEnd = 0;
    private int mHourStart = -1, mMinuteStart = -1, mHourEnd = -1, mMinuteEnd = -1;
    private final HashMap<String, Double> reminderHashMapForMinutes = new HashMap<>();
    private final HashMap<String, Double> reminderHashMapForHours = new HashMap<>();
    private final HashMap<String, Integer> supplierIDHashMap = new HashMap<>();
    private List<String> supplierNameList = new ArrayList<>();
    private boolean dateFlag = false;
    private String selectedSuppliers =" ";
    private SelectedGuestAdapter adapter;
    private DataRepository dataRepository;
    private List<EventGuest> eventGuestList = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentAddScheduleBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataRepository = new DataRepository(requireContext());
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

                            mYearStart = year;
                            mMonthStart = month;
                            mDayStart = dayOfMonth;

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

                        mDayEnd = dayOfMonth;
                        mMonthEnd = month;
                        mYearEnd = year;

                        if (dayOfMonth < mDayStart || month < mMonthStart) {
                            dateFlag = true;

                            Toast.makeText(requireContext(), "Please Valid Date", Toast.LENGTH_SHORT).show();

                        } else {
                            dateFlag = false;

                        }

                        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");

                        calendar.set(year, month, dayOfMonth);


                        String date = format.format(calendar.getTime());


                        mBinding.dateEnd.setText(date);
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
                        mHourStart = hourOfDay;
                        mMinuteStart = minute;
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
                        mHourEnd = hourOfDay;
                        mMinuteEnd = minute;
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

                if (mDayStart == calendar.get(Calendar.DAY_OF_MONTH) && mMonthStart == calendar.get(Calendar.MONTH) && mYearStart == calendar.get(Calendar.YEAR)) {
                    mBinding.allDaySwitch.setChecked(false);
                } else if (mBinding.allDaySwitch.isChecked()) {
                    mBinding.timeEnd.setVisibility(View.GONE);
                    mBinding.timeStart.setVisibility(View.GONE);
                } else if (!mBinding.allDaySwitch.isChecked()) {
                    mBinding.timeEnd.setVisibility(View.VISIBLE);
                    mBinding.timeStart.setVisibility(View.VISIBLE);
                }

            }
        });

        mBinding.spinnerGuestTypeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0: {
                        getSupplier("Dr");
                        break;
                    }
                    case 1: {
                        getSupplier("F");
                        break;
                    }
                    case 2: {
                        getSupplier("H");
                        break;
                    }
                    case 3: {
                        getSupplier("C");
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        mBinding.spinnerGuestsSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedSuppliers.add(supplierNameList.get(position));
//                adapter.setSelectedGuestList(selectedSuppliers);
//
//                EventGuest eventGuest= new EventGuest();
//                eventGuest.setSupplierID(supplierIDHashMap.get(supplierNameList.get(position)));
//                eventGuest.setEventGuestType(mBinding.spinnerGuestTypeSelection.getSelectedItem().toString());
//                eventGuest.setPosition(position);
//                eventGuestList.add(eventGuest);
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.spinnerGuestsSelection.getSelectedItem() != null) {
                    String name =mBinding.spinnerGuestsSelection.getSelectedItem().toString();
                    selectedSuppliers = selectedSuppliers+name+", ";
                    mBinding.tvSelectedGuest.setText(selectedSuppliers);
                    EventGuest eventGuest = new EventGuest();
                    eventGuest.setSupplierID(supplierIDHashMap.get(name));
                    eventGuest.setEventGuestType(mBinding.spinnerGuestTypeSelection.getSelectedItem().toString());
                    eventGuest.setGuestName(name);
                    eventGuestList.add(eventGuest);
                }


            }
        });

        mBinding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showSelectedGuestDialog();

            }
        });



    }

    private void showSelectedGuestDialog() {

        CustomGuestDialogBinding binding= CustomGuestDialogBinding.inflate(getLayoutInflater());

        AlertDialog alertDialog =new AlertDialog.Builder(requireContext())
                .setView(binding.getRoot())
                .show();

        binding.selectedGuestRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new SelectedGuestAdapter();

        binding.selectedGuestRecycler.setAdapter(adapter);
        adapter.setSelectedGuestList(eventGuestList);


        adapter.SetOnClickListener(new SelectedGuestAdapter.SetOnClickListener() {
            @Override
            public void onClick(EventGuest eventGuest) {

                String name =eventGuest.getGuestName();
                selectedSuppliers = selectedSuppliers.replace(", "+name,"");
                mBinding.tvSelectedGuest.setText(selectedSuppliers);
                eventGuestList.remove(eventGuest);

            }
        });
    }


    private void setAlarm() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        saveEvent();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Event event = dataRepository.getLastEvent();
                saveEventGuest(event.getEventID());
                saveTime(event.getEventID());

                if (!dateFlag) {

                    if (mBinding.allDaySwitch.isChecked()) {

                        setAllDayAlarm(1);
                    } else {
                        setAllDayAlarm(2);
                    }

                }

                progressDialog.dismiss();
            }
        }, 1000);

        Alarm alarm = new Alarm(alarmId, mHourStart, mMinuteStart, "Alarm", true, mDayStart, mMonthStart, mYearStart);
        alarm.schedule(requireContext());

    }

    private void saveTime(int eventID) {
        List<Time> timeList = new ArrayList<>();

        String date = "";
        String timeString = "";


        if (!mBinding.allDaySwitch.isChecked()) {

            if (mDayStart != mDayEnd) {
                for (int i = mDayStart; i <= mDayEnd; i++) {
                    Time time = new Time();
                    date = i + "-" + mMonthStart + "-" + mYearStart;
                    if (i == mDayStart) {
                        timeString = mHourStart + "-" + mMinuteStart;
                        time.setTime(timeString);
                    } else if (i == mDayEnd) {
                        timeString = mHourEnd + "-" + mMinuteEnd;
                        time.setTime(timeString);
                    }
                    time.setDate(date);

                    time.setEventID(eventID);

                    timeList.add(time);
                }
                dataRepository.insertTime(timeList);
            } else {

                for (int i = 0; i < 2; i++) {
                    Time time = new Time();
                    date = mDayStart + "-" + mMonthStart + "-" + mYearStart;
                    if (i == 0) {
                        timeString = mHourStart + "-" + mMinuteStart;
                        time.setTime(timeString);
                    } else if (i == 1) {
                        timeString = mHourEnd + "-" + mMinuteEnd;
                        time.setTime(timeString);
                    }
                    time.setDate(date);

                    time.setEventID(eventID);
                    timeList.add(time);

                }
                dataRepository.insertTime(timeList);
//                date= mDayStart +"-"+ mMonthStart +"-"+ mYearStart;
//                timeString= mHourStart +"-"+ mMinuteStart;
//
//                time.setDate(date);
//                time.setTime(timeString);
//                time.setEventID(eventID);
//                dataRepository.insertTime(time);
//                date="";
//                timeString="";
//                date= mDayEnd +"-"+ mMonthEnd +"-"+ mYearEnd;
//                timeString= mHourEnd +"-"+ mMinuteEnd;
//
//                time.setDate(date);
//                time.setTime(timeString);
//                time.setEventID(eventID);
//                dataRepository.insertTime(time);
            }


        } else {


            if (mDayStart != mDayEnd) {
                for (int i = mDayStart; i <= mDayEnd; i++) {
                    Time time = new Time();
                    date = i + "-" + mMonthStart + "-" + mYearStart;
                    if (i == mDayStart) {
                        timeString = "0" + 8 + "-" + "00";
                        time.setTime(timeString);
                    } else if (i == mDayEnd) {
                        timeString = "0" + 5 + "-" + "0" + 0;
                        time.setTime(timeString);
                    }
                    time.setDate(date);
                    time.setEventID(eventID);
                    timeList.add(time);

                }
                dataRepository.insertTime(timeList);
            } else {

                for (int i = 0; i < 2; i++) {
                    Time time = new Time();
                    date = mDayStart + "-" + mMonthStart + "-" + mYearStart;
                    if (i == 0) {
                        timeString = "0" + 8 + "-" + "00";
                        time.setTime(timeString);
                    } else if (i == 1) {
                        timeString = "0" + 5 + "-" + "0" + 0;
                        time.setTime(timeString);
                    }
                    time.setDate(date);

                    time.setEventID(eventID);
                    timeList.add(time);
                    dataRepository.insertTime(timeList);
                }
//                date= mDayStart +"-"+ mMonthStart +"-"+ mYearStart;
//                timeString= mHourStart +"-"+ mMinuteStart;
//
//                time.setDate(date);
//                time.setTime(timeString);
//                time.setEventID(eventID);
//                dataRepository.insertTime(time);
//                date="";
//                timeString="";
//                date= mDayEnd +"-"+ mMonthEnd +"-"+ mYearEnd;
//                timeString= mHourEnd +"-"+ mMinuteEnd;
//
//                time.setDate(date);
//                time.setTime(timeString);
//                time.setEventID(eventID);
//                dataRepository.insertTime(time);
            }
//             date= mDayStart +"-"+ mMonthStart +"-"+ mYearStart;
//             timeString= "0"+8 +"-"+"00";
//
//            time.setDate(date);
//            time.setTime(timeString);
//            time.setEventID(eventID);
//            dataRepository.insertTime(time);
//
//            date= mDayEnd +"-"+ mMonthEnd +"-"+ mYearEnd;
//            timeString= "0"+5 +"-"+ "0"+0;
//
//            time.setDate(date);
//            time.setTime(timeString);
//            time.setEventID(eventID);
//            dataRepository.insertTime(time);

        }


    }

    private void saveEventGuest(int eventID) {


        for (int i = 0; i < eventGuestList.size(); i++) {
            eventGuestList.get(i).setEventID(eventID);
        }

        dataRepository.insertEventGuest(eventGuestList);


    }

    public void saveEvent() {
        String address = mBinding.addressEt.getText() != null ? mBinding.addressEt.getText().toString() : "";
        String description = mBinding.descriptionEt.getText() != null ? mBinding.descriptionEt.getText().toString() : "";
        Event event = new Event();

        event.setAddress(address);
        event.setAllDay(mBinding.allDaySwitch.isChecked());
        event.setCoordinates(" ");
        event.setDescription(description);
        event.setEventType(mBinding.spinnerEvent.getSelectedItem().toString());
        event.setReminder(mBinding.spinnerReminder.getSelectedItem().toString());

        dataRepository.insertEvent(event);

    }

    public void setAllDayAlarm(int key) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYearStart, mMonthStart, mDayStart);

        if (key == 1) {
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, mHourStart);
            calendar.set(Calendar.MINUTE, mMinuteStart);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }


        String reminder = mBinding.spinnerReminder.getSelectedItem().toString();
        if (reminder.equals("1 day before") || reminder.equals("12 hours before")) {

            if (reminder.equals("12 hours before")) {
                calendar.roll(Calendar.HOUR_OF_DAY, 12);
            }

            mHourStart = calendar.get(Calendar.HOUR_OF_DAY);
            mMinuteStart = calendar.get(Calendar.MINUTE);
            mYearStart = calendar.get(Calendar.YEAR);
            mMonthStart = calendar.get(Calendar.MONTH);
            mDayStart = calendar.get(Calendar.DAY_OF_MONTH) - 1;
            if (mDayStart == 31 && mDayStart == 30 && mDayStart == 28) {
                calendar.roll(Calendar.MONTH, -1);
                mMonthStart = calendar.get(Calendar.MONTH);
                if (mMonthStart == 0) {
                    calendar.roll(Calendar.YEAR, -1);
                    mYearStart = calendar.get(Calendar.MONTH);
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


            mHourStart = calendar.get(Calendar.HOUR_OF_DAY);
            mMinuteStart = calendar.get(Calendar.MINUTE);
            mYearStart = calendar.get(Calendar.YEAR);
            mMonthStart = calendar.get(Calendar.MONTH);
            mDayStart = calendar.get(Calendar.DAY_OF_MONTH);
            mHourStart = calendar.get(Calendar.HOUR_OF_DAY);

        }
    }


    private void setUpDate() {

        mMonthStart = customDate.getMonth();
        mDayStart = customDate.getDay();
        mYearStart = customDate.getYear();
        mMonthEnd = customDate.getMonth();
        mDayEnd = customDate.getDay();
        mYearEnd = customDate.getYear();


        calendar.set(mYearStart, mMonthStart, mDayStart);

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


    public void getSupplier(String userType) {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<List<GetSupplierModel>> call = ApiClient.getInstance().getApi().getSupplier(userType, 0, 0);
        call.enqueue(new Callback<List<GetSupplierModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetSupplierModel>> call, @NonNull Response<List<GetSupplierModel>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<GetSupplierModel> getSupplierModelList = response.body();
                        supplierNameList.clear();
                        for (GetSupplierModel model : getSupplierModelList) {
                            supplierNameList.add(model.getSupplier_Name());
                            supplierIDHashMap.put(model.getSupplier_Name(), model.getSupplier_Id());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, supplierNameList);
                        mBinding.spinnerGuestsSelection.setAdapter(adapter);
                        progressDialog.dismiss();

                    }
                    else
                    {
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(requireContext(),""+response.message(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<GetSupplierModel>> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}