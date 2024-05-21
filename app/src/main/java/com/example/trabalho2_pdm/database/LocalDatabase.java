package com.example.trabalho2_pdm.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.trabalho2_pdm.dao.MapaDao;
import com.example.trabalho2_pdm.dao.UsuarioDao;
import com.example.trabalho2_pdm.dao.UsuarioMapaDao;
import com.example.trabalho2_pdm.entities.Usuario;

@Database(entities = {Usuario.class}, version = 2)
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
    public abstract MapaDao mapaModel();
    public abstract UsuarioMapaDao usuarioMapaModel();
}