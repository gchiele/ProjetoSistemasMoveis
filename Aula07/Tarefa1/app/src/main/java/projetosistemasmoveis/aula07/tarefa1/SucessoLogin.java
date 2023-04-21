package projetosistemasmoveis.aula07.tarefa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SucessoLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso_login);
    }

    public  void BotaoSobre(View v){

        Intent TelaSobre = new Intent(this, Sobre.class);
        startActivity(TelaSobre);

    }
}