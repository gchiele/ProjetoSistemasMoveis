package projetosistemasmoveis.aula07.tarefa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public  void BotaoLogin (View v){

        EditText CampoEmail = (EditText) findViewById(R.id.CampoEmail);
        EditText CampoSenha = (EditText) findViewById(R.id.CampoSenha);

        String Email = CampoEmail.getText().toString();
        String Senha = CampoSenha.getText().toString();

        if(Email.equals("peterrizzon@acad.ftec.com.br") && Senha.equals("123456")){
            Intent TelaSucessoLogin = new Intent(this, SucessoLogin.class);
            startActivity(TelaSucessoLogin);
        }else{
            Intent TelaErroLogin = new Intent(this, ErroLogin.class);
            startActivity(TelaErroLogin);
        }
    }
}