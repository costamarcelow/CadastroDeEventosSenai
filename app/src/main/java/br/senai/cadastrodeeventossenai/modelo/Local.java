package br.senai.cadastrodeeventossenai.modelo;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Local implements Serializable {

    private int id;
    private String descricao;
    private String bairro;
    private String cidade;
    private int capacidade;

    public Local(int id, String descricao, String bairro, String cidade, int capacidade) {
        this.id = id;
        this.descricao = descricao;
        this.bairro = bairro;
        this.cidade = cidade;
        this.capacidade = capacidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @NonNull
    @Override
    public String toString() {
        return descricao;
    }
}
