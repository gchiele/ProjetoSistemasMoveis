package projetosistemasmoveis.aula14.aplicativolistacompras;

public class ListaCompra {

    private String nome;
    private String dataCriacao;
    private String id;

    public ListaCompra(String id, String nome, String dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
    }

    public ListaCompra(){}


    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

