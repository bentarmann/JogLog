package com.example.joglog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class represents the AddLog activity when attempting to create a new log
 *
 * @author Benjamin Tarmann
 */
public class AddLogActivity extends AppCompatActivity {

    private EditText dateInput;
    private DatePickerDialog.OnDateSetListener dateInputListener;
    private EditText timeInput;
    private EditText distanceInput;
    private EditText notesInput;

    /**
     * Method creates the AddLogActivity view whenever the AddLogActivity is entered
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);

        // set action for selecting date
        dateSelectAction();

        // set action for adding log
        findViewById(R.id.addLog).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addLogAction();
            }
        });
    }

    /**
     * Method sets the functionality for selecting a date for the log
     */
    @SuppressLint("ClickableViewAccessibility")
    private void dateSelectAction() {
        dateInput = (EditText) findViewById(R.id.Date);

        // set on click action
        dateInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            AddLogActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            dateInputListener,
                            year, month, day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });

        // set on click action if dateInput is clicked on after already being in focus (e.g. when
        // changing the date once, and then clicking on it to change it before moving the focus
        dateInput.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddLogActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateInputListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // display the selected date in the EditText dateInput
        dateInputListener = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                String date = month + 1 + "/" + day + "/" + year;
                dateInput.setText(date);
            }
        };
    }

    /**
     * Method parses all of the fields, checks that they are formatted correctly, and constructs a
     * log based on the info inputted by the user. This method is executed when the user presses the
     * "Add log" button
     */
    private void addLogAction() {
        dateInput = (EditText) findViewById(R.id.Date);
        timeInput = (EditText) findViewById(R.id.Time);
        distanceInput = (EditText) findViewById(R.id.Distance);
        notesInput = (EditText) findViewById(R.id.Notes);

        // parse date
        DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
        Date date = new Date();
        try {
            int month = Integer.parseInt(dateInput.getText().toString().split("/")[0]) - 1;
            int day = Integer.parseInt(dateInput.getText().toString().split("/")[1]);
            int year = Integer.parseInt(dateInput.getText().toString().split("/")[2]);
            date.setMonth(month);
            date.setDate(day);
            date.setYear(year);
        }
        catch (NumberFormatException e) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Please enter a valid date.");
            dialog.setTitle("Invalid date");
            dialog.show();
            return;
        }

        // parse time
        String timeText = timeInput.getText().toString();
        Time time;
        int minutes;
        int seconds;
        try {
            minutes = Integer.parseInt(timeText.split(":")[0]);
            seconds = Integer.parseInt(timeText.split(":")[1]);

            if (seconds >= 60) {
                throw new NumberFormatException();
            }

            time = new Time(minutes, seconds);
        }
        catch (NumberFormatException e) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Please enter a valid time in the format \"hours:minutes\".");
            dialog.setTitle("Invalid time");
            dialog.show();
            return;
        }

        // parse distance
        double distance;
        try {
            distance = Double.parseDouble(distanceInput.getText().toString());
        }
        catch (NumberFormatException e) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Please enter a valid distance in miles.");
            dialog.setTitle("Invalid distance");
            dialog.show();
            return;
        }

        // parse notes
        String notes = notesInput.getText().toString();
        if (notes.equals("")) {
            notes = "No notes";
        }

        // construct new log
        Log log = new Log(date, time, distance, notes);
        LogsFragment.addToJogLog(log);

        // return to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
