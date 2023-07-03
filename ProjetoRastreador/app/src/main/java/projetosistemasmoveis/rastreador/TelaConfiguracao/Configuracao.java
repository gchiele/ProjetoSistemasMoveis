package projetosistemasmoveis.rastreador.TelaConfiguracao;

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
import projetosistemasmoveis.rastreador.Objetos.ObjDispositivoConfiguracao;
import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.TelaDispositivos.Dispositivos;
import projetosistemasmoveis.rastreador.Util;

public class Configuracao extends AppCompatActivity {
    private String IdUsuario;
    private String IdUsuarioDispositivo;
    private TextInputEditText CampoNome;
    private TextInputEditText CampoCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        GetIntent();

        CampoNome = (TextInputEditText) findViewById(R.id.TextCampoNome);
        CampoCodigo = (TextInputEditText) findViewById(R.id.TextCampoCodigo);

        ExecutaBuscaDados executaBuscaDados = new ExecutaBuscaDados();
        //Chama Async Task
        executaBuscaDados.execute(IdUsuarioDispositivo);
    }

    public void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("IdUsuario")) {
            IdUsuario = intent.getStringExtra("IdUsuario");
        } else {
            Intent TelaLogin = new Intent(Configuracao.this, Login.class);
            startActivity(TelaLogin);
        }

        if (intent.hasExtra("IdUsuarioDispositivo")) {
            IdUsuarioDispositivo = intent.getStringExtra("IdUsuarioDispositivo");
        } else {
            Voltar();
        }
    }


    public void BotaoConta(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.conta_opcoes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.opcao1) {

                    Intent TelaConta = new Intent(Configuracao.this, Conta.class);
                    TelaConta.putExtra("IdUsuario", IdUsuario);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(Configuracao.this, Login.class);
                    TelaLogin.putExtra("Sair", "true");
                    startActivity(TelaLogin);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public  void BotaoSalvarDispositivo(View v) {

        String Nome = CampoNome.getText().toString();
        String Codigo = CampoCodigo.getText().toString();

        ObjDispositivoConfiguracao objDispositivoConfiguracao = new ObjDispositivoConfiguracao(IdUsuarioDispositivo, Nome, Codigo);

        ExecutaSalvaDados executaSalvaDados = new ExecutaSalvaDados();
        //Chama Async Task
        executaSalvaDados.execute(objDispositivoConfiguracao);
    }

    public  void BotaoVoltar(View v){
        Voltar();
    }

    private void Voltar(){
        Intent TelaDispositivo = new Intent(this, Dispositivos.class);
        TelaDispositivo.putExtra("IdUsuario", IdUsuario);
        startActivity(TelaDispositivo);
    }

    private class ExecutaBuscaDados extends AsyncTask<String, Void, ObjDispositivoConfiguracao> {

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected ObjDispositivoConfiguracao doInBackground(String... params) {
            String resposta = "";
            ObjDispositivoConfiguracao objDispositivoConfiguracao = null;
            APIHttpClient apiHttpClient = new APIHttpClient();
            Util util = new Util();
            try {
                String IdUsuarioDispositivo = params[0];
                String url = getString(R.string.serverUrl) + "/api/dispositivo/DadosDispositivoEditar/"+ IdUsuarioDispositivo;

                resposta = apiHttpClient.Get(url);

                objDispositivoConfiguracao = util.ConverteJsonObjConfiguracao(resposta);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return objDispositivoConfiguracao;
        }

        @Override
        protected void onPostExecute(ObjDispositivoConfiguracao objeto){
            CampoNome.setText(objeto.getNome());
            CampoCodigo.setText(objeto.getCodigo());
        }
    }


    private class ExecutaSalvaDados extends AsyncTask<ObjDispositivoConfiguracao, Void, String> {

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected String doInBackground(ObjDispositivoConfiguracao... params) {
            String resposta = "";

            APIHttpClient apiHttpClient = new APIHttpClient();
            Util util = new Util();
            try {
                ObjDispositivoConfiguracao objDispositivoConfiguracao = params[0];
                String url = getString(R.string.serverUrl) + "/api/dispositivo/SalvarDispositivo";

                String jsonInputString = "{\"Nome\":\"" + objDispositivoConfiguracao.getNome() + "\",\"Codigo\":\"" + objDispositivoConfiguracao.getCodigo()
                        + "\",\"IdUsuarioDispositivo\":\"" + objDispositivoConfiguracao.getIdUsuarioDispositivo() + "\"}";

                resposta = apiHttpClient.Put(url, jsonInputString);

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
               Voltar();
            } else {
                // Erro Alterar
                Voltar();
            }
        }
    }

}