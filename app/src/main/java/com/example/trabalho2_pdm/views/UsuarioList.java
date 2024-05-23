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
            String nomeUsuario = binding.edtNomeUsuario.getText().toString();
            if (nomeUsuario.isEmpty()) {
                Toast.makeText(UsuarioList.this, "Preencha todos os Campos",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String emailUsuario = binding.edtEmailUsuario.getText().toString();
            if (emailUsuario.isEmpty()) {
                Toast.makeText(UsuarioList.this, "Preencha todos os Campos",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String senhaUsuario = binding.edtSenhaUsuario.getText().toString();
            if (senhaUsuario.isEmpty()) {
                Toast.makeText(UsuarioList.this, "Preencha todos os Campos",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario thisUsuario = new Usuario(dbUsuarioID,nomeUsuario, emailUsuario, senhaUsuario);



            if (dbUsuario != null) {
                thisUsuario.setUsuarioID(dbUsuarioID);
                thisUsuario.setNome(nomeUsuario);
                thisUsuario.setEmail(emailUsuario);
                thisUsuario.setSenha(senhaUsuario);

                db.usuarioModel().update(thisUsuario);
            } else {
                db.usuarioModel().insertAll(thisUsuario);
            }
            onResume();
        }
    });
}
    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, UsuarioView.class);
        preencherUsuarios();
    }
    private void preencherUsuarios(){
        usuarios = db.usuarioModel().getAll();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                usuarios);
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
}
