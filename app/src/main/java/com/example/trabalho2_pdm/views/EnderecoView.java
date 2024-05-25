package com.example.trabalho2_pdm.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabalho2_pdm.R;
import com.example.trabalho2_pdm.database.LocalDatabase;
import com.example.trabalho2_pdm.databinding.ActivityEnderecoViewBinding;
import com.example.trabalho2_pdm.entities.Cidade;
import com.example.trabalho2_pdm.entities.Endereco;

public class EnderecoView extends AppCompatActivity {
    private LocalDatabase db;
    private ActivityEnderecoViewBinding binding;
    private int dbEnderecoID;
    private Endereco dbEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnderecoViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        dbEnderecoID = getIntent().getIntExtra("Endereco_Selecionado_ID", -1);

        if(dbEnderecoID != -1) {
            getDBEndereco();
        }
        binding.bttnAcessarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EnderecoView.this, MapView.class);
                it.putExtra("Endereco_Selecionado_ID", dbEnderecoID);
                startActivity(it);
                finish();
            }
        });
    }

    private void getDBEndereco() {
        dbEndereco = db.enderecoModel().getEndereco(dbEnderecoID);
        if (dbEndereco != null) {
            binding.edtLatitudeNova.setText(String.valueOf(dbEndereco.getLatitude()));
            binding.edtLongitudeNova.setText(String.valueOf(dbEndereco.getLongitude()));
            binding.edtNovaDescricao.setText(dbEndereco.getDescricao());
        } else {
            Toast.makeText(this, "Endereço não encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void salvarEndereco(View view) {
        String descricao = binding.edtNovaDescricao.getText().toString();
        String latitudeStr = binding.edtLatitudeNova.getText().toString();
        String longitudeStr = binding.edtLongitudeNova.getText().toString();

        if (descricao.isEmpty()) {
            Toast.makeText(this, "Preencha a descrição do endereço", Toast.LENGTH_SHORT).show();
            return;
        }
        if (latitudeStr.isEmpty() || longitudeStr.isEmpty()) {
            Toast.makeText(this, "Preencha a latitude e longitude do endereço", Toast.LENGTH_SHORT).show();
            return;
        }

        Double latitude = null;
        Double longitude = null;
        try {
            latitude = Double.valueOf(latitudeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Latitude deve ser um número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            longitude = Double.valueOf(longitudeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Longitude deve ser um número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbEndereco != null) {
            int cidadeID = dbEndereco.getCidadeIDFK();
            Endereco novoEndereco = new Endereco(latitude, longitude, descricao, cidadeID);
            novoEndereco.setEnderecoID(dbEnderecoID);
            db.enderecoModel().update(novoEndereco);
            Toast.makeText(this, "Endereço atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Endereço não encontrado.", Toast.LENGTH_SHORT).show();
        }

        finish();
    }


    public void excluirEndereco(View view) {
        if(dbEndereco != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Exclusão de Endereço")
                    .setMessage("Deseja excluir esse endereço?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            excluir();
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        } else {
            Toast.makeText(this, "Endereço não encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        if(dbEndereco != null) {
            db.enderecoModel().delete(dbEndereco);
            Toast.makeText(this, "Endereço excluido com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Endereço não encontrado", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void voltar(View view) {
        finish();
    }
}