package com.example.trabalho2_pdm.entities;

public class EnderecoCidade {
    private int enderecoID;
    private String enderecoDescricao;
    private String cidadeEndereco;
    private String estadoEndereco;

    public EnderecoCidade(int enderecoID, String enderecoDescricao, String cidadeEndereco, String estadoEndereco) {
        this.enderecoID = enderecoID;
        this.enderecoDescricao = enderecoDescricao;
        this.cidadeEndereco = cidadeEndereco;
        this.estadoEndereco = estadoEndereco;
    }

    public int getEnderecoID() {
        return enderecoID;
    }

    public String getEnderecoDescricao() {
        return enderecoDescricao;
    }

    public String getCidadeEndereco() {
        return cidadeEndereco;
    }

    public String getEstadoEndereco() {
        return estadoEndereco;
    }

    public void setEstadoEndereco(String estadoEndereco) {
        this.estadoEndereco = estadoEndereco;
    }

    @Override
    public String toString() {
        return this.enderecoID + ": " + enderecoDescricao + " - " + cidadeEndereco + ", " + estadoEndereco;
    }
}
