package projetosistemasmoveis.rastreador;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Locale;

public class Historico extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        // Obter o objeto MapView do layout
        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        // Iniciar o carregamento assíncrono do mapa
        mMapView.getMapAsync(this);

        CarregaHorasDefault();
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

                    Intent TelaConta = new Intent(Historico.this, Conta.class);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(Historico.this, Login.class);
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
        startActivity(TelaMapa);
    }


    private void CarregaHorasDefault(){
        Calendar calendarInicial = Calendar.getInstance();
        calendarInicial.add(Calendar.HOUR_OF_DAY, -1); // Subtrai 1 hora da hora atual
        // Formata a data e hora resultante como uma string
        String selectedDateTimeInicio = String.format(Locale.getDefault(), "%02d/%02d/%d %02d:%02d", calendarInicial.get(Calendar.DAY_OF_MONTH), (calendarInicial.get(Calendar.MONTH) + 1), calendarInicial.get(Calendar.YEAR), calendarInicial.get(Calendar.HOUR_OF_DAY), calendarInicial.get(Calendar.MINUTE));
        TextView textViewDateTimeInicio = findViewById(R.id.CampoDataInicial);
        textViewDateTimeInicio.setText(selectedDateTimeInicio);

        Calendar calendarFinal = Calendar.getInstance();
        // Formata a data e hora resultante como uma string
        String selectedDateTimeFinal = String.format(Locale.getDefault(), "%02d/%02d/%d %02d:%02d", calendarFinal.get(Calendar.DAY_OF_MONTH), (calendarFinal.get(Calendar.MONTH) + 1), calendarFinal.get(Calendar.YEAR), calendarFinal.get(Calendar.HOUR_OF_DAY), calendarFinal.get(Calendar.MINUTE));
        TextView textViewDateTimeFinal = findViewById(R.id.CampoDataFinal);
        textViewDateTimeFinal.setText(selectedDateTimeFinal);
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

    }




}