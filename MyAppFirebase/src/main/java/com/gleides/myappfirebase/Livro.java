package com.gleides.myappfirebase;

/**
 * Created by gleides on 18/10/16.
 */

public class Livro {
    private String categoria;
    private String titulo;
    private String autor;
    private Integer pagina;
    private Integer ano;
    private String capa;

    public Livro() {
    }

    public Livro(String categoria, String titulo, String autor, Integer pagina, Integer ano, String capa) {
        this.categoria = categoria;
        this.titulo = titulo;
        this.autor = autor;
        this.pagina = pagina;
        this.ano = ano;
        this.capa = capa;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getPagina() {
        return pagina;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
