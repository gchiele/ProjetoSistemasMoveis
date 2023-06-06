package projetosistemasmoveis.aula14.aplicativolistacompras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ListaCompra> listaCompras;
    private BancoDados bancoDados;
    private int selectedItem = -1; // Índice do item selecionado
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PopularProdutos();

        bancoDados = new BancoDados();

        listView = findViewById(R.id.listView);

        listaCompras = new ArrayList<>();
        listaCompras = bancoDados.BuscaListasCompras(this);

        CustomAdapterListaCompras adapter = new CustomAdapterListaCompras(this, listaCompras);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedItem(position);
                selectedItem = adapter.getSelectedItem();
            }
        });
    }

    public void BotaoEditarLista (View view){
        if(selectedItem > -1){
            ListaCompra listaCompra = listaCompras.get(selectedItem);

            Intent intent = new Intent(this, NovaLista.class);
            intent.putExtra("IdLista", listaCompra.getId());
            intent.putExtra("NomeLista", listaCompra.getNome());
            startActivity(intent);
        }
    }

    public void BotaoApagarLista (View view){
        if(selectedItem > -1){
            ListaCompra listaCompra = listaCompras.get(selectedItem);

            Intent intent = new Intent(this, ExcluirLista.class);
            intent.putExtra("IdLista", listaCompra.getId());
            intent.putExtra("NomeLista", listaCompra.getNome());
            startActivity(intent);
        }
    }
    public void BotaoAdicionarNovaLista (View view){
        Intent TelaNovaLista = new Intent(this, NovaLista.class);
        startActivity(TelaNovaLista);
    }




    private void PopularProdutos(){
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();


        // Seleciona a quantidade de linhas na tabela "produto"
        String query = "SELECT COUNT(*) FROM produto";
        Cursor cursor = db.rawQuery(query, null);

        int rowCount = 0;
        if (cursor != null && cursor.moveToFirst()) {
            rowCount = cursor.getInt(0);
            cursor.close();
        }

        // SE A TABELA ESTA VAZIA ADICIONA OS PRODUTOS
        if (rowCount == 0) {

            // POPULA TABELA PRODUTO
            // Insira os dados na tabela
            ContentValues values = new ContentValues();

            String[] nomes = {"Arroz 1 Kg", "Leite longa vida", "Carne Friboi", "Feijão carioquinha 1 Kg", "Refrigerante coca-cola 2 litros"};
            Double[] precos = {2.69, 2.70, 16.70, 3.38, 3.0};

            for (int i = 0; i < 5; i++) {
                // Gere um UUID
                UUID uuid = UUID.randomUUID();
                String id = uuid.toString();

                values.put("id", id);
                values.put("nome", nomes[i]);
                values.put("preco", precos[i]);

                long newRowId = db.insert("produto", null, values);

                if (newRowId != -1) {
                    Log.d("Database", "Inserção bem-sucedida. ID da nova linha: " + newRowId);
                } else {
                    Log.d("Database", "Erro ao inserir dados.");
                }
            }
        }

        db.close();
    }



}

