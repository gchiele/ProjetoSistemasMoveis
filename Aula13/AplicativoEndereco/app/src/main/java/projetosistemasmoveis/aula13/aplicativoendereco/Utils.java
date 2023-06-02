package projetosistemasmoveis.aula13.aplicativoendereco;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public PessoaObj getInformacao(String end){
        String json;
        PessoaObj retorno;
        json = NetworkUtils.getJSONFromAPI(end);
        Log.i("Resultado", json);
        retorno = parseJson(json);

        return retorno;
    }

    private PessoaObj parseJson(String json){
        try {
            PessoaObj pessoa = new PessoaObj();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray resultados = jsonObj.getJSONArray("results");

            JSONObject objArray = resultados.getJSONObject(0);

            JSONObject obj = objArray.getJSONObject("name");

            pessoa.setNome(obj.getString("first"));

            JSONObject localizacao = objArray.getJSONObject("location");

            JSONObject coordenadas = localizacao.getJSONObject("coordinates");

            pessoa.setLatitude(coordenadas.getDouble("latitude"));
            pessoa.setLongitude(coordenadas.getDouble("longitude"));

            return pessoa;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
