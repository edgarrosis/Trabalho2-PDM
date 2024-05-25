package com.example.trabalho2_pdm.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalho2_pdm.entities.Endereco;

import java.util.List;

@Dao
public interface EnderecoDao {
    @Query("SELECT * FROM Endereco WHERE enderecoID = :id LIMIT 1")
    Endereco getEndereco(int id);
    @Query("SELECT * FROM Endereco WHERE cidadeIDFK = :idCidade LIMIT 1")
    Endereco getEndByCidade(int idCidade);
    @Update
    void update(Endereco endereco);
    @Insert
    void insert(Endereco endereco);
    @Delete
    void delete(Endereco endereco);
}
