package projetosistemasmoveis.rastreador.TelaLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.TelaLogin.Login;

public class EsqueceuSenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);
    }

    public void BotaoRecuperarSenha(View v){
        Intent TelaLogin = new Intent(this, Login.class);
        startActivity(TelaLogin);
    }


    public  void BotaoVoltar(View v){

        Intent TelaLogin = new Intent(this, Login.class);
        startActivity(TelaLogin);

    }
}