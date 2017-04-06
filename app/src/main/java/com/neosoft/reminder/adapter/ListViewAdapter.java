package com.neosoft.reminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neosoft.reminder.R;
import com.neosoft.reminder.model.EventDetails;

import java.util.List;

/**
 * Created by webwerks1 on 5/4/17.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.MyViewHolder> {

    Context mContex;
    private List<EventDetails> eventDetails;
    private LayoutInflater mInflater;

    public ListViewAdapter(Context context, List<EventDetails> eventDetails) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.eventDetails = eventDetails;
        this.mContex = context;

    }

    @Override
    public ListViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.header_date, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewAdapter.MyViewHolder holder, int position) {

        EventDetails eventDetail = eventDetails.get(position);
        String date = eventDetail.getDate();

        holder.title.setText(eventDetail.getTitle());
        holder.detail.setText(eventDetail.getDetails());
        holder.txtdate.setText(eventDetail.getDate());

        if(position>0)
        {
            if(date.equals(eventDetails.get(position-1).getDate()))
            {
                holder.txtdate.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return eventDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, detail, txtdate;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.noteTitle);
            detail = (TextView) view.findViewById(R.id.noteDetail);
            txtdate = (TextView) view.findViewById(R.id.txt_date);
        }
    }


}
