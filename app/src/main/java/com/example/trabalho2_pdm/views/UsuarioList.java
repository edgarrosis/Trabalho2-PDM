package com.example.trabalho2_pdm.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho2_pdm.database.LocalDatabase;
import com.example.trabalho2_pdm.databinding.ActivityUsuarioListBinding;
import com.example.trabalho2_pdm.entities.Usuario;

import java.util.List;

public class UsuarioList extends AppCompatActivity {
    private ActivityUsuarioListBinding binding;
    private LocalDatabase db;
    private int dbUsuarioID;
    private Usuario dbUsuario;
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
        public void onClick(View view) {
            adicionarUsuarios(view);
        }
    });
}
    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, UsuarioView.class);
        preencherUsuarios();
    }

    private void preencherUsuarios() {
        usuarios = db.usuarioModel().getAll();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usuarios);
        listViewUsuario.setAdapter(adapter);

        listViewUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuarioSelecionado = usuarios.get(position);
                edtIntent.putExtra("Usuario_Selecionado_ID", usuarioSelecionado.getUsuarioID());
                startActivity(edtIntent);
            }
        });
    }
    public void adicionarUsuarios(View view) {
        String nome = binding.edtNomeUsuario.getText().toString();
        String email = binding.edtEmailUsuario.getText().toString();
        String telefone = binding.edtTelefoneUsuario.getText().toString();
        String senha = binding.edtSenhaUsuario.getText().toString();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Preencha o nome do usuário", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Preencha o email do usuário", Toast.LENGTH_SHORT).show();
            return;
        }
        if (telefone.isEmpty()) {
            Toast.makeText(this, "Preencha o email do usuário", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.isEmpty()) {
            Toast.makeText(this, "Preencha a senha do usuário", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario novoUsuario = new Usuario(nome, email, telefone, senha);

        if (dbUsuario != null) {
            novoUsuario.setUsuarioID(dbUsuarioID);
            db.usuarioModel().update(novoUsuario);
            Toast.makeText(this, "Usuário atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            db.usuarioModel().insert(novoUsuario);
            Toast.makeText(this, "Usuário cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
        }
        preencherUsuarios();
    }
}