package com.gleidesilva.bookslibrary;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;

public class BookDetailActivity extends AppCompatActivity {
    TextView title;
    TextView autor;
    TextView pagina;
    TextView ano;
    ImageView capa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getLivro();
    }

    public void initialView() {
        title = (TextView) findViewById(R.id.detailTitulo);
        autor = (TextView) findViewById(R.id.detailAutor);
        pagina = (TextView) findViewById(R.id.detailPagina);
        ano = (TextView) findViewById(R.id.detailAno);
        capa = (ImageView) findViewById(R.id.detailImageView);

    }

    public void getLivro() {
        //String titulo = getIntent().getStringExtra("titulo");
        Book book = (Book) getIntent().getSerializableExtra("book");
        initialView();

        title.setText(book.getTitulo());
        autor.setText(book.getAutor());
        pagina.setText(String.valueOf(book.getPagina()));
        ano.setText(String.valueOf(book.getAno()));

        if (book.getCapa() != null){
            Ocean
                    .glide(this)
                    .load(book.getCapa())
                    .build(GlideRequest.BITMAP)
                    .resize(100,100)
                    .into(capa);
        }
    }
}