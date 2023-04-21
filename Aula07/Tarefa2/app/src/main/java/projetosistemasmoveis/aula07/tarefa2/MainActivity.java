package projetosistemasmoveis.aula07.tarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void BotaoCalcularMedia (View v){
        EditText NotaCampo1 = (EditText) findViewById(R.id.CampoNota1);
        EditText NotaCampo2 = (EditText) findViewById(R.id.CampoNota2);

        String Numero1 = NotaCampo1.getText().toString();
        String Numero2 = NotaCampo2.getText().toString();

        Intent telaResultado = new Intent(this, Resultado.class);
        telaResultado.putExtra("Numero1", Double.parseDouble(Numero1));
        telaResultado.putExtra("Numero2", Double.parseDouble(Numero2));

        startActivity(telaResultado);

    }
}