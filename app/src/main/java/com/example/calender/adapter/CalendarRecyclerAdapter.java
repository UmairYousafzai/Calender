package com.example.calender.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calender.R;
import com.example.calender.clickListener.OnCLickListener;
import com.example.calender.databinding.CalendarCellLayoutBinding;
import com.example.calender.models.CustomDate;

import java.util.ArrayList;
import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.CalenderViewHolder>  {

    private LayoutInflater layoutInflater;
    private List<CustomDate> customDateList;
    private boolean isClick=true;
    private int position =-1;

    private OnCLickListener listener;
    private View view;
    private Context context;


    public CalendarRecyclerAdapter(Context context) {
        customDateList = new ArrayList<>();
        this.context = context;
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

        CustomDate customDate= customDateList.get(position);


        if (customDate.getDay()!=0)
        {
            if (customDate.isCurrentDate())
            {
                holder.mBinding.dateTextView.setTextColor(Color.parseColor("#286A9C"));
                holder.mBinding.dateTextView.setTypeface(holder.mBinding.dateTextView.getTypeface(), Typeface.BOLD);
                holder.mBinding.dateTextView.setTextSize(30f);
                holder.mBinding.dateTextView.setText(String.valueOf(customDate.getDay()));
                holder.mBinding.dateTextView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_baseline_circle_24, 0, 0);
//                holder.mBinding.dateTextView.setTextColor(Color.parseColor("#FF000000"));
//                holder.mBinding.dateTextView.setTypeface(holder.mBinding.dateTextView.getTypeface(), Typeface.NORMAL);

            }else
            {
                holder.mBinding.dateTextView.setText(String.valueOf(customDate.getDay()));
            }

        }


    }
    public void setOnClickListener(OnCLickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return customDateList.size();
    }

    public void setCustomDateList(List<CustomDate > list)
    {
        if (list!=null)
        {
            customDateList = list;
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

                    if (view!=null)
                    {
                        if (view.getVisibility()==View.VISIBLE)
                        {
                            view.setVisibility(View.GONE);

                        }
                    }


                        if (isClick)
                    {

                        mBinding.addBtn.setVisibility(View.VISIBLE);
                       view=  mBinding.addBtn;
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

                    listener.onClick(customDateList.get(getAdapterPosition()));



                }
            });
        }
    }
}
