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
        dbUsuarioID = getIntent().getIntExtra(
                "USUARIO_SELECIONADO_ID", -1);
    }

    private void getDBUsuario() {
        dbUsuario = (Usuario) db.usuarioModel().getAll();
        binding.edtNomeMod.setText(dbUsuario.getNome());
        binding.edtEmailMod.setText(dbUsuario.getEmail());
        binding.edtSenhaMod.setText(dbUsuario.getSenha());
    }

    public void salvarMarca(View view) {
        String nomeUsuario = binding.edtNomeMod.getText().toString();
        if (nomeUsuario.isEmpty()) {
            Toast.makeText(this, "Adicione um nome.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String emailUsuario = binding.edtNomeMod.getText().toString();
        if (emailUsuario.isEmpty()) {
            Toast.makeText(this, "Adicione um nome.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String senhaUsuario = binding.edtNomeMod.getText().toString();
        if (senhaUsuario.isEmpty()) {
            Toast.makeText(this, "Adicione um nome.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario thisUsuario = new Usuario(dbUsuarioID, nomeUsuario, emailUsuario, senhaUsuario);

        if (dbUsuario != null) {
            thisUsuario.setUsuarioID(dbUsuarioID);
            db.usuarioModel().update(thisUsuario);
            Toast.makeText(this, "Marca atualizada com sucesso.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void excluirMarca(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Exclusão de Marca")
                .setMessage("Deseja excluir essa marca?")
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
        //if(db.celularModel().getCelByMarca(dbMarcaID)!=null){
         //   Toast.makeText(this, "Impossível excluir marca com celulares cadastrados", Toast.LENGTH_SHORT).show();
       // }else {
            db.usuarioModel().delete(dbUsuario);
            Toast.makeText(this, "Marca excluída com sucesso", Toast.LENGTH_SHORT).show();

        finish();
    }
    public void voltar(View view) {
        finish();
    }
}