package com.example.trabalho2_pdm.views;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho2_pdm.database.LocalDatabase;
import com.example.trabalho2_pdm.databinding.ActivityUsuarioListBinding;
import com.example.trabalho2_pdm.entities.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UsuarioList extends AppCompatActivity {
    private ActivityUsuarioListBinding binding;
    private LocalDatabase db;
    private int dbUsuarioID;
    private Usuario dbUsuario;
    private List<Usuario> usuarios;
    private ListView listViewUsuario;
    private Intent edtIntent;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();


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
            validarUsuario(view);
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
    public void validarUsuario(View view) {
        String nome = binding.edtNomeUsuario.getText().toString();
        String email = binding.edtEmailUsuario.getText().toString();
        String senha = binding.edtSenhaUsuario.getText().toString();




        if (!email.isEmpty()) {
            if (!nome.isEmpty()) {
                if (!senha.isEmpty()) {
                    criarConta(email,senha);
                }else{
                    Toast.makeText(this, "Preencha a senha do usuário", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Preencha o nome do usuário", Toast.LENGTH_SHORT).show();

            }
        }else {
            Toast.makeText(this, "Preencha o email do usuário", Toast.LENGTH_SHORT).show();

        }


    }
    private void criarConta(String email, String senha){
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(this,MainActivity.class));
                    Toast.makeText(this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"Erro ao cadastrar!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}