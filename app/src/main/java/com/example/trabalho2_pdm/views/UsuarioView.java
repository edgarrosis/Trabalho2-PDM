package com.example.trabalho2_pdm.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho2_pdm.database.LocalDatabase;
import com.example.trabalho2_pdm.databinding.ActivityUsuarioViewBinding;
import com.example.trabalho2_pdm.entities.Usuario;

public class UsuarioView extends AppCompatActivity {
    private LocalDatabase db;
    private ActivityUsuarioViewBinding binding;
    private int dbUsuarioID;
    private Usuario dbUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        dbUsuarioID = getIntent().getIntExtra("Usuario_Selecionado_ID", -1);

        if (dbUsuarioID != -1) {
            getDBUsuario();
        }
    }

    private void getDBUsuario() {
        dbUsuario = db.usuarioModel().getUsuario(dbUsuarioID);
        if (dbUsuario != null) {
            binding.edtNomeMod.setText(dbUsuario.getNome());
            binding.edtEmailMod.setText(dbUsuario.getEmail());
            binding.edtTelefoneMod.setText(dbUsuario.getTelefone());
            binding.edtSenhaMod.setText(dbUsuario.getSenha());
        }
    }

    public void salvarUsuario(View view) {
        String nomeUsuario = binding.edtNomeMod.getText().toString();
        String emailUsuario = binding.edtEmailMod.getText().toString();
        String telefoneUsuario = binding.edtTelefoneMod.getText().toString();
        String senhaUsuario = binding.edtSenhaMod.getText().toString();

        if (nomeUsuario.isEmpty()) {
            Toast.makeText(this, "Adicione um nome.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (emailUsuario.isEmpty()) {
            Toast.makeText(this, "Adicione um email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (telefoneUsuario.isEmpty()) {
            Toast.makeText(this, "Adicione um telefone.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senhaUsuario.isEmpty()) {
            Toast.makeText(this, "Adicione uma senha.", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario thisUsuario = new Usuario(nomeUsuario, emailUsuario, telefoneUsuario, senhaUsuario);
        thisUsuario.setUsuarioID(dbUsuarioID);

        if (dbUsuario != null) {
            db.usuarioModel().update(thisUsuario);
            Toast.makeText(this, "Usuário atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void excluirUsuario(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Exclusão de Usuário")
                .setMessage("Deseja excluir esse usuário?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void excluir() {
        db.usuarioModel().delete(dbUsuario);
        Toast.makeText(this, "Usuário excluído com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void voltar(View view) {
        finish();
    }
}
