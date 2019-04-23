package com.example.sofra.model.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.view.ViewDebug;

@Database(entities = {RoomCartModel.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CartDeo cartDeo ();
}
