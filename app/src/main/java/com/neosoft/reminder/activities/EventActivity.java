package com.neosoft.reminder.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.neosoft.reminder.R;
import com.neosoft.reminder.helper.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by webwerks1 on 5/4/17.
 */

public class EventActivity extends AppCompatActivity implements View.OnClickListener {
    int day, month, years;
    private EditText date, title, detail;
    private Context mContext;
    private SimpleDateFormat dateFormatter;
    private SQLiteHelper db;
    private String selectedDate;
    private Calendar newCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_event);

        Bundle bundle = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Event");
        selectedDate = bundle.getString("selected_date");
        day = bundle.getInt("date");
        month = bundle.getInt("month");
        years = bundle.getInt("year");
        Button saveBtn = (Button) findViewById(R.id.btn_Save);

        date = (EditText) findViewById(R.id.edt_Date);
        title = (EditText) findViewById(R.id.edt_Title);
        detail = (EditText) findViewById(R.id.edt_Note);

        date.setText(selectedDate);
        db = new SQLiteHelper(this);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        saveBtn.setOnClickListener(this);
        date.setOnClickListener(this);
        return;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_Date:

                newCalendar = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        date.setText(dateFormatter.format(newDate.getTime()));
                        day = dayOfMonth;
                        month = monthOfYear;
                        years = year;
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                Log.d("tag", "month " + newCalendar.get(Calendar.MONTH) + 1);
                if (datePicker != null)
                    datePicker.show();
                break;

            case R.id.btn_Save:

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if (title.getText().toString().equals("") || title.getText() == null) {
                    Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
                } else if (detail.getText().toString().equals("") || detail.getText() == null) {
                    Toast.makeText(this, "Enter Note", Toast.LENGTH_SHORT).show();
                } else if (date.getText().toString().equals("") || date.getText() == null) {
                    Toast.makeText(this, "Enter Date", Toast.LENGTH_SHORT).show();
                } else {
                    db.addEvent(title.getText().toString(), date.getText().toString(), detail.getText().toString(), String.valueOf(day), String.valueOf(month), String.valueOf(years));
                    startActivity(new Intent(EventActivity.this, MainActivity.class));
                    finish();
                }
                break;
        }
    }
}
