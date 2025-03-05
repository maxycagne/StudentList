package com.example.studentlist.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.studentlist.Database.Dao.AppointmentDao;
import com.example.studentlist.Database.Dao.UserDao;
import com.example.studentlist.Database.Entity.Appointment;
import com.example.studentlist.Database.Entity.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Appointment.class}, version = 1)
public abstract class RoomDb extends RoomDatabase
{
    public abstract AppointmentDao appointmentDao();
    public abstract UserDao userDao();
    public static RoomDb INSTANCE;

    public synchronized static RoomDb getINSTANCE(Context context)
    {
        INSTANCE = Room.databaseBuilder(context,RoomDb.class,"DentalAppointment.db").addCallback(new Callback()
        {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                UserDao userDao = RoomDb.INSTANCE.userDao();
                AppointmentDao appointmentDao = RoomDb.INSTANCE.appointmentDao();

                Executors.newSingleThreadExecutor().submit(()->{
                   userDao.insertAll(new User("Maverick","Cagne","+639318992301","mav","user"));
                   appointmentDao.insertAll(new Appointment("ToothChange","04/04/2025","12:00 PM","Pending","Masakit ngipin ko"));

                });

            }
        }).build();

        return INSTANCE;
    }
}
