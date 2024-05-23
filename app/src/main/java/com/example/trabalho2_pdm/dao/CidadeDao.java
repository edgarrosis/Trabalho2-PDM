package com.example.trabalho2_pdm.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalho2_pdm.entities.Cidade;

import java.util.List;

@Dao
public interface CidadeDao {
    @Query("SELECT * FROM Cidade WHERE cidadeID = :id LIMIT 1")
    Cidade getCidadeID(int id);
    @Query("SELECT * FROM Cidade")
    List<Cidade> getAll();
    @Update
    void update(Cidade cidade);
    @Insert
    void insert(Cidade cidade);
    @Delete
    void delete(Cidade cidade);
}
