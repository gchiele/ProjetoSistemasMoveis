package projetosistemasmoveis.rastreador.TelaAdicionarDispositivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import projetosistemasmoveis.rastreador.API.APIHttpClient;
import projetosistemasmoveis.rastreador.Conta.Conta;
import projetosistemasmoveis.rastreador.TelaLogin.Login;
import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.TelaDispositivos.Dispositivos;
import projetosistemasmoveis.rastreador.Util;

public class AdicionarDispositivo extends AppCompatActivity {

    private String IdUsuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_dispositivo);
        GetIntent();
    }

    public void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("IdUsuario")) {
            IdUsuario = intent.getStringExtra("IdUsuario");
        } else {
            Intent TelaLogin = new Intent(AdicionarDispositivo.this, Login.class);
            startActivity(TelaLogin);
        }
    }


    public void BotaoConta(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.conta_opcoes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.opcao1) {

                    Intent TelaConta = new Intent(AdicionarDispositivo.this, Conta.class);
                    TelaConta.putExtra("IdUsuario", IdUsuario);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(AdicionarDispositivo.this, Login.class);
                    TelaLogin.putExtra("Sair", "true");
                    startActivity(TelaLogin);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public void BotaoVoltar(View v){
        Voltar();
    }

    public void BotaoSalvar(View v){
        TextInputEditText CampoNome = (TextInputEditText) findViewById(R.id.TextCampoNome);
        TextInputEditText CampoCodigo = (TextInputEditText) findViewById(R.id.TextCampoCodigo);

        String Nome = CampoNome.getText().toString();
        String Codigo = CampoCodigo.getText().toString();

        ExecutaAdicaoDispositivo executaAdicaoDispositivo = new ExecutaAdicaoDispositivo();
        //Chama Async Task
        executaAdicaoDispositivo.execute(IdUsuario,Nome,Codigo);
    }

    private void Voltar(){
        Intent TelaDispositivos = new Intent(AdicionarDispositivo.this, Dispositivos.class);
        TelaDispositivos.putExtra("IdUsuario", IdUsuario);
        startActivity(TelaDispositivos);
    }


    private class ExecutaAdicaoDispositivo extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected String doInBackground(String... params) {
            String resposta = "";
            APIHttpClient apiHttpClient = new APIHttpClient();
            try {
                String url = getString(R.string.serverUrl) + "/api/Dispositivo/AdicionarDispositivo";
                String IdUsuario = params[0];
                String Nome = params[1];
                String Codigo = params[2];
                String jsonInputString = "{\"IdUsuario\":\"" + IdUsuario + "\",\"Nome\":\"" + Nome + "\",\"Codigo\":\"" + Codigo + "\"}";

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
                Intent TelaSucessoAdicionarDispositivo = new Intent(AdicionarDispositivo.this, SucessoAdicionarDispositivo.class);
                TelaSucessoAdicionarDispositivo.putExtra("IdUsuario", IdUsuario);
                startActivity(TelaSucessoAdicionarDispositivo);
            } else {
                Intent TelaErroAdicionarDispositivo = new Intent(AdicionarDispositivo.this, ErroAdicionarDispositivo.class);
                TelaErroAdicionarDispositivo.putExtra("IdUsuario", IdUsuario);
                startActivity(TelaErroAdicionarDispositivo);
            }
        }
    }




}