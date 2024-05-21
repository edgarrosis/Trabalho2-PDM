package com.example.trabalho2_pdm.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho2_pdm.database.LocalDatabase;
import com.example.trabalho2_pdm.databinding.ActivityUsuarioListBinding;
import com.example.trabalho2_pdm.entities.Usuario;

import java.util.List;

public class UsuarioList extends AppCompatActivity {
    private ActivityUsuarioListBinding binding;
    private LocalDatabase db;
    private List<Usuario> usuarios;
    private ListView listViewUsuario;
    private Intent edtIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewUsuario = binding.listUsuario;

        binding.backBttnUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.addBttnUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UsuarioList.this, UsuarioView.class));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, UsuarioView.class);
        preencheUsuarios();
    }
    private void preencheUsuarios() {
        usuarios = db.usuarioModel().getAll();
        ArrayAdapter<Usuario> usuariosAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, usuarios);
        listViewUsuario.setAdapter(usuariosAdapter);

        listViewUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Usuario usuarioSelecionado = usuarios.get(position);
                edtIntent.putExtra("MARCA_SELECIONADA_ID",
                        usuarioSelecionado.getUsuarioID());
                startActivity(edtIntent);
            }
        });
    }
}
