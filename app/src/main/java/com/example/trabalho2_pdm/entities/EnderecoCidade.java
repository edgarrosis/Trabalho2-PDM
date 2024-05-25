package com.example.trabalho2_pdm.entities;


public class EnderecoCidade {
    private int enderecoID;
    private String enderecoDescricao;
    private String cidadeEndereco;

    public int getEnderecoID() {
        return enderecoID;
    }
    @Override
    public String toString() {
        return this.enderecoID + ": " + enderecoDescricao + " - " + cidadeEndereco;
    }
}