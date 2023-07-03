package projetosistemasmoveis.rastreador.TelaMapaAtual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;

import projetosistemasmoveis.rastreador.API.APIHttpClient;
import projetosistemasmoveis.rastreador.Conta.Conta;
import projetosistemasmoveis.rastreador.TelaLogin.Login;
import projetosistemasmoveis.rastreador.Objetos.ObjDadosLocalizacaoDispositivo;
import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.TelaDispositivos.Dispositivos;
import projetosistemasmoveis.rastreador.TelaHistorico.Historico;
import projetosistemasmoveis.rastreador.Util;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;
    private Handler handler;
    private Runnable runnable;
    private String IdUsuario;
    private String IdUsuarioDispositivo;
    private String NomeDispositivo;
    private ObjDadosLocalizacaoDispositivo LocalizacaoAtual;
    private TextView textViewCampoNome;
    private TextView textViewCampoUltimaAtualizacao;

    private Boolean BotaoAtualizaLocalizacao = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        textViewCampoNome = findViewById(R.id.CampoNome);
        textViewCampoUltimaAtualizacao = findViewById(R.id.CampoUltimaAtualizacao);

        GetIntent();

        AtualizaBotaoLocalizacao();

        textViewCampoNome.setText(NomeDispositivo);
        textViewCampoUltimaAtualizacao.setText("Ultima Atualização:");

        // Obter o objeto MapView do layout
        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        // Iniciar o carregamento assíncrono do mapa
        mMapView.getMapAsync(this);

        // Inicializar o Handler
        handler = new Handler();

        // Inicializar o Runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                // Chamar o método desejado aqui
                BuscaStatusBotaoStop();
                BuscaDadosLocalizacao();
                // Agendar a próxima execução após 2 segundo
                handler.postDelayed(this, 2000);
            }
        };
    }

    public void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("IdUsuario")) {
            IdUsuario = intent.getStringExtra("IdUsuario");
            if (intent.hasExtra("IdUsuarioDispositivo")) {
                IdUsuarioDispositivo = intent.getStringExtra("IdUsuarioDispositivo");
                if (intent.hasExtra("NomeDispositivo")) {
                    NomeDispositivo = intent.getStringExtra("NomeDispositivo");
                }else{
                    Voltar();
                }
            } else {
                Voltar();
            }
        } else {
            Intent TelaLogin = new Intent(Mapa.this, Login.class);
            startActivity(TelaLogin);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Obter o objeto GoogleMap
        mMap = googleMap;
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        handler.postDelayed(runnable, 0);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();

        // Remover callbacks para evitar vazamento de memória
        handler.removeCallbacks(runnable);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

        // Remover callbacks para evitar vazamento de memória
        handler.removeCallbacks(runnable);
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
                    TelaConta.putExtra("IdUsuario", IdUsuario);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(Mapa.this, Login.class);
                    TelaLogin.putExtra("Sair", "true");
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

    public void BuscaDadosLocalizacao(){
        ExecutaBuscaDadosLocalizacao executaBuscaDadosLocalizacao = new ExecutaBuscaDadosLocalizacao();
        //Chama Async Task
        executaBuscaDadosLocalizacao.execute(IdUsuarioDispositivo);
    }

    public void BuscaStatusBotaoStop(){
        ExecutaBuscaStatusBotaoStop executaBuscaStatusBotaoStop = new ExecutaBuscaStatusBotaoStop();
        executaBuscaStatusBotaoStop.execute(IdUsuarioDispositivo);
    }
    public void SetaStatusBotaoStop(String Estado){
        ExecutaExecutaStatusBotaoStop executaExecutaStatusBotaoStop = new ExecutaExecutaStatusBotaoStop();
        executaExecutaStatusBotaoStop.execute(IdUsuarioDispositivo, Estado);
    }


    private class ExecutaBuscaDadosLocalizacao extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){}

        @Override
        protected String doInBackground(String... params) {
            String resposta = "";
            APIHttpClient apiHttpClient = new APIHttpClient();
            try {
                String IdUsuarioDispositivo = params[0];
                String url = getString(R.string.serverUrl) + "/api/dispositivo/BuscaDadosMarcador/"+ IdUsuarioDispositivo;

                resposta = apiHttpClient.Get(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resposta;
        }

        @Override
        protected void onPostExecute(String resposta){
            try {
                LocalizacaoAtual = ObjDadosLocalizacaoDispositivo.fromJson(resposta);
                //Verifica dado Valido
                if(LocalizacaoAtual.getDataHora().equals("") == false){
                    AtualizaMapa(LocalizacaoAtual);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class ExecutaBuscaStatusBotaoStop extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){}

        @Override
        protected String doInBackground(String... params) {
            String resposta = "";
            APIHttpClient apiHttpClient = new APIHttpClient();
            try {
                String IdUsuarioDispositivo = params[0];
                String url = getString(R.string.serverUrl) + "/api/dispositivo/BuscaEstadoBotaoStop/"+ IdUsuarioDispositivo;

                resposta = apiHttpClient.Get(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resposta;
        }

        @Override
        protected void onPostExecute(String resposta){
            if(resposta.equals("true")){
                AlteraBotaoBloqueado();
            }else{
                AlteraBotaoLiberado();
            }
        }
    }

    private class ExecutaExecutaStatusBotaoStop extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){}

        @Override
        protected String doInBackground(String... params) {
            String resposta = "";
            APIHttpClient apiHttpClient = new APIHttpClient();
            try {
                String IdUsuarioDispositivo = params[0];
                String Estado = params[1];
                String url = getString(R.string.serverUrl) + "/api/dispositivo/"+Estado;
                String jsonInputString = "\"" + IdUsuarioDispositivo + "\"";

                resposta = apiHttpClient.Put(url,jsonInputString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resposta;
        }

        @Override
        protected void onPostExecute(String guid){
            guid = guid.replace("\"", "");

            Util util = new Util();

            if (util.isValidGuid(guid)) {
                BuscaStatusBotaoStop();
            }
        }
    }
    public void AtualizaMapa(ObjDadosLocalizacaoDispositivo objDadosLocalizacaoDispositivo){

        textViewCampoUltimaAtualizacao.setText("Ultima Atualização: "+objDadosLocalizacaoDispositivo.getDataHora());

        if(mMap != null){
            // Remover o marcador
            mMap.clear();

            // Adicionar um marcador na posição (-23.550520, -46.633309) com título e snippet
            LatLng posicao = new LatLng(objDadosLocalizacaoDispositivo.getLatitude(), objDadosLocalizacaoDispositivo.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(posicao)
                    .title(NomeDispositivo)
                    .snippet("Selites: "+objDadosLocalizacaoDispositivo.getSatelites());
            mMap.addMarker(markerOptions);

            if(BotaoAtualizaLocalizacao){
                // Centralizar o mapa na posição do marcador com zoom 10
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicao, 17));
            }
        }
    }
    public void BotaoBloqueado(View v){
        SetaStatusBotaoStop("LiberarVeiculo");
    }
    public void BotaoLiberado(View v){
        SetaStatusBotaoStop("BloquearVeiculo");
    }
    public void AlteraBotaoBloqueado(){
        Button btnBloqueado = findViewById(R.id.btnBloqueado);
        Button btnLiberado = findViewById(R.id.btnLiberado);
        btnBloqueado.setVisibility(View.VISIBLE);
        btnLiberado.setVisibility(View.GONE);
    }
    public void AlteraBotaoLiberado(){
        Button btnBloqueado = findViewById(R.id.btnBloqueado);
        Button btnLiberado = findViewById(R.id.btnLiberado);
        btnBloqueado.setVisibility(View.GONE);
        btnLiberado.setVisibility(View.VISIBLE);
    }

    public void BotaoAtualizaLocalizacao(View v){
        BotaoAtualizaLocalizacao = !BotaoAtualizaLocalizacao;
        AtualizaBotaoLocalizacao();
    }

    public void AtualizaBotaoLocalizacao(){
        Button botaoMinhaLocalizacao = findViewById(R.id.btnAtualizaLocalizacao);
        if (BotaoAtualizaLocalizacao) {
            botaoMinhaLocalizacao.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreen)));
        } else {
            botaoMinhaLocalizacao.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBlue)));
        }
    }
    public void BotaoHistorico(View v){
        Intent TelaHistorico = new Intent(this, Historico.class);
        TelaHistorico.putExtra("IdUsuario", IdUsuario);
        TelaHistorico.putExtra("IdUsuarioDispositivo", IdUsuarioDispositivo);
        TelaHistorico.putExtra("NomeDispositivo", NomeDispositivo);
        startActivity(TelaHistorico);
    }
    private void Voltar(){
        Intent TelaDispositivo = new Intent(this, Dispositivos.class);
        TelaDispositivo.putExtra("IdUsuario", IdUsuario);
        startActivity(TelaDispositivo);
    }



}