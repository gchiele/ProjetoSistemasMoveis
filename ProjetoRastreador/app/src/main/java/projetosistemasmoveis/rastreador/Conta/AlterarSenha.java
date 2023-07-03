package projetosistemasmoveis.rastreador.Conta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.io.IOException;

import projetosistemasmoveis.rastreador.API.APIHttpClient;
import projetosistemasmoveis.rastreador.Objetos.ObjUsuario;
import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.TelaLogin.Login;
import projetosistemasmoveis.rastreador.Util;

public class AlterarSenha extends AppCompatActivity {
    private String IdUsuario;

    private TextInputEditText CampoSenhaAtual;
    private TextInputEditText CampoNovaSenha;
    private TextInputEditText CampoConfirmaNovaSenha;
    private ObjUsuario objUsuario;
    private Util util;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);
        GetIntent();

        CampoSenhaAtual = findViewById(R.id.CampoSenhaAtual);
        CampoNovaSenha = findViewById(R.id.CampoNovaSenha);
        CampoConfirmaNovaSenha = findViewById(R.id.CampoConfirmaNovaSenha);

        util = new Util();

        BuscaDados();
    }

    public void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("IdUsuario")) {
            IdUsuario = intent.getStringExtra("IdUsuario");
        } else {
            Intent TelaLogin = new Intent(AlterarSenha.this, Login.class);
            startActivity(TelaLogin);
        }
    }

    public void BuscaDados(){
        ExecutaBuscaDadosUsuario executaBuscaDadosUsuario = new ExecutaBuscaDadosUsuario();
        //Chama Async Task
        executaBuscaDadosUsuario.execute(IdUsuario);
    }
    public void BotaoConta(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.conta_opcoes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.opcao1) {

                    Intent TelaConta = new Intent(AlterarSenha.this, Conta.class);
                    TelaConta.putExtra("IdUsuario", IdUsuario);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(AlterarSenha.this, Login.class);
                    TelaLogin.putExtra("Sair", "true");
                    startActivity(TelaLogin);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public  void BotaoVoltar(View v){
        Voltar();
    }

    public void BotaoSalvarSenha(View v){
        if(objUsuario.getSenha().equals(CampoSenhaAtual.getText().toString())){
            if(CampoNovaSenha.getText().toString().equals(CampoConfirmaNovaSenha.getText().toString())){
                objUsuario.setSenha(CampoNovaSenha.getText().toString());

                ExecutaSalvaDadosUsuario executaSalvaDadosUsuario = new ExecutaSalvaDadosUsuario();
                executaSalvaDadosUsuario.execute(objUsuario);
            }else{
                util.MsgErro(AlterarSenha.this,"Nova senha esta Diferente de Confirma Nova Senha!");
            }
        }else{
            util.MsgErro(AlterarSenha.this,"Senha Atual não Corresponde!");
        }
    }

    private class ExecutaBuscaDadosUsuario extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(AlterarSenha.this,
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
            } catch (JSONException e) {
                e.printStackTrace();
                util.MsgErro(AlterarSenha.this,"Erro ao Carregar os Dados do Usuario!");
            }
        }
    }

    private class ExecutaSalvaDadosUsuario extends AsyncTask<ObjUsuario, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(AlterarSenha.this,
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
                util.MsgErro(AlterarSenha.this,"Erro ao Salvar o Usuario!");
                BuscaDados();
            }
        }
    }



    private void Voltar(){
        Intent TelaConta = new Intent(this, Conta.class);
        TelaConta.putExtra("IdUsuario", IdUsuario);
        startActivity(TelaConta);
    }




}