package com.example.sofra.model.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CartDeo {
    @Query("SELECT * fROM RoomCartModel")
    List<RoomCartModel> getAllData();

    @Insert
    void insertAll(RoomCartModel... cartModels);

    @Query("delete from RoomCartModel")
    void deletAll();

    @Delete
    void deleteItem(RoomCartModel ... cartModels);
    @Update
    void update(RoomCartModel ... cartModels);
}
