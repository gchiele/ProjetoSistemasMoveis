package projetosistemasmoveis.rastreador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ErroAdicionarDispositivo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erro_adicionar_dispositivo);
    }

    public  void BotaoVoltar(View v){
        Intent TelaDispostivos = new Intent(this, Dispositivos.class);
        startActivity(TelaDispostivos);
    }
}