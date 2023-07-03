package projetosistemasmoveis.rastreador.BancoDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_rastreador.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crie a tabela do banco de dados, se necessário
        String createTableQuery;

        // Cria Tabela Lista
        createTableQuery = "CREATE TABLE IF NOT EXISTS usuario ("+
                "id UUID PRIMARY KEY)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualizações do banco de dados, se necessário
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS usuario");
            onCreate(db);
        }
    }
}