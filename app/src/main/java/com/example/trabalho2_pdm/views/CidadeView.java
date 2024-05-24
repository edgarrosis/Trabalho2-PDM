package com.example.trabalho2_pdm.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho2_pdm.database.LocalDatabase;
import com.example.trabalho2_pdm.databinding.ActivityCidadeViewBinding;
import com.example.trabalho2_pdm.entities.Cidade;

public class CidadeView extends AppCompatActivity {
    private LocalDatabase db;
    private ActivityCidadeViewBinding binding;
    private int dbCidadeID;
    private Cidade dbCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCidadeViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        dbCidadeID = getIntent().getIntExtra("Cidade_Selecionada_ID", -1);

        if (dbCidadeID != -1) {
            getDBCidade();
        }
    }

    private void getDBCidade() {
        dbCidade = db.cidadeModel().getCidID(dbCidadeID);
        if (dbCidade != null) {
            binding.edtModCidade.setText(dbCidade.getCidade());
            binding.edtModEstado.setText(dbCidade.getEstado());
        } else {
            Toast.makeText(this, "Cidade não encontrada.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void salvarCidade(View view) {
        String cidade = binding.edtModCidade.getText().toString();
        String estado = binding.edtModEstado.getText().toString();

        if (cidade.isEmpty()) {
            Toast.makeText(this, "Adicione um nome para cidade.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (estado.isEmpty()) {
            Toast.makeText(this, "Adicione um nome para estado.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cidade thisCidade = new Cidade(cidade, estado);
        if (dbCidade != null) {
            thisCidade.setCidadeID(dbCidadeID);
            db.cidadeModel().update(thisCidade);
            Toast.makeText(this, "Cidade atualizada com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            db.cidadeModel().insert(thisCidade);
            Toast.makeText(this, "Cidade criada com sucesso.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void excluirCidade(View view) {
        if (dbCidade != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Exclusão de Cidade")
                    .setMessage("Deseja excluir essa cidade?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            excluir();
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        } else {
            Toast.makeText(this, "Cidade não encontrada.", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        if (dbCidade != null) {
            db.cidadeModel().delete(dbCidade);
            Toast.makeText(this, "Cidade excluída com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cidade não encontrada.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void voltar(View view) {
        finish();
    }
}
