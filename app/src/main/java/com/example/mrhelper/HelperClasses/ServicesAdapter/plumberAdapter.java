package com.example.mrhelper.HelperClasses.ServicesAdapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrhelper.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class plumberAdapter extends RecyclerView.Adapter<plumberAdapter.ViewHolder> implements Adapter {

    List<String> mName, mPhoneNo, mAddress, mHours, mImage;
    LayoutInflater inflater;

    public plumberAdapter(Context context, List<String> mName, List<String> mPhoneNo, List<String> mAddress, List<String> mHours, List<String> mImage){
        this.inflater = LayoutInflater.from(context);
        this.mName = mName;
        this.mPhoneNo = mPhoneNo;
        this.mAddress = mAddress;
        this.mHours = mHours;
        this.mImage = mImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_recycler_plumber,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(mName.get(position));
        holder.phoneNo.setText(mPhoneNo.get(position));
        holder.address.setText(mAddress.get(position));
        holder.hours.setText(mHours.get(position));
        Picasso.get().load(mImage.get(position)).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mName.size();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,phoneNo,address,hours;
        ImageView thumbnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phoneNo = itemView.findViewById(R.id.phone_no);
            address = itemView.findViewById(R.id.address);
            hours = itemView.findViewById(R.id.hours);
            thumbnail = itemView.findViewById(R.id.cardImg);

        }
    }
}
