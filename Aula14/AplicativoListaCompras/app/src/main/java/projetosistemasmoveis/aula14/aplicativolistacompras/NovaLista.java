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
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NovaLista extends AppCompatActivity {
    private ListView listView;
    private TextInputEditText CampoNomeLista;
    private TextView textViewPreco;
    private BancoDados bancoDados;
    private ArrayList<Produto> listaProdutos;
    private int selectedItem = -1; // Índice do item selecionado
    private String IdLista = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_lista);

        listView = findViewById(R.id.listView);
        CampoNomeLista = findViewById(R.id.textViewNomeLista);
        textViewPreco = findViewById(R.id.textViewPreco);

        listaProdutos = new ArrayList<>();

        bancoDados = new BancoDados();

        // VERIFICA SE TEM ENTRADA DE DADOS
        String idLista = getIntent().getStringExtra("IdLista");
        if(idLista != null){
            IdLista = idLista;
            listaProdutos = bancoDados.BuscaListasProdutos(this , idLista);
        }

        String NomeLista = getIntent().getStringExtra("NomeLista");
        if(NomeLista != null){
            CampoNomeLista.setText(NomeLista);
        }

        String json = getIntent().getStringExtra("listaProdutos");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Produto>>() {}.getType();
        ArrayList<Produto> listaProdutos2 = gson.fromJson(json, type);

        if (listaProdutos2 != null) {
            listaProdutos = listaProdutos2;
        }

        CalculaPreco(listaProdutos);

        CustomAdapterListaComprasProdutos adapter = new CustomAdapterListaComprasProdutos(this, listaProdutos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedItem(position);
                selectedItem = adapter.getSelectedItem();
            }
        });

    }

    public void BotaoExcluirProduto (View view){
        if(selectedItem > -1){
            Gson gson = new Gson();
            String json = gson.toJson(listaProdutos);

            Intent intent = new Intent(this, ExcluirProduto.class);
            intent.putExtra("IdLista", IdLista);
            intent.putExtra("NomeLista", CampoNomeLista.getText().toString());
            intent.putExtra("ItemSelecionado", selectedItem);
            intent.putExtra("listaProdutos", json);
            startActivity(intent);
        }
    }

    public void BotaoSalvar (View view){
        String NomeLista = CampoNomeLista.getText().toString();

        bancoDados.SalvarLista(this,IdLista,GetDataHoraAtual(), NomeLista, listaProdutos);
        Voltar();
    }


    public void BotaoCancelar (View view){
        Voltar();
    }

    private void Voltar(){
        Intent TelaMain = new Intent(this, MainActivity.class);
        startActivity(TelaMain);
    }


    public void BotaoAdicionarProduto (View view){
        Gson gson = new Gson();
        String json = gson.toJson(listaProdutos);

        Intent intent = new Intent(this, SelecionaProduto.class);
        intent.putExtra("IdLista", IdLista);
        intent.putExtra("NomeLista", CampoNomeLista.getText().toString());
        intent.putExtra("listaProdutos", json);
        startActivity(intent);
    }

    private void CalculaPreco(ArrayList<Produto> produtos){
        double preco = 0.0;
        for (Produto produto : produtos) {
            preco = preco + produto.getPreco();
        }
        textViewPreco.setText("Preço a Pagar: R$"+preco);
    }

    private String GetDataHoraAtual(){
        // Obtém a instância do calendário
        Calendar calendar = Calendar.getInstance();

        // Obtém a data e hora atual
        String dataHoraAtual;

        // Formato desejado para a data e hora (exemplo: "dd/MM/yyyy HH:mm:ss")
        SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

        // Obtém a representação de string da data e hora atual
        dataHoraAtual = formatoDataHora.format(calendar.getTime());

        return dataHoraAtual;
    }


}