package projetosistemasmoveis.rastreador.TelaExcluirDispositivo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.io.IOException;

import projetosistemasmoveis.rastreador.API.APIHttpClient;
import projetosistemasmoveis.rastreador.Conta.Conta;
import projetosistemasmoveis.rastreador.TelaLogin.Login;
import projetosistemasmoveis.rastreador.R;
import projetosistemasmoveis.rastreador.TelaDispositivos.Dispositivos;

public class Excluir extends AppCompatActivity {
    private String IdUsuario;
    private String IdUsuarioDispositivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir);
        GetIntent();
    }

    public void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("IdUsuario")) {
            IdUsuario = intent.getStringExtra("IdUsuario");

            if (intent.hasExtra("IdUsuarioDispositivo")) {
                IdUsuarioDispositivo = intent.getStringExtra("IdUsuarioDispositivo");
            } else {
                Voltar();
            }

        } else {
            Intent TelaLogin = new Intent(Excluir.this, Login.class);
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

                    Intent TelaConta = new Intent(Excluir.this, Conta.class);
                    TelaConta.putExtra("IdUsuario", IdUsuario);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(Excluir.this, Login.class);
                    TelaLogin.putExtra("Sair", "true");
                    startActivity(TelaLogin);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public  void BotaoSim(View v){
        ExecutaExcluir ExecutaExcluir = new ExecutaExcluir();
        //Chama Async Task
        ExecutaExcluir.execute(IdUsuarioDispositivo);
    }

    public  void BotaoNao(View v){
        Voltar();
    }

    public  void BotaoVoltar(View v){
        Voltar();
    }

    private void Voltar(){
        Intent TelaDispositivos = new Intent(Excluir.this, Dispositivos.class);
        TelaDispositivos.putExtra("IdUsuario", IdUsuario);
        startActivity(TelaDispositivos);
    }


    private class ExecutaExcluir extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected String doInBackground(String... params) {
            String resposta = "";
            APIHttpClient apiHttpClient = new APIHttpClient();
            try {
                String IdUsuarioDispositivo = params[0];
                String url = getString(R.string.serverUrl) + "/api/dispositivo/ApagarDispositivo/"+IdUsuarioDispositivo;

                resposta = apiHttpClient.Delete(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resposta;
        }

        @Override
        protected void onPostExecute(String resposta){
            Voltar();
        }
    }




}