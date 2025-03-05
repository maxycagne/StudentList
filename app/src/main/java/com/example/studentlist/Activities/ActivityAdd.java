package com.example.studentlist.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentlist.Database.DbHelper;
import com.example.studentlist.Database.Entity.Appointment;
import com.example.studentlist.R;
import com.example.studentlist.databinding.ActivityAddBinding;

import java.util.Calendar;
import java.util.concurrent.Executors;

public class ActivityAdd extends AppCompatActivity {
    private ActivityAddBinding root;

    private Handler handler;

    private DbHelper dbHelper;

    private void setDb()
    {
        handler = new Handler(Looper.getMainLooper());
        dbHelper = new DbHelper(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        root = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setDb();

        root.txtDate.setOnClickListener(view -> showDatePickerDialog());

        root.txtTime.setOnClickListener(view -> showTimePickerDialog());

        root.btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this,ActivityDashboard.class));
            finish();
        });

        root.buttonClear.setOnClickListener(view -> {
            root.txtAppointmentName.setText("");
            root.txtDate.setText("");
            root.txtTime.setText("");
            root.txtDescription.setText("");

        });

        root.buttonDone.setOnClickListener(view -> {
            addAppointment();
        });


    }

    private void showDatePickerDialog()
    {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    root.txtDate.setText(date);
                },
                year, month, day
        );
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        boolean isPM = calendar.get(Calendar.AM_PM) == Calendar.PM;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (TimePicker view, int selectedHour, int selectedMinute) -> {
                    boolean selectedIsPM = selectedHour >= 12;


                    int formattedHour = (selectedHour == 0) ? 12 : (selectedHour > 12 ? selectedHour - 12 : selectedHour);
                    String period = selectedHour >= 12 ? "PM" : "AM";

                    String time = String.format("%02d:%02d %s", formattedHour, selectedMinute, period);
                    root.txtTime.setText(time);
                },
                hour, minute, false
        );

        timePickerDialog.show();
    }

    private void addAppointment()
    {
        String appointmentName = root.txtAppointmentName.getText().toString();
        String date = root.txtDate.getText().toString();
        String time = root.txtTime.getText().toString();
        String desc = root.txtDescription.getText().toString();
        String status = "Pending";

        Executors.newSingleThreadExecutor().submit(()->{
            Appointment availAppointment = dbHelper.getAppointmentDao().findAppointmentByDate(date);

            if(availAppointment != null)
            {
                handler.post(() -> Toast.makeText(ActivityAdd.this, "Date not available, please choose another date", Toast.LENGTH_SHORT).show());

            }
            else
            {
                Appointment appointment = new Appointment(appointmentName,date,time,status,desc);

                dbHelper.getAppointmentDao().insertAll(appointment);
                handler.post(() ->{
                    startActivity(new Intent(this,ActivityDashboard.class));
                    finish();
                    Toast.makeText(this, "Appointment Added", Toast.LENGTH_SHORT).show();
                });


            }


        });
    }

}