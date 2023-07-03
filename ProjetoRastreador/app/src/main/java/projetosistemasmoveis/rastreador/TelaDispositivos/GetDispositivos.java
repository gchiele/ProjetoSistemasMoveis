package projetosistemasmoveis.rastreador.TelaDispositivos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import projetosistemasmoveis.rastreador.API.APIHttpClient;

import projetosistemasmoveis.rastreador.Objetos.ObjDispositivoStatus;
import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.Util;

public class GetDispositivos extends AsyncTask<String, Void, ObjDispositivoStatus[]> {
    private GetDispositivosCallback callback;

    private Context context;
    public GetDispositivos(GetDispositivosCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected ObjDispositivoStatus[] doInBackground(String... params) {
        ObjDispositivoStatus[] objetos = null;

        APIHttpClient apiHttpClient = new APIHttpClient();
        try {
            String IdUsuario = params[0];
            Util util = new Util();
            String url = context.getString(R.string.serverUrl) + "/api/dispositivo/ListaStatusDispositivo/"+IdUsuario;

            String resposta = apiHttpClient.Get(url);
            Log.i("Teste", "Resposta = " + resposta);

            // Converter a resposta em objetos MeuObjeto[]
            objetos = util.ConverteJsonObjStatus(resposta);



        } catch (IOException e) {
            Log.i("Teste", "Erro = " + e.toString());
            e.printStackTrace();
        }
        return objetos;
    }

    @Override
    protected void onPostExecute(ObjDispositivoStatus[] objetos){
        if (callback != null) {
            if (objetos != null) {
                callback.onDispositivosLoaded(objetos);
            } else {
                callback.onError();
            }
        }
    }
}