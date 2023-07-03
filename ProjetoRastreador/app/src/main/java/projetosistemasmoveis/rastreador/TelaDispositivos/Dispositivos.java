package projetosistemasmoveis.rastreador.TelaDispositivos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import projetosistemasmoveis.rastreador.TelaAdicionarDispositivo.AdicionarDispositivo;
import projetosistemasmoveis.rastreador.TelaConfiguracao.Configuracao;
import projetosistemasmoveis.rastreador.Conta.Conta;
import projetosistemasmoveis.rastreador.TelaExcluirDispositivo.Excluir;
import projetosistemasmoveis.rastreador.TelaLogin.Login;
import projetosistemasmoveis.rastreador.TelaMapaAtual.Mapa;
import projetosistemasmoveis.rastreador.Objetos.ObjDispositivoStatus;
import projetosistemasmoveis.rastreador.R;

public class Dispositivos extends AppCompatActivity implements GetDispositivosCallback, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ObjDispositivoStatus[] dispositivos;
    private DispositivoCardViewAdapter mAdapter;
    private String IdUsuario = "";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);

        GetIntent();

        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        carregarDispositivos();
    }

    @Override
    public void onRefresh() {
        carregarDispositivos();
    }

    public void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("IdUsuario")) {
            IdUsuario = intent.getStringExtra("IdUsuario");
        } else {
            Intent TelaLogin = new Intent(Dispositivos.this, Login.class);
            startActivity(TelaLogin);
        }
    }

    private void carregarDispositivos() {
        // Criar uma instância do callback
        GetDispositivosCallback callback = this;

        // Criar uma instância da tarefa GetDispositivos e passar o callback no construtor
        GetDispositivos getDispositivosTask = new GetDispositivos(callback, Dispositivos.this);

        // Executar a tarefa passando os parâmetros necessários
        getDispositivosTask.execute(IdUsuario);
    }

    // Implementar os métodos da interface GetDispositivosCallback
    @Override
    public void onDispositivosLoaded(ObjDispositivoStatus[] objetos) {
        swipeRefreshLayout.setRefreshing(false);

        // Tratar o array de objetos recebido
        dispositivos = objetos;

        // Criar e setar o adaptador
        mAdapter = new DispositivoCardViewAdapter(dispositivos);
        mRecyclerView.setAdapter(mAdapter);

        // Defina o listener de clique no adaptador
        mAdapter.setOnItemClickListener(new DispositivoCardViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ObjDispositivoStatus dispositivo) {
                // Lógica para manipular o clique do item

                Intent TelaMapa = new Intent(Dispositivos.this, Mapa.class);
                TelaMapa.putExtra("IdUsuario", IdUsuario);
                TelaMapa.putExtra("IdUsuarioDispositivo", dispositivo.getIdUsuarioDispositivo());
                TelaMapa.putExtra("NomeDispositivo", dispositivo.getNome());
                startActivity(TelaMapa);
            }
        });

        mAdapter.setOnMenuClickListener(new DispositivoCardViewAdapter.OnMenuClickListener() {
            @Override
            public void onMenuClick(int position, ObjDispositivoStatus dispositivo, View view) {
                // Lógica para manipular o clique do item
                PopupMenu popup = new PopupMenu(Dispositivos.this, view);
                popup.getMenuInflater().inflate(R.menu.dispositivo_opcoes, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.opcao1) {
                            Intent TelaExcluir = new Intent(Dispositivos.this, Excluir.class);
                            TelaExcluir.putExtra("IdUsuario", IdUsuario);
                            TelaExcluir.putExtra("IdUsuarioDispositivo", dispositivo.getIdUsuarioDispositivo());
                            startActivity(TelaExcluir);

                            return true;
                        } else if (id == R.id.opcao2) {
                            Intent TelaConfiguracao = new Intent(Dispositivos.this, Configuracao.class);
                            TelaConfiguracao.putExtra("IdUsuario", IdUsuario);
                            TelaConfiguracao.putExtra("IdUsuarioDispositivo", dispositivo.getIdUsuarioDispositivo());
                            startActivity(TelaConfiguracao);
                            return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public void onError() {
        swipeRefreshLayout.setRefreshing(false);
        // Tratar o erro
        Log.i("Teste", "Erro");
    }


    public void BotaoConta(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.conta_opcoes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.opcao1) {

                    Intent TelaConta = new Intent(Dispositivos.this, Conta.class);
                    TelaConta.putExtra("IdUsuario", IdUsuario);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(Dispositivos.this, Login.class);
                    TelaLogin.putExtra("Sair", "true");
                    startActivity(TelaLogin);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public void BotaoAdd(View v){

       Intent TelaAddDispositivo = new Intent(this, AdicionarDispositivo.class);
       TelaAddDispositivo.putExtra("IdUsuario", IdUsuario);
       startActivity(TelaAddDispositivo);
    }







}