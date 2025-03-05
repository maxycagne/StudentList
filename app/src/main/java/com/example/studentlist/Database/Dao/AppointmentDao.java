package com.example.studentlist.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.studentlist.Database.Entity.Appointment;
import com.example.studentlist.Database.Entity.User;

import java.util.List;

@Dao
public interface AppointmentDao {

    @Query("SELECT * FROM appointment")
    List<Appointment> getAll();




    @Insert
    void insertAll(Appointment appointment);

    @Delete
    void delete(Appointment appointment);
}
