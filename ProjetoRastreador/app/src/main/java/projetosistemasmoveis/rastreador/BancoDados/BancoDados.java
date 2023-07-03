package projetosistemasmoveis.rastreador.BancoDados;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class BancoDados {

    private DatabaseHelper databaseHelper;

    public String BuscaUsuario(Context context){
        String idUsuario = "";

        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String query = "SELECT id FROM usuario";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            idUsuario = cursor.getString(cursor.getColumnIndexOrThrow("id"));
        }

        // Fecha o cursor e o banco de dados
        cursor.close();
        db.close();

        return idUsuario;
    }

    public void SalvarUsuario(Context context, String idUsuario){

        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ApagarUsuario(context);

        String query;

        query = "INSERT INTO usuario (id) VALUES ('"+idUsuario+"')";
        db.execSQL(query);
        db.close();
    }


    public void ApagarUsuario(Context context){
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String query;

        query = "DELETE FROM usuario";
        db.execSQL(query);

        db.close();
    }
}
