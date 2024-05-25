package com.example.trabalho2_pdm.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho2_pdm.database.LocalDatabase;
import com.example.trabalho2_pdm.databinding.ActivityMapViewBinding;
import com.example.trabalho2_pdm.entities.Endereco;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapView extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityMapViewBinding binding;
    private GoogleMap map;
    private LocalDatabase db;
    private com.google.android.gms.maps.MapView mapview;
    private int enderecoID;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = LocalDatabase.getDatabase(getApplicationContext());

        binding = ActivityMapViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        enderecoID = getIntent().getIntExtra("Endereco_Selecionado_ID", -1);

        mapview = binding.mapView;
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapview.onCreate(mapViewBundle);
        mapview.getMapAsync(this);

        binding.bttnBackMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        MapsInitializer.initialize(this);
        Endereco novoEnd = db.enderecoModel().getEndereco(enderecoID);

        if (novoEnd != null) {
            LatLng defaultLocation = new LatLng(novoEnd.getLatitude(), novoEnd.getLongitude());
            map.addMarker(new MarkerOptions().position(defaultLocation).title("Marcador em " + novoEnd.getDescricao()));
            map.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,
                    15));
        }else{
            Toast.makeText(this, "Endereço não encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    protected void onStart() {
        super.onStart();
        mapview.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
    }

    @Override
    protected void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapview.onSaveInstanceState(mapViewBundle);
    }
}