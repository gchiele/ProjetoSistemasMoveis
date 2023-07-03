package projetosistemasmoveis.rastreador.Objetos;

public class ObjDispositivoConfiguracao {
    private String idUsuarioDispositivo;
    private String nome;
    private String codigo;

    public ObjDispositivoConfiguracao(String idUsuarioDispositivo, String nome, String codigo) {
        this.idUsuarioDispositivo = idUsuarioDispositivo;
        this.nome = nome;
        this.codigo = codigo;
    }

    // Getters e setters

    public String getIdUsuarioDispositivo() {
        return idUsuarioDispositivo;
    }

    public void setIdUsuarioDispositivo(String idUsuarioDispositivo) {
        this.idUsuarioDispositivo = idUsuarioDispositivo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
