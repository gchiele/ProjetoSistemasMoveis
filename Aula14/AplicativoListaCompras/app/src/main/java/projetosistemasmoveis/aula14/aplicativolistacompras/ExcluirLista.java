package projetosistemasmoveis.aula14.aplicativolistacompras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExcluirLista extends AppCompatActivity {

    TextView textViewLista;
    private String IdLista;
    private BancoDados bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_lista);

        textViewLista = findViewById(R.id.textViewLista);

        bancoDados = new BancoDados();

        IdLista = getIntent().getStringExtra("IdLista");
        String NomeLista = getIntent().getStringExtra("NomeLista");

        textViewLista.setText("Deseja Realmente Apagar a Lista: " + NomeLista + "?");
    }

    public void BotaoSim(View view){
        bancoDados.ApagarLista(this,IdLista);
        Voltar();
    }

    public void BotaoNao(View view){
        Voltar();
    }

    private void Voltar(){
        Intent TelaMain = new Intent(this, MainActivity.class);
        startActivity(TelaMain);
    }
}