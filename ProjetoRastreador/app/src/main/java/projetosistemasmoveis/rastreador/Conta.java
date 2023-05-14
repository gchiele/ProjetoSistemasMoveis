package projetosistemasmoveis.rastreador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class Conta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
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
        startActivity(TelaAlterarSenha);
    }

    public  void BotaoSalvarConta(View v) {
        // Salva Dados
        Voltar();
    }

    public  void BotaoVoltar(View v){

        Voltar();
    }

    private void Voltar(){
        Intent TelaDispositivo = new Intent(this, Dispositivos.class);
        startActivity(TelaDispositivo);
    }

}