package projetosistemasmoveis.aula07.tarefa1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class Teste extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
    }

    public void mostrarMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_opcoes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.opcao1:
                        // faça algo quando a opção 1 é selecionada
                        return true;
                    case R.id.opcao2:
                        // faça algo quando a opção 2 é selecionada
                        return true;
                    case R.id.opcao3:
                        // faça algo quando a opção 3 é selecionada
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }
}