package projetosistemasmoveis.rastreador.Objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ObjUsuario {
    private String Id;
    private String Nome;
    private String Fone;
    private String Email;
    private String Senha;


    public ObjUsuario(String Id, String Nome, String Fone, String Email, String Senha) {
        this.Id = Id;
        this.Nome = Nome;
        this.Fone = Fone;
        this.Email = Email;
        this.Senha = Senha;

    }

    public String getId() {
        return Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }

    public String getNome() {
        return Nome;
    }
    public void setNome(String Nome) {
        this.Nome = Nome;
    }
    public String getFone() {
        return Fone;
    }
    public void setFone(String Fone) {
        this.Fone = Fone;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getSenha() {
        return Senha;
    }
    public void setSenha(String Senha) {
        this.Senha = Senha;
    }


    public static ObjUsuario fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        String Id = jsonObject.getString("id");
        String Nome = jsonObject.getString("nome");
        String Fone = jsonObject.getString("fone");
        String Email = jsonObject.getString("email");
        String Senha = jsonObject.getString("senha");

        return new ObjUsuario(Id, Nome, Fone, Email, Senha);
    }
}
