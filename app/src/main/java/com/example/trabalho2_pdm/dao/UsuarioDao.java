package com.example.trabalho2_pdm.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalho2_pdm.entities.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM Usuario WHERE usuarioID = :id LIMIT 1")
    Usuario getUsuario(int id);
    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();
    @Update
    void update(Usuario usuario);
    @Insert
    void insertAll(Usuario... usuario);
    @Delete
    void delete(Usuario usuario);
    @Query("SELECT * FROM Usuario WHERE usuarioID = :usuarioID LIMIT 1")
    Usuario getCityByUsuario(int usuarioID);
}