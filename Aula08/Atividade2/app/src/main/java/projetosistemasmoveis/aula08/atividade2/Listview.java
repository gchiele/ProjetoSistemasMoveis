package projetosistemasmoveis.aula08.atividade2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Listview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
    }

    public  void BotaoVoltar(View v){

        Intent TelaMainActivity = new Intent(this, MainActivity.class);
        startActivity(TelaMainActivity);

    }
}