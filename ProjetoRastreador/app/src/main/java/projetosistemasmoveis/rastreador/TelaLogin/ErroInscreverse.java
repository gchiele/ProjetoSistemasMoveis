package projetosistemasmoveis.rastreador.TelaLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import projetosistemasmoveis.rastreador.R;

public class ErroInscreverse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erro_inscreverse);
    }

    public  void BotaoVoltar(View v){

        Intent TelaInscreverse = new Intent(this, Inscrever.class);
        startActivity(TelaInscreverse);

    }
}