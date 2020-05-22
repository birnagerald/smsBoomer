package com.example.smsboomer.ui.stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smsboomer.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private ArrayList contactName;
    private ArrayList contactTotalSms;
    private ArrayList contactPhoneNumber;
    private LayoutInflater inflater;

    public RecyclerAdapter(ArrayList contactName, ArrayList contactTotalSms, ArrayList contactPhoneNumber,Context context) {
        this.inflater = LayoutInflater.from(context);
        this.contactName = contactName;
        this.contactTotalSms = contactTotalSms;
        this.contactPhoneNumber = contactPhoneNumber;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.contact_name.setText(contactName.get(position).toString());
        holder.phone_number.setText(contactPhoneNumber.get(position).toString());
        holder.sms_count.setText(contactTotalSms.get(position).toString());
    }

    @Override
    public int getItemCount() {
        ArrayList[] arrays = {contactPhoneNumber, contactTotalSms};
        ArrayList smallest = contactName;

        for (ArrayList a : arrays) {
            if (a.size() < smallest.size()) {
                smallest = a;
            }
        }
        return smallest.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        protected TextView contact_name;
        protected TextView phone_number;
        protected TextView sms_count;

        public MyViewHolder(View itemView) {
            super(itemView);
            contact_name = itemView.findViewById(R.id.contact_name);
            phone_number = itemView.findViewById(R.id.phone_number);
            sms_count = itemView.findViewById(R.id.sms_count);
        }
    }
}

