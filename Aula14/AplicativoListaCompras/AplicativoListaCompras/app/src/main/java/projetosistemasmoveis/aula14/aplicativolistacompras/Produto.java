package projetosistemasmoveis.aula14.aplicativolistacompras;

import java.io.Serializable;

public class Produto{
    private String id;
    private String nome;
    private double preco;

    public Produto(String id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Produto() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {this.nome = nome;}

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
