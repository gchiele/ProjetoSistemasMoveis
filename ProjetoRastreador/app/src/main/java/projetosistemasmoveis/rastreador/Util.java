package projetosistemasmoveis.rastreador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import projetosistemasmoveis.rastreador.Objetos.ObjDadosLocalizacaoDispositivo;
import projetosistemasmoveis.rastreador.Objetos.ObjDispositivoConfiguracao;
import projetosistemasmoveis.rastreador.Objetos.ObjDispositivoStatus;

public class Util {

    public static boolean isValidGuid(String guid) {
        if (guid.equals("00000000-0000-0000-0000-000000000000")) {
            return false;
        }

        String regex = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
        return Pattern.matches(regex, guid);
    }

    public static ObjDispositivoStatus[] ConverteJsonObjStatus(String Json){
        try {
            // Converter o JSON em um JSONArray
            JSONArray jsonArray = new JSONArray(Json);

            // Criar um array de objetos da classe ObjDispositivoStatus
            ObjDispositivoStatus[] objetos = new ObjDispositivoStatus[jsonArray.length()];

            // Percorrer os objetos no JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extrair os dados do objeto JSON
                String idUsuarioDispositivo = jsonObject.getString("idUsuarioDispositivo");
                String nome = jsonObject.getString("nome");
                boolean online = jsonObject.getBoolean("online");
                String ultimoDadoRecebido = jsonObject.getString("ultimoDadoRecebido");

                // Criar um objeto da classe ObjDispositivoStatus e atribuir os dados extraídos
                ObjDispositivoStatus objDispositivoStatus = new ObjDispositivoStatus(idUsuarioDispositivo, nome, online, ultimoDadoRecebido);

                // Adicionar o objeto ao array
                objetos[i] = objDispositivoStatus;
            }

            // Retornar o array de objetos
            return objetos;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Retornar null em caso de erro ou JSON inválido
        return null;
    }

    public static ObjDispositivoConfiguracao ConverteJsonObjConfiguracao(String json) {
        try {
            // Converter o JSON em um objeto JSONObject
            JSONObject jsonObject = new JSONObject(json);

            // Extrair os dados do objeto JSON
            String nome = jsonObject.getString("nome");
            String codigo = jsonObject.getString("codigo");
            String idUsuarioDispositivo = jsonObject.getString("idUsuarioDispositivo");

            // Criar um objeto da classe ObjDispositivoStatus e atribuir os dados extraídos
            ObjDispositivoConfiguracao objDispositivoConfiguracao = new ObjDispositivoConfiguracao(idUsuarioDispositivo, nome, codigo);

            // Retornar o objeto
            return objDispositivoConfiguracao;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Retornar null em caso de erro ou JSON inválido
        return null;
    }

    public static ObjDadosLocalizacaoDispositivo[] ConverteJsonObjLocalizacao(String json) {
        try {
            // Converter o JSON em um JSONArray
            JSONArray jsonArray = new JSONArray(json);

            // Criar um array de objetos da classe ObjDispositivoStatus
            ObjDadosLocalizacaoDispositivo[] objetos = new ObjDadosLocalizacaoDispositivo[jsonArray.length()];

            // Percorrer os objetos no JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extrair os dados do objeto JSON
                String dataHora = jsonObject.getString("dataHora");
                double latitude = jsonObject.getDouble("latitude");
                double longitude = jsonObject.getDouble("longitude");
                int altitude = jsonObject.getInt("altitude");
                int satelites = jsonObject.getInt("satelites");
                int sinalOperadora = jsonObject.getInt("sinalOperadora");
                boolean saida = jsonObject.getBoolean("saida");

                // Criar um objeto da classe ObjDispositivoStatus e atribuir os dados extraídos
                ObjDadosLocalizacaoDispositivo objDadosLocalizacaoDispositivo = new ObjDadosLocalizacaoDispositivo(dataHora,latitude,longitude,altitude,satelites,sinalOperadora,saida);

                // Adicionar o objeto ao array
                objetos[i] = objDadosLocalizacaoDispositivo;
            }

            // Retornar o array de objetos
            return objetos;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Retornar null em caso de erro ou JSON inválido
        return null;
    }

    public void MsgErro(Context context, String msg){
        // Crie uma instância do AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Defina o título e a mensagem do diálogo
        builder.setTitle("Erro");
        builder.setMessage(msg);

        // Adicione o botão "OK" e defina seu listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Código para lidar com o clique no botão "OK" (opcional)
                dialog.dismiss(); // Fecha o diálogo
            }
        });

        // Crie o AlertDialog e exiba-o
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
