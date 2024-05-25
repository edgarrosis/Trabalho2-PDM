package com.example.trabalho2_pdm.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.trabalho2_pdm.entities.EnderecoCidade;

import java.util.List;

@Dao
public interface EnderecoCidadeDao {
    @Query("SELECT Endereco.enderecoID AS enderecoID, Endereco.descricao AS enderecoDescricao, Cidade.cidade " +
            "AS cidadeEndereco, Cidade.estado AS estadoEndereco " +
            "FROM Endereco INNER JOIN Cidade ON Endereco.cidadeIDFK = Cidade.cidadeID")
    List<EnderecoCidade> getAllEndeCid();
}
