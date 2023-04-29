package projetosistemasmoveis.aula08.atividade2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recyclerview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CustomAdapter(GeraDados()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private List<String> GeraDados() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("Elemento :"+String.valueOf(i) + " Adicionado");
        }
        return data;
    }

    public  void BotaoVoltar(View v){

        Intent TelaMainActivity = new Intent(this, MainActivity.class);
        startActivity(TelaMainActivity);

    }

}