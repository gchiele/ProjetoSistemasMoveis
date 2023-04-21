package projetosistemasmoveis.aula07.tarefa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ErroCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erro_cadastro);


        TextView CampoTexto = (TextView) findViewById(R.id.CampoTexto);

        String Texto ="";
        Intent TelaAnterior = getIntent();
        boolean Nome = TelaAnterior.getBooleanExtra("Nome",false);
        boolean Endereco = TelaAnterior.getBooleanExtra("Endereco",false);
        boolean Numero = TelaAnterior.getBooleanExtra("Numero",false);
        boolean Cep = TelaAnterior.getBooleanExtra("Cep",false);
        boolean Complemento = TelaAnterior.getBooleanExtra("Complemento",false);
        boolean Email = TelaAnterior.getBooleanExtra("Email",false);

        if(Nome){
            Texto = Texto + "Campo Nome esta Vazio!\n";
        }
        if(Endereco){
            Texto = Texto + "Campo Endereco esta Vazio!\n";
        }
        if(Numero){
            Texto = Texto + "Campo Numero esta Vazio!\n";
        }
        if(Cep){
            Texto = Texto + "Campo Cep esta Vazio!\n";
        }
        if(Complemento){
            Texto = Texto + "Campo Complemento esta Vazio!\n";
        }
        if(Email){
            Texto = Texto + "Campo Email esta Vazio!\n";
        }
        CampoTexto.setText(Texto);
    }

    public  void BotaoVoltar(View v){

        Intent TelaCadastro = new Intent(this, Cadastro.class);
        startActivity(TelaCadastro);

    }
}