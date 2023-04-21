package projetosistemasmoveis.aula07.tarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class Resultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        TextView Texto1 = (TextView) findViewById(R.id.Texto1);
        TextView Texto2 = (TextView) findViewById(R.id.Texto2);

        Intent telaNumero = getIntent();
        double Nota1 = telaNumero.getDoubleExtra("Numero1", 0);
        double Nota2 = telaNumero.getDoubleExtra("Numero2", 0);

        double Media = (Nota1 + Nota2)/2;

        Texto1.setText("Media Final = "+Media);
        Texto1.setTextSize(20);

        if(Media >= 7){
            Texto2.setText("Aprovado!");
            Texto2.setTextColor(Color.rgb(0,255,0));
            Texto2.setTextSize(30);
        }else{
            Texto2.setText("Reprovado");
            Texto2.setTextColor(Color.rgb(255,0,0));
            Texto2.setTextSize(30);
        }

    }
}