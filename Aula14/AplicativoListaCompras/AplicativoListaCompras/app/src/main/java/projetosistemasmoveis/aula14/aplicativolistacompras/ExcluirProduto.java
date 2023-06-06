package projetosistemasmoveis.aula14.aplicativolistacompras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExcluirProduto extends AppCompatActivity {

    private String CampoNomeLista;
    private ArrayList<Produto> listaProdutos;
    TextView textViewProduto;
    private String IdLista = null;

    private int selectedItem = -1; // Índice do item selecionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_produto);

        textViewProduto = findViewById(R.id.textViewProduto);

        String NomeLista = getIntent().getStringExtra("NomeLista");
        if(NomeLista != null){
            CampoNomeLista = NomeLista;
        }

        String idLista = getIntent().getStringExtra("IdLista");
        if(idLista != null){
            IdLista = idLista;
        }

        selectedItem = getIntent().getIntExtra("ItemSelecionado", -1);

        String json = getIntent().getStringExtra("listaProdutos");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Produto>>() {}.getType();
        ArrayList<Produto> listaProdutos2 = gson.fromJson(json, type);
        if (listaProdutos2 != null) {
            listaProdutos = listaProdutos2;
        }

        Produto produto = new Produto();
        produto = listaProdutos.get(selectedItem);

        textViewProduto.setText("Deseja Realmente Excluir o Produto: "+produto.getNome()+"?");
    }

    public void BotaoSim(View view){
        listaProdutos.remove(selectedItem);
        Voltar();
    }

    public void BotaoNao(View view){
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