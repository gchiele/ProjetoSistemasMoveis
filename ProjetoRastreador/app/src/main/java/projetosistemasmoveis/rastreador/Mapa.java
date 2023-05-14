package projetosistemasmoveis.rastreador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obter o objeto MapView do layout
        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        // Iniciar o carregamento assíncrono do mapa
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Obter o objeto GoogleMap
        mMap = googleMap;

        // Adicionar um marcador na posição (-23.550520, -46.633309) com título e snippet
        LatLng posicao = new LatLng(-23.550520, -46.633309);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(posicao)
                .title("São Paulo")
                .snippet("A maior cidade do Brasil");
        mMap.addMarker(markerOptions);

        // Centralizar o mapa na posição do marcador com zoom 10
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicao, 10));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void BotaoConta(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.conta_opcoes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.opcao1) {

                    Intent TelaConta = new Intent(Mapa.this, Conta.class);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(Mapa.this, Login.class);
                    startActivity(TelaLogin);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public  void BotaoVoltar(View v){

        Voltar();
    }



    public void BotaoBloqueado(View v){
        Button btnBloqueado = findViewById(R.id.btnBloqueado);
        Button btnLiberado = findViewById(R.id.btnLiberado);
        btnBloqueado.setVisibility(View.GONE);
        btnLiberado.setVisibility(View.VISIBLE);
    }

    public void BotaoLiberado(View v){
        Button btnBloqueado = findViewById(R.id.btnBloqueado);
        Button btnLiberado = findViewById(R.id.btnLiberado);
        btnBloqueado.setVisibility(View.VISIBLE);
        btnLiberado.setVisibility(View.GONE);
    }


    public void BotaoHistorico(View v){
        Intent TelaHistorico = new Intent(this, Historico.class);
        startActivity(TelaHistorico);
    }

    private void Voltar(){
        Intent TelaDispositivo = new Intent(this, Dispositivos.class);
        startActivity(TelaDispositivo);
    }

}