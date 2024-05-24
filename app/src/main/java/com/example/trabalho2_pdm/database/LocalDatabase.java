package com.example.trabalho2_pdm.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.trabalho2_pdm.dao.CidadeDao;
import com.example.trabalho2_pdm.dao.EnderecoDao;
import com.example.trabalho2_pdm.dao.UsuarioDao;
import com.example.trabalho2_pdm.entities.Endereco;
import com.example.trabalho2_pdm.entities.Usuario;
import com.example.trabalho2_pdm.entities.Cidade;

@Database(entities = {Usuario.class, Cidade.class, Endereco.class}, version = 2)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase INSTANCE;

    public static LocalDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LocalDatabase.class, "ControleUsuarios").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public abstract UsuarioDao usuarioModel();
    public abstract CidadeDao cidadeModel();
    public abstract EnderecoDao enderecoModel();
}