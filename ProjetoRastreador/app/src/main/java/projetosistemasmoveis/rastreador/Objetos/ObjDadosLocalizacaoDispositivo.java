package projetosistemasmoveis.rastreador.Objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ObjDadosLocalizacaoDispositivo {
    private String dataHora;
    private double latitude;
    private double longitude;
    private int altitude;
    private int satelites;
    private int sinalOperadora;
    private boolean saida;

    public ObjDadosLocalizacaoDispositivo(String dataHora, double latitude, double longitude, int altitude, int satelites, int sinalOperadora, boolean saida) {
        this.dataHora = dataHora;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.satelites = satelites;
        this.sinalOperadora = sinalOperadora;
        this.saida = saida;
    }

    public String getDataHora() {
        return dataHora;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public int getSatelites() {
        return satelites;
    }

    public int getSinalOperadora() {
        return sinalOperadora;
    }

    public boolean isSaida() {
        return saida;
    }

    public static ObjDadosLocalizacaoDispositivo fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        String dataHora = jsonObject.getString("dataHora");
        double latitude = jsonObject.getDouble("latitude");
        double longitude = jsonObject.getDouble("longitude");
        int altitude = jsonObject.getInt("altitude");
        int satelites = jsonObject.getInt("satelites");
        int sinalOperadora = jsonObject.getInt("sinalOperadora");
        boolean saida = jsonObject.getBoolean("saida");

        return new ObjDadosLocalizacaoDispositivo(dataHora, latitude, longitude, altitude, satelites, sinalOperadora, saida);
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

                ObjDadosLocalizacaoDispositivo objDadosLocalizacaoDispositivo = ObjDadosLocalizacaoDispositivo.fromJson(jsonObject.toString());

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
}
