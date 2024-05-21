package com.example.trabalho2_pdm.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class UsuarioView {
    private ActivityMarcaListBinding binding;
    private LocalDatabase db;
    private List<Marca> marcas;
    private ListView listViewMarcas;
    private Intent edtIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMarcaListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewMarcas = binding.listMarcas;

        binding.btnHomeMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MarcaList.this, MarcaView.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, MarcaView.class);
        preencheMarcas();
    }

    private void preencheMarcas() {
        marcas = db.marcaModel().getAll();
        ArrayAdapter<Marca> marcasAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, marcas);
        listViewMarcas.setAdapter(marcasAdapter);

        listViewMarcas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Marca marcaselecionada = marcas.get(position);
                edtIntent.putExtra("MARCA_SELECIONADA_ID",
                        marcaselecionada.getMarcaID());
                startActivity(edtIntent);
            }
        });
    }
}