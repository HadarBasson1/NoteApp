package com.example.moveoapp.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moveoapp.MyApplication;

@Database(entities = {Note.class,User.class}, version = 8)

abstract  class AppLocalDbRepository extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract UserDao userDao();
}


public class AppLocalDb {
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}
