package projetosistemasmoveis.rastreador.Conta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.io.IOException;

import projetosistemasmoveis.rastreador.API.APIHttpClient;
import projetosistemasmoveis.rastreador.Objetos.ObjDadosLocalizacaoDispositivo;
import projetosistemasmoveis.rastreador.Objetos.ObjUsuario;
import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.TelaDispositivos.Dispositivos;
import projetosistemasmoveis.rastreador.TelaHistorico.Historico;
import projetosistemasmoveis.rastreador.TelaLogin.ErroLogin;
import projetosistemasmoveis.rastreador.TelaLogin.Login;
import projetosistemasmoveis.rastreador.Util;

public class Conta extends AppCompatActivity {

    private String IdUsuario;
    private TextInputEditText CampoNome;
    private TextInputEditText CampoEmail;
    private TextInputEditText CampoFone;
    private ObjUsuario objUsuario;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        GetIntent();

        CampoNome = findViewById(R.id.CampoNome);
        CampoEmail = findViewById(R.id.CampoEmail);
        CampoFone = findViewById(R.id.CampoFone);

        util = new Util();

        ExecutaBuscaDadosUsuario executaBuscaDadosUsuario = new ExecutaBuscaDadosUsuario();
        //Chama Async Task
        executaBuscaDadosUsuario.execute(IdUsuario);
    }

    public void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("IdUsuario")) {
            IdUsuario = intent.getStringExtra("IdUsuario");
        } else {
            Intent TelaLogin = new Intent(Conta.this, Login.class);
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

                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(Conta.this, Login.class);
                    TelaLogin.putExtra("Sair", "true");
                    startActivity(TelaLogin);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public  void BotaoAlterarSenha(View v) {
        Intent TelaAlterarSenha = new Intent(this, AlterarSenha.class);
        TelaAlterarSenha.putExtra("IdUsuario", IdUsuario);
        startActivity(TelaAlterarSenha);
    }

    public  void BotaoSalvarConta(View v) {
        objUsuario.setEmail(CampoEmail.getText().toString());
        objUsuario.setNome(CampoNome.getText().toString());
        objUsuario.setFone(CampoFone.getText().toString());

        ExecutaSalvaDadosUsuario executaSalvaDadosUsuario = new ExecutaSalvaDadosUsuario();
        executaSalvaDadosUsuario.execute(objUsuario);
    }

    private class ExecutaBuscaDadosUsuario extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Conta.this,
                    "Por favor Aguarde ...", "Buscando Dados...");
        }

        @Override
        protected String doInBackground(String... params) {
            String resposta = "";
            APIHttpClient apiHttpClient = new APIHttpClient();
            try {
                String IdUsuario = params[0];
                String url = getString(R.string.serverUrl) + "/api/usuario/"+ IdUsuario;

                resposta = apiHttpClient.Get(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resposta;
        }

        @Override
        protected void onPostExecute(String resposta){
            progressDialog.dismiss();

            try {
                objUsuario = ObjUsuario.fromJson(resposta);
                AtualizaCampos();
            } catch (JSONException e) {
                e.printStackTrace();
                util.MsgErro(Conta.this,"Erro ao Carregar os Dados do Usuario!");
            }
        }
    }

    private class ExecutaSalvaDadosUsuario extends AsyncTask<ObjUsuario, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Conta.this,
                    "Por favor Aguarde ...", "Salvando Dados...");
        }

        @Override
        protected String doInBackground(ObjUsuario... params) {
            String resposta = "";
            APIHttpClient apiHttpClient = new APIHttpClient();
            try {
                ObjUsuario usuario = params[0];
                String url = getString(R.string.serverUrl) + "/api/usuario/SalvarConta";
                String jsonInputString = "{\"id\":\"" + usuario.getId() + "\"," +
                        "\"nome\":\"" + usuario.getNome() + "\"," +
                        "\"fone\":\"" + usuario.getFone() + "\"," +
                        "\"email\":\"" + usuario.getEmail() + "\"," +
                        "\"senhaAtual\":\"" + usuario.getSenha() + "\"," +
                        "\"novaSenha\":\"" + usuario.getSenha() + "\"," +
                        "\"confirmaNovaSenha\":\"" + usuario.getSenha() + "\"}";

                resposta = apiHttpClient.Put(url,jsonInputString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resposta;
        }

        @Override
        protected void onPostExecute(String guid){
            progressDialog.dismiss();

            guid = guid.replace("\"", "");

            if (util.isValidGuid(guid)) {
                Voltar();
            } else {
                util.MsgErro(Conta.this,"Erro ao Salvar o Usuario!");
            }
        }
    }





    public void AtualizaCampos(){
        CampoNome.setText(objUsuario.getNome());
        CampoEmail.setText(objUsuario.getEmail());
        CampoFone.setText(objUsuario.getFone());
    }



    public  void BotaoVoltar(View v){
        Voltar();
    }

    private void Voltar(){
        Intent TelaDispositivo = new Intent(this, Dispositivos.class);
        TelaDispositivo.putExtra("IdUsuario", IdUsuario);
        startActivity(TelaDispositivo);
    }


}