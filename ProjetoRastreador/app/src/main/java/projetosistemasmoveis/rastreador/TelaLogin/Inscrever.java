package projetosistemasmoveis.rastreador.TelaLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import projetosistemasmoveis.rastreador.API.APIHttpClient;
import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.Util;

public class Inscrever extends AppCompatActivity {

    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrever);
    }

    public  void BotaoVoltar(View v){

        Intent TelaLogin = new Intent(this, Login.class);
        startActivity(TelaLogin);

    }

    public  void BotaoCriarConta(View v){

        TextInputEditText CampoNome = (TextInputEditText) findViewById(R.id.CampoNome);
        TextInputEditText CampoEmail = (TextInputEditText) findViewById(R.id.CampoEmail);
        TextInputEditText CampoFone = (TextInputEditText) findViewById(R.id.CampoFone);
        TextInputEditText CampoSenha = (TextInputEditText) findViewById(R.id.CampoSenha);
        TextInputEditText CampoConfirmaSenha = (TextInputEditText) findViewById(R.id.CampoConfirmaSenha);

        String Nome = CampoNome.getText().toString();
        String Email = CampoEmail.getText().toString();
        String Fone = CampoFone.getText().toString();
        String Senha = CampoSenha.getText().toString();
        String ConfirmaSenha = CampoConfirmaSenha.getText().toString();

        if(Senha.equals(ConfirmaSenha)){
            ExecutaInscreverse executaInscreverse = new ExecutaInscreverse();
            //Chama Async Task
            executaInscreverse.execute(Nome,Email,Fone,Senha,ConfirmaSenha);
        }else{
            Intent TelaErroInscreverse = new Intent(Inscrever.this, ErroInscreverse.class);
            startActivity(TelaErroInscreverse);
        }

    }

    private class ExecutaInscreverse extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(Inscrever.this,
                    "Por favor Aguarde ...", "Realizando Inscrição...");
        }

        @Override
        protected String doInBackground(String... params) {
            String resposta = "";
            APIHttpClient apiHttpClient = new APIHttpClient();
            try {
                String url = getString(R.string.serverUrl) + "/api/Usuario/NovoUsuario";
                String Nome = params[0];
                String Email = params[1];
                String Fone = params[2];
                String Senha = params[3];
                String ConfirmaSenha = params[4];

                String jsonInputString = "{\"Nome\":\"" + Nome + "\"," +
                        "\"Email\":\"" + Email + "\"," +
                        "\"Fone\":\"" + Fone + "\"," +
                        "\"Senha\":\"" + Senha + "\"," +
                        "\"ConfirmaSenha\":\"" + ConfirmaSenha + "\"}";

                resposta = apiHttpClient.Post(url, jsonInputString);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return resposta;
        }

        @Override
        protected void onPostExecute(String guid){
            guid = guid.replace("\"", "");

            Util util = new Util();

            if (util.isValidGuid(guid)) {
                Intent TelaLogin = new Intent(Inscrever.this, Login.class);
                startActivity(TelaLogin);
            } else {
                Intent TelaErroInscreverse = new Intent(Inscrever.this, ErroInscreverse.class);
                startActivity(TelaErroInscreverse);
            }
            load.dismiss();
        }
    }

}