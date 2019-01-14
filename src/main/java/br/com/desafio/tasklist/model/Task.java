package br.com.desafio.tasklist.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Task {

    private long id;
    private Date dtCriacao;
    private String titulo;
    private String descricao;
    private boolean concluida;

    public Task() {
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Date getDtCriacao() {
	return dtCriacao;
    }

    public void setDtCriacao(Date dtCriacao) {
	this.dtCriacao = dtCriacao;
    }

    public String getTitulo() {
	return titulo;
    }

    public void setTitulo(String titulo) {
	this.titulo = titulo;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public boolean isConcluida() {
	return concluida;
    }

    public void setConcluida(boolean concluida) {
	this.concluida = concluida;
    }

    @Override
    public String toString() {
	return "Task [id=" + id + ", dtCriacao=" + dtCriacao + ", titulo=" + titulo + ", descricao=" + descricao + ", concluida=" + concluida + "]";
    }

}