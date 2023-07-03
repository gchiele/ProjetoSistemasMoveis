package projetosistemasmoveis.rastreador.TelaAdicionarDispositivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import projetosistemasmoveis.rastreador.TelaLogin.Login;
import projetosistemasmoveis.rastreador.R;

public class ErroAdicionarDispositivo extends AppCompatActivity {
    private String IdUsuario = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erro_adicionar_dispositivo);
        GetIntent();
    }

    public void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("IdUsuario")) {
            IdUsuario = intent.getStringExtra("IdUsuario");
        } else {
            Intent TelaLogin = new Intent(ErroAdicionarDispositivo.this, Login.class);
            startActivity(TelaLogin);
        }
    }


    public  void BotaoVoltar(View v){
        Intent TelaAdicionarDispositivos = new Intent(this, AdicionarDispositivo.class);
        TelaAdicionarDispositivos.putExtra("IdUsuario", IdUsuario);
        startActivity(TelaAdicionarDispositivos);
    }
}