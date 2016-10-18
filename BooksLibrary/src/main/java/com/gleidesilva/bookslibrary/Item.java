package com.gleidesilva.bookslibrary;

import java.util.ArrayList;

/**
 * Created by gleides on 17/10/16.
 */

public class Item {
    public String categoria;

    public ArrayList<Book> livros;

    public String getCategorias() {
        return categoria;
    }

    public void setCategorias(String categorias) {
        this.categoria = categorias;
    }

    public ArrayList<Book> getLivros() {
        return livros;
    }

    public void setLivros(ArrayList<Book> livros) {
        this.livros = livros;
    }
}
