package com.neosoft.reminder.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.neosoft.reminder.R;
import com.neosoft.reminder.adapter.ListViewAdapter;
import com.neosoft.reminder.helper.EventDecorator;
import com.neosoft.reminder.helper.OneDayDecorator;
import com.neosoft.reminder.helper.SQLiteHelper;
import com.neosoft.reminder.model.EventDetails;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnDateSelectedListener, OnMonthChangedListener {
    private static DateFormat FORMATTER;
    private RecyclerView recyclerView;
    private ListViewAdapter mAdapter;
    private String selectedDate;
    private SQLiteHelper db;
    private int day, month, year;
    private MaterialCalendarView calendarView;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        FORMATTER = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        initView();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("selected_date", selectedDate);
                bundle.putInt("day", day);
                bundle.putInt("month", month);
                bundle.putInt("year", year);
                startActivity(new Intent(MainActivity.this, EventActivity.class).putExtras(bundle));

            }
        });
    }

    private void setupAdapter() {

            mAdapter = new ListViewAdapter(this, db.getEventDeatils(calendarView.getCurrentDate().getMonth()));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);
        calendarView.addDecorators(
                oneDayDecorator
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new SQLiteHelper(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.lv_Main);
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        oneDayDecorator.setDate(date.getDate());
        calendarView.invalidateDecorators();
        selectedDate = getSelectedDatesString();
        day = date.getDay();
        month = date.getMonth();
        year = date.getYear();
        Log.e("tag", "Date " + selectedDate);
    }


    private String getSelectedDatesString() {
        CalendarDay date = calendarView.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupAdapter();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        setupAdapter();
    }
}
