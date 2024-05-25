package com.example.trabalho2_pdm.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho2_pdm.R;
import com.example.trabalho2_pdm.database.LocalDatabase;
import com.example.trabalho2_pdm.databinding.ActivityEnderecoListBinding;
import com.example.trabalho2_pdm.entities.Cidade;
import com.example.trabalho2_pdm.entities.EnderecoCidade;
import com.example.trabalho2_pdm.entities.Endereco;

import java.util.List;

public class EnderecoList extends AppCompatActivity {
    private ActivityEnderecoListBinding binding;
    private LocalDatabase db;
    private int dbEnderecoID;
    private Endereco dbEndereco;
    private List<EnderecoCidade> cidEndList;
    private ListView listViewEndereco;
    private List<Cidade> cidades;
    private Spinner spnCidades;
    private ArrayAdapter<Cidade> cidadesAdapter;
    private Intent edtIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnderecoListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spnCidades = findViewById(R.id.spinnerCidades);
        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewEndereco = binding.listEnderecos;

        binding.bttnBackEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.bttnAddEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarEndereco(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherCidade();
        edtIntent = new Intent(this, EnderecoView.class);
        preencherEndereco();
    }

    private void preencherEndereco() {
        cidEndList = db.endCidModel().getAllEndeCid();

        // Verificar se os dados estão corretos
        for (EnderecoCidade enderecoCidade : cidEndList) {
            Log.d("EnderecoList", "ID: " + enderecoCidade.getEnderecoID() +
                    ", Descrição: " + enderecoCidade.getEnderecoDescricao() +
                    ", Cidade: " + enderecoCidade.getCidadeEndereco());
        }

        ArrayAdapter<EnderecoCidade> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cidEndList);
        listViewEndereco.setAdapter(adapter);

        listViewEndereco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnderecoCidade cidEndSelecionado = cidEndList.get(position);
                edtIntent.putExtra("Endereco_Selecionado_ID", cidEndSelecionado.getEnderecoID());
                startActivity(edtIntent);
            }
        });
    }


    private void preencherCidade() {
        cidades = db.cidadeModel().getAll();
        cidadesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cidades);
        cidadesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCidades.setAdapter(cidadesAdapter);
    }

    private void adicionarEndereco(View view) {
        String descricao = binding.edtDescEndereco.getText().toString();
        String latitudeStr = binding.edtLatitude.getText().toString();
        String longitudeStr = binding.edtLongitude.getText().toString();
        Cidade cidadeSelecionada = (Cidade) spnCidades.getSelectedItem();

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
            longitude = Double.valueOf(longitudeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Latitude e Longitude devem ser números válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cidadeSelecionada == null) {
            Toast.makeText(this, "Selecione uma cidade", Toast.LENGTH_SHORT).show();
            return;
        }

        int cidadeID = cidadeSelecionada.getCidadeID();
        Endereco novoEndereco = new Endereco(latitude, longitude, descricao, cidadeID);

        if (dbEndereco != null) {
            novoEndereco.setEnderecoID(dbEnderecoID);
            db.enderecoModel().update(novoEndereco);
            Toast.makeText(this, "Endereço atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            db.enderecoModel().insert(novoEndereco);
            Toast.makeText(this, "Endereço cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
        }
        preencherEndereco();
    }
}
