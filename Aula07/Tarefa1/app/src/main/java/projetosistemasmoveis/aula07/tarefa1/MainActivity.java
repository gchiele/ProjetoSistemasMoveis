package projetosistemasmoveis.aula07.tarefa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void BotaoLogin (View v){

        Intent TelaLogin = new Intent(this, Login.class);
        startActivity(TelaLogin);

    }

    public  void BotaoCadastro (View v){

        Intent TelaCadastro = new Intent(this, Cadastro.class);
        startActivity(TelaCadastro);

    }

    public  void BotaoSobre(View v){

        Intent TelaSobre = new Intent(this, Sobre.class);
        startActivity(TelaSobre);

    }

    public  void BotaoTeste(View v){

        Intent TelaTeste = new Intent(this, Teste.class);
        startActivity(TelaTeste);

    }
}