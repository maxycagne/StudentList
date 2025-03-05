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

    @Query("UPDATE appointment SET status = :status WHERE id = :id")
    void updateAppointmentStatusById(int id, String status);

    @Query("SELECT * FROM appointment WHERE date LIKE :date LIMIT 1")
    Appointment findAppointmentByDate(String date);

    @Query("SELECT * FROM appointment WHERE id = :id")
    Appointment getAppointmentById(int id);

    @Insert
    void insertAll(Appointment appointment);

    @Delete
    void delete(Appointment appointment);
}
