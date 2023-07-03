package projetosistemasmoveis.rastreador.TelaLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import projetosistemasmoveis.rastreador.API.APIHttpClient;
import projetosistemasmoveis.rastreador.BancoDados.BancoDados;
import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.TelaDispositivos.Dispositivos;
import projetosistemasmoveis.rastreador.Util;

public class Login extends AppCompatActivity {

    TextInputEditText CampoEmail;
    TextInputEditText CampoSenha;
    CheckBox CheckBoxManterConectado;
    BancoDados db;
    Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CampoEmail = (TextInputEditText) findViewById(R.id.TextCampoEmail);
        CampoSenha = (TextInputEditText) findViewById(R.id.TextCampoSenha);
        CheckBoxManterConectado = findViewById(R.id.checkBoxManterConectado);

        db = new BancoDados();
        util = new Util();

        Intent intent = getIntent();
        if (intent.hasExtra("Sair")) {
            db.ApagarUsuario(Login.this);
        }

        String IdUsuario = db.BuscaUsuario(Login.this);
        if(util.isValidGuid(IdUsuario)){
            Acessar(IdUsuario);
        }
    }


    public  void BotaoLogin (View v){

        String Email = CampoEmail.getText().toString();
        String Senha = CampoSenha.getText().toString();

        ExecutaLogin executaLogin = new ExecutaLogin();
        //Chama Async Task
        executaLogin.execute(Email,Senha);
    }

    public  void BotaoEsqueceuSenha (View v){
        Intent TelaEsqueceuSenha = new Intent(this, EsqueceuSenha.class);
        startActivity(TelaEsqueceuSenha);
    }

    public  void BotaoInscrever (View v){
        Intent TelaInscrever = new Intent(this, Inscrever.class);
        startActivity(TelaInscrever);
    }

    public void Acessar(String IdUsuario){
        Intent TelaDispostivos = new Intent(Login.this, Dispositivos.class);
        TelaDispostivos.putExtra("IdUsuario", IdUsuario);
        startActivity(TelaDispostivos);
    }



    private class ExecutaLogin extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... params) {
            String resposta = "";
            APIHttpClient apiHttpClient = new APIHttpClient();
            try {
                String url = getString(R.string.serverUrl) + "/api/usuario/Login";
                String email = params[0]; // Primeiro parâmetro é o email
                String senha = params[1]; // Segundo parâmetro é a senha
                String jsonInputString = "{\"Email\":\"" + email + "\",\"Senha\":\"" + senha + "\"}";
                Log.i("Teste", "url -"+ url);
                resposta = apiHttpClient.Put(url, jsonInputString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resposta;
        }

        @Override
        protected void onPostExecute(String guid){
            guid = guid.replace("\"", "");

            if (util.isValidGuid(guid)) {

                if(CheckBoxManterConectado.isChecked()){
                    db.SalvarUsuario(Login.this,guid);
                }else{
                    db.ApagarUsuario(Login.this);
                }
                Acessar(guid);

            } else {
                Intent TelaErroLogin = new Intent(Login.this, ErroLogin.class);
                startActivity(TelaErroLogin);
            }
        }
    }


}