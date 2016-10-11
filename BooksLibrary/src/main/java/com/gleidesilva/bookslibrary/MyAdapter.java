package com.gleidesilva.bookslibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gleides on 10/10/16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<Book> bookList;

    public MyAdapter(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_book, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Book itemBookList = bookList.get(position);

        //Seta os valores do livro para o layout dentro do holder
        holder.setTxtTitle(itemBookList.getTitulo())
                .setTxtAutor(itemBookList.getAutor())
                .setTxtPagina(itemBookList.getPagina())
                .setTxtAno(itemBookList.getAno())
                .setTImgBook(itemBookList.getCapa());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtAutor;
        private TextView txtPagina;
        private TextView txtAno;
        private ImageView imgCapa;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            //Recupera as referencias do layout
            txtTitle = (TextView) itemView.findViewById(R.id.titulo);
            txtAutor = (TextView) itemView.findViewById(R.id.autor);
            txtPagina = (TextView) itemView.findViewById(R.id.pagina);
            txtAno = (TextView) itemView.findViewById(R.id.ano);
            imgCapa = (ImageView) itemView.findViewById(R.id.imageBook);
            progressBar = (ProgressBar) itemView.findViewById(R.id.my_progress_bar);
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

        public ViewHolder setTImgBook(String urlBookCover) {
            if (imgCapa == null) return this;
            new DownloadImageTask(imgCapa).execute(urlBookCover);
            return this;
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mImage = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mImage = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mImage;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

    }
}