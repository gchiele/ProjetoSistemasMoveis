package projetosistemasmoveis.aula07.tarefa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SucessoCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso_cadastro);

        TextView CampoTexto = (TextView) findViewById(R.id.CampoTexto);

        Intent TelaAnterior = getIntent();
        String Nome = TelaAnterior.getStringExtra("Nome");

        CampoTexto.setText("Olá "+Nome+". "+"Cadastro realizado com sucesso");

    }

    public  void BotaoSobre(View v){

        Intent TelaSobre = new Intent(this, Sobre.class);
        startActivity(TelaSobre);
    }
}