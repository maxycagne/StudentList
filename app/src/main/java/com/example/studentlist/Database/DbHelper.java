package com.example.studentlist.Database;

import android.content.Context;

import com.example.studentlist.Database.Dao.AppointmentDao;
import com.example.studentlist.Database.Dao.UserDao;

public class DbHelper {

    public UserDao userDao;

    public RoomDb roomDb;

    public AppointmentDao appointmentDao;

    public DbHelper(Context context) {
        this.roomDb = RoomDb.getINSTANCE(context);
        this.userDao = roomDb.userDao();
        this.appointmentDao = roomDb.appointmentDao();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public RoomDb getRoomDb() {
        return roomDb;
    }

    public AppointmentDao getAppointmentDao() {
        return appointmentDao;
    }
}
