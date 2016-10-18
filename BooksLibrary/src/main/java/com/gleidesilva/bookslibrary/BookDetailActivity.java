package com.gleidesilva.bookslibrary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;

public class BookDetailActivity extends AppCompatActivity {
    TextView txtTitle;
    TextView txtAutor;
    TextView txtPagina;
    TextView txtAno;
    ImageView imgCapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_book);
        initialView();
        getLivro();
    }

    public void initialView() {
        txtTitle = (TextView) findViewById(R.id.detalhesTitulo);
        txtAutor = (TextView) findViewById(R.id.detalhesAutor);
        txtPagina = (TextView) findViewById(R.id.detalhesPaginas);
        txtAno = (TextView) findViewById(R.id.detalhesAno);
        imgCapa = (ImageView) findViewById(R.id.detalhesImageView);

    }

    public void getLivro() {
        //String titulo = getIntent().getStringExtra("titulo");
        Book book = (Book) getIntent().getSerializableExtra("book");

        txtTitle.setText(book.getTitulo());
        txtAutor.setText(book.getAutor());
        txtPagina.setText(book.getPagina());
        txtAno.setText(book.getAno());

        if (book.getCapa() != null){
            Ocean
                    .glide(this)
                    .load(book.getCapa())
                    .build(GlideRequest.BITMAP)
                    .resize(100,100)
                    .circle()
                    .into(imgCapa);
        }
    }
}