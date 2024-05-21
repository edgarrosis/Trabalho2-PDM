package com.example.trabalho2_pdm.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int usuarioID;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {    }
    public Usuario(String nome) {
        this.nome=nome;
    }
    public int getUsuarioID() {
        return usuarioID;
    }
    public void setUsuarioID(int marcaID) {
        this.usuarioID = marcaID;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return this.usuarioID + ": " + getNome();
    }
}

