package projetosistemasmoveis.rastreador.TelaHistorico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import projetosistemasmoveis.rastreador.API.APIHttpClient;
import projetosistemasmoveis.rastreador.Conta.Conta;
import projetosistemasmoveis.rastreador.TelaLogin.Login;
import projetosistemasmoveis.rastreador.Objetos.ObjDadosLocalizacaoDispositivo;
import projetosistemasmoveis.rastreador.TelaMapaAtual.Mapa;
import projetosistemasmoveis.rastreador.R;

public class Historico extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;
    private String IdUsuario;
    private String IdUsuarioDispositivo;
    private String NomeDispositivo;
    private TextView textViewCampoNome;
    private TextView textViewCampoDataHoraInicial;
    private TextView textViewCampoDataHoraFinal;
    private TextView textViewCampoInfoDataHoraPesquisada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        textViewCampoNome = findViewById(R.id.CampoNome);
        textViewCampoInfoDataHoraPesquisada = findViewById(R.id.CampoInfoDataHoraPesquisada);
        textViewCampoDataHoraInicial = findViewById(R.id.CampoDataInicial);
        textViewCampoDataHoraFinal = findViewById(R.id.CampoDataFinal);

        GetIntent();

        textViewCampoNome.setText(NomeDispositivo);

        // Obter o objeto MapView do layout
        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        // Iniciar o carregamento assíncrono do mapa
        mMapView.getMapAsync(this);

        CarregaHorasDefault();
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
            Intent TelaLogin = new Intent(Historico.this, Login.class);
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

                    Intent TelaConta = new Intent(Historico.this, Conta.class);
                    TelaConta.putExtra("IdUsuario", IdUsuario);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(Historico.this, Login.class);
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

    private void Voltar(){
        Intent TelaMapa = new Intent(this, Mapa.class);
        TelaMapa.putExtra("IdUsuario", IdUsuario);
        TelaMapa.putExtra("IdUsuarioDispositivo", IdUsuarioDispositivo);
        TelaMapa.putExtra("NomeDispositivo", NomeDispositivo);
        startActivity(TelaMapa);
    }


    private void CarregaHorasDefault(){
        Calendar calendarInicial = Calendar.getInstance();
        calendarInicial.add(Calendar.HOUR_OF_DAY, -1); // Subtrai 1 hora da hora atual
        // Formata a data e hora resultante como uma string
        String selectedDateTimeInicio = String.format(Locale.getDefault(), "%02d/%02d/%d %02d:%02d", calendarInicial.get(Calendar.DAY_OF_MONTH), (calendarInicial.get(Calendar.MONTH) + 1), calendarInicial.get(Calendar.YEAR), calendarInicial.get(Calendar.HOUR_OF_DAY), calendarInicial.get(Calendar.MINUTE));

        textViewCampoDataHoraInicial.setText(selectedDateTimeInicio);

        Calendar calendarFinal = Calendar.getInstance();
        // Formata a data e hora resultante como uma string
        String selectedDateTimeFinal = String.format(Locale.getDefault(), "%02d/%02d/%d %02d:%02d", calendarFinal.get(Calendar.DAY_OF_MONTH), (calendarFinal.get(Calendar.MONTH) + 1), calendarFinal.get(Calendar.YEAR), calendarFinal.get(Calendar.HOUR_OF_DAY), calendarFinal.get(Calendar.MINUTE));

        textViewCampoDataHoraFinal.setText(selectedDateTimeFinal);

        textViewCampoInfoDataHoraPesquisada.setText(selectedDateTimeInicio+" ate "+selectedDateTimeFinal);
    }

    public void SelecionaDataInicial(View v) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1); // Subtrai 1 hora da hora atual
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Historico.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // A data foi selecionada
                TimePickerDialog timePickerDialog = new TimePickerDialog(Historico.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // A hora foi selecionada

                        String selectedDateTime = String.format(Locale.getDefault(), "%02d/%02d/%d %02d:%02d", dayOfMonth, (month + 1), year, hourOfDay, minute);
                        TextView textViewDateTime = findViewById(R.id.CampoDataInicial);
                        textViewDateTime.setText(selectedDateTime);
                    }
                }, hourOfDay, minute, true);
                timePickerDialog.show();
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }


    public void SelecionaDataFinal(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Historico.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // A data foi selecionada
                TimePickerDialog timePickerDialog = new TimePickerDialog(Historico.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // A hora foi selecionada

                        String selectedDateTime = String.format(Locale.getDefault(), "%02d/%02d/%d %02d:%02d", dayOfMonth, (month + 1), year, hourOfDay, minute);
                        TextView textViewDateTime = findViewById(R.id.CampoDataFinal);
                        textViewDateTime.setText(selectedDateTime);
                    }
                }, hourOfDay, minute, true);
                timePickerDialog.show();
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    public void BotaoPesquisar(View v){
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            String dataHoraInicio = outputFormat.format(inputFormat.parse(textViewCampoDataHoraInicial.getText().toString()));
            String dataHoraFim = outputFormat.format(inputFormat.parse(textViewCampoDataHoraFinal.getText().toString()));

            textViewCampoInfoDataHoraPesquisada.setText(textViewCampoDataHoraInicial.getText().toString()+" ate "+textViewCampoDataHoraFinal.getText().toString());

            ExecutaBuscaDadosLocalizacao executaBuscaDadosLocalizacao = new ExecutaBuscaDadosLocalizacao();
            //Chama Async Task
            executaBuscaDadosLocalizacao.execute(IdUsuarioDispositivo, dataHoraInicio, dataHoraFim);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private class ExecutaBuscaDadosLocalizacao extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Historico.this,
                    "Por favor Aguarde ...", "Buscando Dados...");
        }

        @Override
        protected String doInBackground(String... params) {
            String resposta = "";
            APIHttpClient apiHttpClient = new APIHttpClient();
            try {
                String IdUsuarioDispositivo = params[0];
                String dataHoraInicio = params[1];
                String dataHoraFim = params[2];

                String url = getString(R.string.serverUrl) + "/api/dispositivo/BuscaDadosMapa?id="+IdUsuarioDispositivo+
                    "&dataInicio="+dataHoraInicio+"&dataFim="+dataHoraFim;

                resposta = apiHttpClient.Get(url);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return resposta;
        }

        @Override
        protected void onPostExecute(String resposta){
            ObjDadosLocalizacaoDispositivo[] objDadosLocalizacaoDispositivo = ObjDadosLocalizacaoDispositivo.ConverteJsonObjLocalizacao(resposta);
            // Remover o marcador
            mMap.clear();

            if(objDadosLocalizacaoDispositivo.length > 0){

                // Adicionar marcador no primeiro ponto
                ObjDadosLocalizacaoDispositivo primeiroPonto = objDadosLocalizacaoDispositivo[0];
                LatLng primeiraPosicao = new LatLng(primeiroPonto.getLatitude(), primeiroPonto.getLongitude());
                MarkerOptions primeiroMarcador = new MarkerOptions()
                        .position(primeiraPosicao)
                        .title("Inicio")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)); // Marcador verde
                mMap.addMarker(primeiroMarcador);

                // Adicionar marcador no último ponto
                ObjDadosLocalizacaoDispositivo ultimoPonto = objDadosLocalizacaoDispositivo[objDadosLocalizacaoDispositivo.length - 1];
                LatLng ultimaPosicao = new LatLng(ultimoPonto.getLatitude(), ultimoPonto.getLongitude());
                MarkerOptions ultimoMarcador = new MarkerOptions()
                        .position(ultimaPosicao)
                        .title("Fim");
                mMap.addMarker(ultimoMarcador);

                // Adicionar linha que liga os pontos intermediários
                PolylineOptions linha = new PolylineOptions().color(Color.RED);;
                for (ObjDadosLocalizacaoDispositivo ponto : objDadosLocalizacaoDispositivo) {
                    LatLng posicao = new LatLng(ponto.getLatitude(), ponto.getLongitude());
                    linha.add(posicao);
                }
                mMap.addPolyline(linha);

                // Centralizar o mapa na posição do marcador do ultimo ponto com zoom 10
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ultimaPosicao, 10));
            }

            progressDialog.dismiss();
        }
    }


}