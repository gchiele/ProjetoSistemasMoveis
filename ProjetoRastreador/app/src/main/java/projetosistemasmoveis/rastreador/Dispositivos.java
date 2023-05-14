package projetosistemasmoveis.rastreador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

public class Dispositivos extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> Nomes = new ArrayList<>();
    private List<String> Status = new ArrayList<>();
    private DispositivoCardViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);

        mRecyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        Nomes.add("Dispositivo 1");
        Nomes.add("Rastreador Carro");

        Status.add("Online");
        Status.add("Offline");

        // Criar e setar o adaptador
        mAdapter = new DispositivoCardViewAdapter(Nomes, Status);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void BotaoConta(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.conta_opcoes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.opcao1) {

                    Intent TelaConta = new Intent(Dispositivos.this, Conta.class);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(Dispositivos.this, Login.class);
                    startActivity(TelaLogin);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }
    public void BotaoSelecionadoDispositivo(View view) {

        Intent TelaMapa = new Intent(this, Mapa.class);
        startActivity(TelaMapa);

    }
    public void BotaoInfo(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.dispositivo_opcoes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.opcao1) {

                    Intent TelaExcluir = new Intent(Dispositivos.this, Excluir.class);
                    startActivity(TelaExcluir);

                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaConfiguracao = new Intent(Dispositivos.this, Configuracao.class);
                    startActivity(TelaConfiguracao);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }


    public void BotaoAdd(View v){

        //adicionarDispositivo("Teste1","Online");

        Intent TelaAddDispositivo = new Intent(this, AdicionarDispositivo.class);
        startActivity(TelaAddDispositivo);
    }

    public void adicionarDispositivo(String nome, String status) {
        Nomes.add(nome);
        Status.add(status);
        mAdapter.notifyItemInserted(Nomes.size() - 1);
    }



}