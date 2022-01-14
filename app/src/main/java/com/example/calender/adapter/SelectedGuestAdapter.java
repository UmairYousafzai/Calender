package com.example.calender.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calender.databinding.SelectedGuestLayoutBinding;
import com.example.calender.models.Event;
import com.example.calender.models.EventGuest;

import java.util.ArrayList;
import java.util.List;

public class SelectedGuestAdapter extends RecyclerView.Adapter<SelectedGuestAdapter.SelectedGuestViewHolder> {
    private LayoutInflater layoutInflater;
    private List<EventGuest> selectedGuestList= new ArrayList<>();
    private SetOnClickListener listener;

    public void SetOnClickListener(SetOnClickListener listener)
    {
        this.listener = listener;
    }



    @NonNull
    @Override
    public SelectedGuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        SelectedGuestLayoutBinding binding = SelectedGuestLayoutBinding.inflate(layoutInflater,parent,false);
        return new SelectedGuestViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedGuestViewHolder holder, int position) {

        holder.mBinding.tvGuestName.setText(selectedGuestList.get(position).getGuestName());

    }

    @Override
    public int getItemCount() {
        return selectedGuestList.size();
    }

    public void setSelectedGuestList(List<EventGuest> list)
    {
        if (list!=null)
        {
            selectedGuestList = list;
        }
        else
        {
            selectedGuestList.clear();

        }
        notifyDataSetChanged();
    }

    public class SelectedGuestViewHolder extends RecyclerView.ViewHolder
    {
        SelectedGuestLayoutBinding mBinding;

        public SelectedGuestViewHolder(@NonNull SelectedGuestLayoutBinding binding) {
            super(binding.getRoot());

            mBinding= binding;
            mBinding.btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    EventGuest eventGuest = selectedGuestList.get(getAdapterPosition());
                    listener.onClick(eventGuest);
                    notifyDataSetChanged();
                }
            });
        }
    }
    public interface  SetOnClickListener
    {
        public void onClick(EventGuest eventGuest);
    }
}
