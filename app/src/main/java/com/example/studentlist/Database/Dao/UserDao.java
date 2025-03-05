package com.example.studentlist.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.studentlist.Database.Entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();


    @Query("SELECT * FROM user WHERE phoneNumber LIKE :phoneNumber AND " +
            "password LIKE :password LIMIT 1")
    User findUserByUsernamePass(String phoneNumber, String password);

    @Query("SELECT * FROM user WHERE phoneNumber LIKE :phoneNumber LIMIT 1")
    User findUserByNumber(String phoneNumber);

    @Insert
    void insertAll(User users);

    @Delete
    void delete(User user);
}
