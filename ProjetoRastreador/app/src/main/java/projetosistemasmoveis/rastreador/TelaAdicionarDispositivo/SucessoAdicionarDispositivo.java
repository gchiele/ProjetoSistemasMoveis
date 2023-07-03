package projetosistemasmoveis.rastreador.TelaAdicionarDispositivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import projetosistemasmoveis.rastreador.TelaLogin.Login;
import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.TelaDispositivos.Dispositivos;

public class SucessoAdicionarDispositivo extends AppCompatActivity {
    private String IdUsuario = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso_adicionar_dispositivo);
        GetIntent();
    }
    public void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("IdUsuario")) {
            IdUsuario = intent.getStringExtra("IdUsuario");
        } else {
            Intent TelaLogin = new Intent(SucessoAdicionarDispositivo.this, Login.class);
            startActivity(TelaLogin);
        }
    }

    public  void BotaoVoltar(View v){
        Intent TelaDispostivos = new Intent(this, Dispositivos.class);
        TelaDispostivos.putExtra("IdUsuario", IdUsuario);
        startActivity(TelaDispostivos);
    }
}