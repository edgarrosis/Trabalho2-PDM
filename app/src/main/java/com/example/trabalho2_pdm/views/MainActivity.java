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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private LocalDatabase db;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

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
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

    }

    protected void onResume(){
        super.onResume();

    }

    private void realizarLogin(){
        String email = binding.edtEmailLogin.getText().toString();
        String senha = binding.edtSenhaLogin.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                loginFirebase(email,senha);
            } else {
                Toast.makeText(this, "Preencha o campo senha",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Preencha o email do usuário",
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void loginFirebase(String email, String senha){
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        startActivity(new Intent(MainActivity.this,CidadeList.class));
                        Toast.makeText(this,"Logado com sucesso!",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this,"Erro no Login!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}