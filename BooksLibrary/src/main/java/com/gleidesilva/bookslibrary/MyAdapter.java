package com.gleidesilva.bookslibrary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gleides on 10/10/16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<Book> lista;
    public MyAdapter(ArrayList<Book> lista) {
        this.lista = lista;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_book, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Book book = lista.get(position);

        //Seta os valores do livro para o layout dentro do holder
        holder.setTxtTitle(book.getTitulo())
                .setTxtAutor(book.getAutor())
                .setTxtPagina(book.getPagina())
                .setTxtAno(book.getAno())
                .setTImgBook(book.getCapa());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtAutor;
        private TextView txtPagina;
        private TextView txtAno;
        private ImageView imgCapa;

        public ViewHolder(View itemView) {
            super(itemView);
            //Recupera as referencias do layout
            txtTitle = (TextView) itemView.findViewById(R.id.titulo);
            txtAutor = (TextView) itemView.findViewById(R.id.autor);
            txtPagina = (TextView) itemView.findViewById(R.id.pagina);
            txtAno = (TextView) itemView.findViewById(R.id.ano);
            imgCapa = (ImageView) itemView.findViewById(R.id.imageBook);
        }

        public ViewHolder setTxtTitle(String title) {
            if (txtTitle == null) return this;
            txtTitle.setText(title);
            return this;
        }

        public ViewHolder setTxtAutor(String autor) {
            if (txtAutor == null) return this;
            txtAutor.setText(autor);
            return this;
        }

        public ViewHolder setTxtPagina(int pagina) {
            if (txtPagina == null) return this;
            txtPagina.setText(pagina+"");
            return this;
        }

        public ViewHolder setTxtAno(int ano) {
            if (txtAno == null) return this;
            txtAno.setText(ano+"");
            return this;
        }

        public ViewHolder setTImgBook(String capa) {
            if (imgCapa == null) return this;
            return this;
        }

    }
}
