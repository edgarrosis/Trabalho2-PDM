package com.example.trabalho2_pdm.entities;

import androidx.room.Entity;

@Entity
public class CidadeEndereco {
    private int cidadeID;
    private String cidadeEndereco;
    private String enderecoDescricao;

    public int getCidadeID() {
        return cidadeID;
    }
    @Override
    public String toString() {
        return this.cidadeID + ": " + cidadeEndereco + ", " + enderecoDescricao;
    }
}
