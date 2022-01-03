package com.example.calender.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calender.CalenderFragmentDirections;
import com.example.calender.databinding.CalendarCellLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.CalenderViewHolder>  {

    private LayoutInflater layoutInflater;
    private List<String> daysInMonth;
    private boolean isClick=true;
    private Fragment fragment;


    public CalendarRecyclerAdapter(Fragment fragment) {
        daysInMonth= new ArrayList<>();
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        CalendarCellLayoutBinding binding = CalendarCellLayoutBinding.inflate(layoutInflater,parent,false);

        return new CalenderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {
        holder.mBinding.dateTextView.setText(daysInMonth.get(position));

    }

    @Override
    public int getItemCount() {
        return daysInMonth.size();
    }

    public void setDaysInMonth(List<String > list)
    {
        if (list!=null)
        {
            daysInMonth = list;
            notifyDataSetChanged();
        }
    }

    public class CalenderViewHolder extends RecyclerView.ViewHolder
    {
        CalendarCellLayoutBinding mBinding;

        public CalenderViewHolder(@NonNull CalendarCellLayoutBinding binding) {
            super(binding.getRoot());

            mBinding= binding;

            mBinding.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isClick)
                    {
                        mBinding.addBtn.setVisibility(View.VISIBLE);
                        isClick= false;

                    }
                    else
                    {
                        mBinding.addBtn.setVisibility(View.GONE);
                        isClick= true;
                    }

                }
            });

            mBinding.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NavController navController = NavHostFragment.findNavController(fragment);

                    NavDirections navDirections = CalenderFragmentDirections.actionCalenderFragmentToAddScheduleFragment();

                    navController.navigate(navDirections);

                }
            });
        }
    }
}
