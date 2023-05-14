package projetosistemasmoveis.rastreador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // Intent TelaDispostivos = new Intent(this, Dispositivos.class);
       // startActivity(TelaDispostivos);
    }



    public  void BotaoLogin (View v){

        TextInputEditText CampoEmail = (TextInputEditText) findViewById(R.id.TextCampoEmail);
        TextInputEditText CampoSenha = (TextInputEditText) findViewById(R.id.TextCampoSenha);

        String Email = CampoEmail.getText().toString();
        String Senha = CampoSenha.getText().toString();

        if(Email.equals("guilherme.chiele@gmail.com") && Senha.equals("1234")){
            Intent TelaDispostivos = new Intent(this, Dispositivos.class);
            startActivity(TelaDispostivos);
        }else{
            Intent TelaErroLogin = new Intent(this, ErroLogin.class);
            startActivity(TelaErroLogin);
        }
    }

    public  void BotaoEsqueceuSenha (View v){
        Intent TelaEsqueceuSenha = new Intent(this, EsqueceuSenha.class);
        startActivity(TelaEsqueceuSenha);
    }

    public  void BotaoInscrever (View v){
        Intent TelaInscrever = new Intent(this, Inscrever.class);
        startActivity(TelaInscrever);
    }


}