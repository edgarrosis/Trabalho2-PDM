package com.example.trabalho2_pdm.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int usuarioID;
    private String nome;
    private String email;
    private String telefone;
    private String senha;

    public Usuario() {    }
    public Usuario(String nome, String email, String telefone, String senha) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String toString() {
        return getUsuarioID() + ": " + getNome() + ", " + getEmail() + ", " + getTelefone();
    }
}

