package projetosistemasmoveis.aula14.aplicativolistacompras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelecionaProduto extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Produto> listaProdutosBanco;
    private ArrayList<Produto> listaProdutos;
    private BancoDados bancoDados;
    private int selectedItem = -1; // Índice do item selecionado
    private String CampoNomeLista;
    private String IdLista = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleciona_produto);

        listView = findViewById(R.id.listView);

        listaProdutos = new ArrayList<>();

        bancoDados = new BancoDados();

        String NomeLista = getIntent().getStringExtra("NomeLista");
        if(NomeLista != null){
            CampoNomeLista = NomeLista;
        }

        String idLista = getIntent().getStringExtra("IdLista");
        if(idLista != null){
            IdLista = idLista;
        }

        String json = getIntent().getStringExtra("listaProdutos");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Produto>>() {}.getType();
        ArrayList<Produto> listaProdutos2 = gson.fromJson(json, type);
        if (listaProdutos2 != null) {
            listaProdutos = listaProdutos2;
        }

        listaProdutosBanco = bancoDados.BuscaListasProdutos(this);

        CustomAdapterListaSelecionarProdutos adapter = new CustomAdapterListaSelecionarProdutos(this, listaProdutosBanco);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedItem(position);
                selectedItem = adapter.getSelectedItem();
            }
        });
    }


    public void BotaoAdicionar (View view){
        if(selectedItem > -1){
            listaProdutos.add(listaProdutosBanco.get(selectedItem));
            Voltar();
        }

    }
    public void BotaoCancelar (View view){
        Voltar();
    }

    private void Voltar(){
        Gson gson = new Gson();
        String json = gson.toJson(listaProdutos);

        Intent TelaNovaLista = new Intent(this, NovaLista.class);
        TelaNovaLista.putExtra("IdLista", IdLista);
        TelaNovaLista.putExtra("NomeLista", CampoNomeLista);
        TelaNovaLista.putExtra("listaProdutos", json);
        startActivity(TelaNovaLista);
    }

}