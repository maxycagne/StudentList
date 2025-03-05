package com.example.studentlist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentlist.Adapter.AppointmentAdapter;
import com.example.studentlist.Database.Dao.AppointmentDao;
import com.example.studentlist.Database.DbHelper;
import com.example.studentlist.Database.Entity.Appointment;
import com.example.studentlist.R;
import com.example.studentlist.databinding.ActivityViewBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class ActivityView extends AppCompatActivity {

    private ActivityViewBinding root;
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
        root = ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        setDb();
        int id = getIntent().getIntExtra("id",0);
        retriveAppointment(id);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        root.btnBack.setOnClickListener(v->{
            startActivity(new Intent(this,ActivityDashboard.class));
            finish();
        });
        root.buttonConfirmCancel.setOnClickListener(v -> cancelStatus(id));


    }

    private void retriveAppointment(int id) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Appointment appointment = dbHelper.getAppointmentDao().getAppointmentById(id);
                if (appointment != null) {
                    handler.post(() -> updateUI(appointment));
                }
            } catch (Exception ex) {
                Log.e("ActivityView", "Error retrieving appointment", ex);
            }
        });
    }

    private void updateUI(Appointment appointment) {
        root.textAppointmentName.setText(appointment.getAppointmentName());
        root.textDate.setText(appointment.getDate());
        root.textStatus.setText(appointment.getStatus());
        root.textTime.setText(appointment.getTime());
        root.textDescription.setText(appointment.getDesc());

        if (appointment.getStatus().equals("Done")) {
            root.buttonConfirmCancel.setText("Confirm");
        } else {
            root.buttonConfirmCancel.setText("Cancel");
        }
    }


    private void cancelStatus(int appointmentId) {
        Executors.newSingleThreadExecutor().submit(() -> {
            Appointment appointment = dbHelper.getAppointmentDao().getAppointmentById(appointmentId);
            if (appointment != null && appointment.getStatus().equals("Pending")) {
                dbHelper.getAppointmentDao().delete(appointment);
                handler.post(() -> {
                    root.textStatus.setText("Cancelled");
                    root.buttonConfirmCancel.setEnabled(false);
                });
            }
        });
    }


}