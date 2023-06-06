package projetosistemasmoveis.aula14.aplicativolistacompras;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class BancoDados {

    private DatabaseHelper databaseHelper;

    public Produto BuscaProduto(Context context, String idProduto){
        Produto produto = new Produto();

        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String query = "SELECT * FROM produto WHERE id = '" + idProduto+"'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            produto.setId(cursor.getString(cursor.getColumnIndexOrThrow("id")));
            produto.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            produto.setPreco(cursor.getDouble(cursor.getColumnIndexOrThrow("preco")));
        }

        // Fecha o cursor e o banco de dados
        cursor.close();
        db.close();

        return produto;
    }

    public ArrayList BuscaListasProdutos(Context context){
        ArrayList listaProdutosBanco = new ArrayList<>();

        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String query = "SELECT * FROM produto";
        Cursor cursor = db.rawQuery(query, null);

        // Percorre os resultados da consulta
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            Double preco = cursor.getDouble(cursor.getColumnIndexOrThrow("preco"));

            listaProdutosBanco.add(new Produto(id,nome, preco));
        }

        // Fecha o cursor e o banco de dados
        cursor.close();
        db.close();

        return listaProdutosBanco;
    }

    public ArrayList BuscaListasProdutos(Context context, String idLista){
        ArrayList listaProdutos = new ArrayList<>();

        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String query = "SELECT produto.id, produto.nome, produto.preco FROM lista_produtos INNER JOIN produto ON lista_produtos.id_produto = produto.id  WHERE id_lista = '"+idLista+"'";
        Cursor cursor = db.rawQuery(query, null);

        // Percorre os resultados da consulta
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            Double preco = cursor.getDouble(cursor.getColumnIndexOrThrow("preco"));

            listaProdutos.add(new Produto(id, nome, preco));
        }

        // Fecha o cursor e o banco de dados
        cursor.close();
        db.close();

        return listaProdutos;
    }

    public ArrayList BuscaListasCompras(Context context){
        ArrayList listaCompras = new ArrayList<>();

        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String query = "SELECT id,nome,data FROM lista";
        Cursor cursor = db.rawQuery(query, null);

        // Percorre os resultados da consulta
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));

            listaCompras.add(new ListaCompra(id, nome, data));
        }

        // Fecha o cursor e o banco de dados
        cursor.close();
        db.close();

        return listaCompras;
    }


    public void SalvarLista(Context context, String idLista, String data, String nomeLista, ArrayList<Produto> listaProdutos){

        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String query;
        if(idLista != null){
            query = "UPDATE lista SET nome='"+nomeLista+"', data='"+data+"' WHERE id='"+idLista+"'";
            db.execSQL(query);

        }else{
            idLista = UUID.randomUUID().toString();
            query = "INSERT INTO lista (id, nome, data) VALUES ('"+idLista+"','"+nomeLista+"','"+data+"')";
            db.execSQL(query);
        }

        query = "DELETE FROM lista_produtos WHERE id_lista ='"+idLista+"'";
        db.execSQL(query);

        for (Produto produto : listaProdutos) {
            query = "INSERT INTO lista_produtos (id, id_lista, id_produto) VALUES ('"+UUID.randomUUID()+"','"+idLista+"','"+produto.getId()+"')";
            db.execSQL(query);
        }

        db.close();
    }


    public void ApagarLista(Context context, String id){
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String query;

        query = "DELETE FROM lista_produtos WHERE id_lista ='"+id+"'";
        db.execSQL(query);

        query = "DELETE FROM lista WHERE id ='"+id+"'";
        db.execSQL(query);

        db.close();
    }
}
