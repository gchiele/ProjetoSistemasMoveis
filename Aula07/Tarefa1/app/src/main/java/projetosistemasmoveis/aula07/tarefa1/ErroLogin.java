package projetosistemasmoveis.aula07.tarefa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ErroLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erro_login);
    }

    public  void BotaoVoltar(View v){

        Intent TelaLogin = new Intent(this, Login.class);
        startActivity(TelaLogin);

    }
}