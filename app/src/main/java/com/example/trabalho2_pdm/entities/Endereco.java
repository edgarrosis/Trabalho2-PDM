package com.example.trabalho2_pdm.entities;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (foreignKeys = @ForeignKey(entity = Cidade.class,
        parentColumns = "cidadeID", childColumns = "cidadeIDFK",
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE))
public class Endereco {
    @PrimaryKey(autoGenerate = true)
    private int enderecoID;
    private double latitude;
    private double longitude;
    private String descricao;
    private int cidadeIDFK;

    public Endereco() {}

    public Endereco(Double latitude, Double longitude, String descricao, int cidadeIDFK) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.descricao = descricao;
        this.cidadeIDFK = cidadeIDFK;
    }

    public int getEnderecoID() {
        return enderecoID;
    }

    public void setEnderecoID(int enderecoID) {
        this.enderecoID = enderecoID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCidadeIDFK() {
        return cidadeIDFK;
    }

    public void setCidadeIDFK(int cidadeIDFK) {
        this.cidadeIDFK = cidadeIDFK;
    }

    public String toString() {
        return getEnderecoID() + ": " + getDescricao() + " Cidade: "+getCidadeIDFK();
    }
}
