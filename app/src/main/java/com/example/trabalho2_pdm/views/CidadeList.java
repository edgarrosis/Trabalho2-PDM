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
import com.example.trabalho2_pdm.databinding.ActivityCidadeListBinding;
import com.example.trabalho2_pdm.entities.Cidade;

import java.util.List;

public class CidadeList extends AppCompatActivity {
    private ActivityCidadeListBinding binding;
    private LocalDatabase db;
    private int dbCidadeID;
    private Cidade dbCidade;
    private List<Cidade> cidadeList;
    private ListView listViewCidade;
    private Intent edtIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCidadeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewCidade = binding.listCidade;

        binding.backBttnCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.addBttnCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarCidade(view);
            }
        });

        binding.addBttnEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CidadeList.this, EnderecoList.class);
                startActivity(it);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, CidadeView.class);
        preencherCidade();
    }

    private void preencherCidade() {
        cidadeList = db.cidadeModel().getAll();
        ArrayAdapter<Cidade> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cidadeList);
        listViewCidade.setAdapter(adapter);

        listViewCidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cidade cidadeSelecionado = cidadeList.get(position);
                edtIntent.putExtra("Cidade_Selecionada_ID", cidadeSelecionado.getCidadeID());
                startActivity(edtIntent);
            }
        });
    }
    public void adicionarCidade(View view) {
        String cidade = binding.edtCidadeNome.getText().toString();
        String estado = binding.edtEstadoNome.getText().toString();

        if (cidade.isEmpty()) {
            Toast.makeText(this, "Preencha o nome da cidade", Toast.LENGTH_SHORT).show();
            return;
        }
        if (estado.isEmpty()) {
            Toast.makeText(this, "Preencha o nome do estado", Toast.LENGTH_SHORT).show();
            return;
        }

        Cidade novaCidade = new Cidade(cidade, estado);

        if (dbCidade != null) {
            novaCidade.setCidadeID(dbCidadeID);
            db.cidadeModel().update(novaCidade);
            Toast.makeText(this, "Cidade atualizada com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            db.cidadeModel().insert(novaCidade);
            Toast.makeText(this, "Cidade cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
        }
        preencherCidade();
    }
}