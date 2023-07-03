package projetosistemasmoveis.rastreador.API;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APIHttpClient {


    public static String Put(String url, String jsonInputString) throws IOException {
        String retorno = "";
        int codigoResposta;
        InputStream is;

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection conexao = (HttpURLConnection) apiUrl.openConnection();
            conexao.setRequestMethod("PUT");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conexao.setDoOutput(true);

            // Escreve os dados no corpo da requisição
            try (OutputStream outputStream = new BufferedOutputStream(conexao.getOutputStream())) {
                byte[] inputData = jsonInputString.getBytes(StandardCharsets.UTF_8);
                outputStream.write(inputData);
            }catch (Exception e) {
                e.printStackTrace();
                Log.i("Put", "Erro - " + e.toString());
            }

            // Lê a resposta da requisição
            codigoResposta = conexao.getResponseCode();
            Log.i("Put", "Cod = " + codigoResposta);

            if(codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                is = conexao.getInputStream();
            }else{
                is = conexao.getErrorStream();
            }

            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();
            Log.i("Put", "Resposta = " + retorno.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return retorno;
    }
    public static String Post(String url, String jsonInputString) throws IOException {
        String retorno = "";
        int codigoResposta;
        InputStream is;

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection conexao = (HttpURLConnection) apiUrl.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conexao.setDoOutput(true);

            // Escreve os dados no corpo da requisição
            try (OutputStream outputStream = new BufferedOutputStream(conexao.getOutputStream())) {
                byte[] inputData = jsonInputString.getBytes(StandardCharsets.UTF_8);
                outputStream.write(inputData);
            }catch (Exception e) {
                e.printStackTrace();
                Log.i("Post", "Erro - " + e.toString());
            }

            // Lê a resposta da requisição
            codigoResposta = conexao.getResponseCode();
            Log.i("Post", "Cod = " + codigoResposta);

            if(codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                is = conexao.getInputStream();
            }else{
                is = conexao.getErrorStream();
            }

            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();
            Log.i("Post", "Resposta = " + retorno.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return retorno;
    }
    public static String Get(String url) throws IOException {
        String retorno = "";
        int codigoResposta;
        InputStream is;

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection conexao = (HttpURLConnection) apiUrl.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);

            // Lê a resposta da requisição
            codigoResposta = conexao.getResponseCode();
            Log.i("Get", "Cod = " + codigoResposta);

            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conexao.getInputStream();
            } else {
                is = conexao.getErrorStream();
            }

            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();
            Log.i("Get", "Resposta = " + retorno.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }
    public static String Delete(String url) throws IOException {
        String retorno = "";
        int codigoResposta;
        InputStream is;

        try {
            URL apiUrl = new URL(url);
            HttpURLConnection conexao = (HttpURLConnection) apiUrl.openConnection();
            conexao.setRequestMethod("DELETE");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);

            // Lê a resposta da requisição
            codigoResposta = conexao.getResponseCode();
            Log.i("Delete", "Cod = " + codigoResposta);

            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conexao.getInputStream();
            } else {
                is = conexao.getErrorStream();
            }

            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();
            Log.i("Delete", "Resposta = " + retorno.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }


    private static String converterInputStreamToString(InputStream is){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while((linha = br.readLine())!=null){
                buffer.append(linha);
            }

            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return buffer.toString();
    }

}
