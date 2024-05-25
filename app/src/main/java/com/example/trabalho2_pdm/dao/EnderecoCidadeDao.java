package com.example.trabalho2_pdm.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.trabalho2_pdm.entities.EnderecoCidade;

import java.util.List;

@Dao
public interface EnderecoCidadeDao {
    @Query("SELECT Endereco.enderecoID AS enderecoID, Endereco.descricao " +
            "AS endereco, Cidade.cidade AS cidadeNome " +
            "FROM Endereco INNER JOIN Cidade ON Endereco.enderecoID = Cidade.cidadeID")
    List<EnderecoCidade> getAllEndeCid();
}
