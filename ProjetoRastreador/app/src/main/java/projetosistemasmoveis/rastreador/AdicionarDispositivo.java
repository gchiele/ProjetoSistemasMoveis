package projetosistemasmoveis.rastreador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class AdicionarDispositivo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_dispositivo);
    }

    public void BotaoConta(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.conta_opcoes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.opcao1) {

                    Intent TelaConta = new Intent(AdicionarDispositivo.this, Conta.class);
                    startActivity(TelaConta);
                    return true;
                } else if (id == R.id.opcao2) {

                    Intent TelaLogin = new Intent(AdicionarDispositivo.this, Login.class);
                    startActivity(TelaLogin);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public  void BotaoVoltar(View v){
        Intent TelaDispostivos = new Intent(this, Dispositivos.class);
        startActivity(TelaDispostivos);
    }

    public  void BotaoSalvar(View v){


        //Intent TelaErroAdicionarDispositivo = new Intent(this, ErroAdicionarDispositivo.class);
        //startActivity(TelaErroAdicionarDispositivo);


        Intent TelaSucessoAdicionarDispositivo = new Intent(this, SucessoAdicionarDispositivo.class);
        startActivity(TelaSucessoAdicionarDispositivo);
    }

}