package projetosistemasmoveis.rastreador.Objetos;

public class ObjDispositivoStatus {

    private String idUsuarioDispositivo;
    private String nome;
    private boolean online;
    private String ultimoDadoRecebido;

    public ObjDispositivoStatus(String idUsuarioDispositivo, String nome, boolean online, String ultimoDadoRecebido) {
        this.idUsuarioDispositivo = idUsuarioDispositivo;
        this.nome = nome;
        this.online = online;
        this.ultimoDadoRecebido = ultimoDadoRecebido;
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

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getUltimoDadoRecebido() {
        return ultimoDadoRecebido;
    }

    public void setUltimoDadoRecebido(String ultimoDadoRecebido) {
        this.ultimoDadoRecebido = ultimoDadoRecebido;
    }

}
