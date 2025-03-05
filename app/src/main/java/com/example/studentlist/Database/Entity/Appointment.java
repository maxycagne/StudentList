package com.example.studentlist.Database.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Appointment implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String appointmentName;

    private String date;

    private String time;

    private String status;

    private String desc;



    @Ignore
    public Appointment() {
    }

    @Ignore
    public Appointment(String appointmentName, String date, String time, String status, String desc) {
        this.appointmentName = appointmentName;
        this.date = date;
        this.time = time;
        this.status = status;
        this.desc = desc;
    }

    public Appointment(int id, String appointmentName, String date, String time, String status, String desc) {
        this.id = id;
        this.appointmentName = appointmentName;
        this.date = date;
        this.time = time;
        this.status = status;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
