package com.example.trabalho2_pdm.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.trabalho2_pdm.databinding.ActivityMainBinding;
import com.example.trabalho2_pdm.entities.Usuario;
import com.example.trabalho2_pdm.database.LocalDatabase;
import com.example.trabalho2_pdm.entities.Usuario;

public class MainActivity extends AppCompatActivity {

    private LocalDatabase db;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = LocalDatabase.getDatabase(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLogin();
            }
        });
        binding.bttnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, UsuarioList.class);
                startActivity(it);
            }
        });
    }

    protected void onResume(){
        super.onResume();

    }

    private void realizarLogin(){
        String email = binding.edtEmailLogin.getText().toString();
        String senha = binding.edtSenhaLogin.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Preencha o email do usuário",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.isEmpty()) {
            Toast.makeText(this, "Preencha o campo senha",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            Usuario user = db.usuarioModel().login(email, senha);
            if(user == null){
                Toast.makeText(MainActivity.this, "Usuário não cadastrado.",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(MainActivity.this, CidadeList.class);
                startActivity(it);
            }
        }
    }
}