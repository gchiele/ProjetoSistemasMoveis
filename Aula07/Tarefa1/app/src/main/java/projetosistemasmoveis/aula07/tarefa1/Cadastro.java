package projetosistemasmoveis.aula07.tarefa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }
    public  void BotaoSalvar (View v){

        EditText CampoNome = (EditText) findViewById(R.id.CampoNome);
        EditText CampoEndereco = (EditText) findViewById(R.id.CampoEndereco);
        EditText CampoNumero = (EditText) findViewById(R.id.CampoNumero);
        EditText CampoCep = (EditText) findViewById(R.id.CampoCep);
        EditText CampoComplemento = (EditText) findViewById(R.id.CampoComplemento);
        EditText CampoEmail = (EditText) findViewById(R.id.CampoEmail);

        String Nome = CampoNome.getText().toString();
        String Endereco = CampoEndereco.getText().toString();
        String Numero = CampoNumero.getText().toString();
        String Cep = CampoCep.getText().toString();
        String Complemento = CampoComplemento.getText().toString();
        String Email = CampoEmail.getText().toString();

        if(Nome.isEmpty() || Endereco.isEmpty() || Numero.isEmpty() || Cep.isEmpty() || Complemento.isEmpty() || Email.isEmpty()){
            Intent TelaErroCadastro = new Intent(this, ErroCadastro.class);

            TelaErroCadastro.putExtra("Nome", Nome.isEmpty());
            TelaErroCadastro.putExtra("Endereco", Endereco.isEmpty());
            TelaErroCadastro.putExtra("Numero", Numero.isEmpty());
            TelaErroCadastro.putExtra("Cep", Cep.isEmpty());
            TelaErroCadastro.putExtra("Complemento", Complemento.isEmpty());
            TelaErroCadastro.putExtra("Email", Email.isEmpty());

            startActivity(TelaErroCadastro);
        }else{
            Intent TelaSucessoCadastro = new Intent(this, SucessoCadastro.class);
            TelaSucessoCadastro.putExtra("Nome", Nome);
            startActivity(TelaSucessoCadastro);
        }

    }

}