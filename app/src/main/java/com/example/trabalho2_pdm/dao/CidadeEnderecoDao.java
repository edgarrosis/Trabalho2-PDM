package com.example.trabalho2_pdm.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.trabalho2_pdm.entities.CidadeEndereco;

import java.util.List;

@Dao
public interface CidadeEnderecoDao {
    @Query("SELECT Cidade.cidadeID AS cidadeID, Cidade.cidade " +
            "AS cidadeNome, Endereco.descricao AS endereco " +
            "FROM Cidade INNER JOIN Endereco ON Cidade.cidadeID = Endereco.enderecoID")
    List<CidadeEndereco> getAllCidEnde();
}
