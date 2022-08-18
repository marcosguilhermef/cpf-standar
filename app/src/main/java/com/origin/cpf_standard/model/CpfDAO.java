package com.origin.cpf_standard.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CpfDAO {
    @Insert
    void insert(CPF cpf);
    @Insert
    void insertAll(CPF... cpf);
    @Delete
    void delete(CPF contact);
    @Query("SELECT * FROM cpf WHERE id = :id ORDER BY ID DESC LIMIT 20")
    Flowable<List<CPF>> getAll(long id);
    @Query("SELECT * FROM cpf ORDER BY ID DESC LIMIT 20")
    Flowable<List<CPF>> getAll();
}
