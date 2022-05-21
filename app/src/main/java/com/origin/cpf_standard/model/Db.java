package com.origin.cpf_standard.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = { CPF.class }, version = 1)
public abstract class Db extends RoomDatabase {
    private static final String NAME_DATABASE = "CPFs";
    private static Db instance;

    public static synchronized Db getInstance(Context context){
        if(instance == null){
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), Db.class, NAME_DATABASE)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CpfDAO cpfdados();
}
