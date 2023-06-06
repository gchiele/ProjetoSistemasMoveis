package projetosistemasmoveis.aula14.aplicativolistacompras;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "aplicativo_compras.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crie a tabela do banco de dados, se necessário
        String createTableQuery;

        // Cria Tabela Lista
        createTableQuery = "CREATE TABLE IF NOT EXISTS lista ("+
                "id UUID PRIMARY KEY,"+
                "nome TEXT,"+
                "data TEXT)";
        db.execSQL(createTableQuery);

        // Cria Tabela Produto
        createTableQuery = "CREATE TABLE IF NOT EXISTS produto ("+
                "id UUID PRIMARY KEY,"+
                "nome TEXT,"+
                "preco REAL)";
        db.execSQL(createTableQuery);

        // Cria Tabela Lista Produto
        createTableQuery = "CREATE TABLE IF NOT EXISTS lista_produtos ("+
                "id UUID PRIMARY KEY,"+
                "id_lista UUID,"+
                "id_produto UUID,"+
                "FOREIGN KEY (id_lista) REFERENCES lista(id),"+
                "FOREIGN KEY (id_produto) REFERENCES produto(id)"+
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualizações do banco de dados, se necessário
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS lista_produtos");
            onCreate(db);

            db.execSQL("DROP TABLE IF EXISTS produto");
            onCreate(db);

            db.execSQL("DROP TABLE IF EXISTS lista");
            onCreate(db);
        }
    }
}