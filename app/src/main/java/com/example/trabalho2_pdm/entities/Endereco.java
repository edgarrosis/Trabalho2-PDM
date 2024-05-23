package com.example.trabalho2_pdm.entities;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Endereco {
    @PrimaryKey(autoGenerate = true)
    public int enderecoID;
}
