package projetosistemasmoveis.aula08.atividade2;

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

    public  void Botao1 (View v){
        Intent TelaListview = new Intent(this, Listview.class);
        startActivity(TelaListview);
    }

    public  void Botao2 (View v){
        Intent TelaRecyclerview = new Intent(this, Recyclerview.class);
        startActivity(TelaRecyclerview);
    }

    public  void Botao3 (View v){

        Intent TelaCardview = new Intent(this, Cardview.class);
        startActivity(TelaCardview);

    }
}