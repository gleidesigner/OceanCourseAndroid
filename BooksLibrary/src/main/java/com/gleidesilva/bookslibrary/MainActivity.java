package com.gleidesilva.bookslibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Book mBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_book);
        ArrayList<Book> lista = iniciaLista();

        for (Book book:lista) {
            Log.i(TAG, "titulo: " + book.getTitulo());
        }

        MyAdapter adapter = new MyAdapter(lista);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recicler_view_book);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public ArrayList<Book> iniciaLista(){
        ArrayList<Book> books = new ArrayList<>();
        mBook = new Book();
        mBook.setTitulo("MOODLE 2 para Autores e Tutores - 3ª Edição");
        mBook.setAutor("Robson Santos da Silva");
        mBook.setAno(2013);
        mBook.setPagina(168);
        mBook.setCapa("http://172.25.1.17/oceanbook/moodle2.jpg");
        books.add(mBook);

        mBook = new Book();
        mBook.setTitulo("NoSQL Essencial");
        mBook.setAutor("Pramod J. Sadalage / Martin Fowler");
        mBook.setAno(2013);
        mBook.setPagina(216);
        mBook.setCapa("http://172.25.1.17/oceanbook/NoSQLEssencial.png");
        books.add(mBook);

        mBook = new Book();
        mBook.setTitulo("Fundamentos de Bancos de Dados com C#");
        mBook.setAutor("Michael Schmalz");
        mBook.setAno(2012);
        mBook.setPagina(120);
        mBook.setCapa("http://172.25.1.17/oceanbook/BancoDeDadosComC.jpg");
        books.add(mBook);

        mBook = new Book();
        mBook.setTitulo("Jovem e Bem-Sucedido");
        mBook.setAutor("Juliano Niederauer");
        mBook.setAno(2013);
        mBook.setPagina(192);
        mBook.setCapa("http://172.25.1.17/oceanbook/JovemeBem-Sucedido.jpg");
        books.add(mBook);

        mBook = new Book();
        mBook.setTitulo("Lidando com a Incertezao");
        mBook.setAutor("Jonathan Fields");
        mBook.setAno(2013);
        mBook.setPagina(208);
        mBook.setCapa("http://172.25.1.17/oceanbook/LidandocomaIncerteza.png");
        books.add(mBook);

        return books;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
