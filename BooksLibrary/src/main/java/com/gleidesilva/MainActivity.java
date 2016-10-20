package com.gleidesilva;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gleidesilva.bookslibrary.Book;
import com.gleidesilva.bookslibrary.BookDetailActivity;
import com.gleidesilva.bookslibrary.BookStore;
import com.gleidesilva.bookslibrary.Item;
import com.gleidesilva.adapter.BookAdapter;
import com.gleidesilva.bookslibrary.R;
import com.google.gson.Gson;
import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.http.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Request.RequestListener, BookAdapter.AdapterListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    //ProgressBar progressBar;
    private ArrayList<Book> mBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_book);
        //Efeito de pulsador
       /* setContentView(R.layout.pulsator4droid);
        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();*/

        /*progressBar = (ProgressBar) findViewById(R.id.my_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);*/

        setTitle(getResources().getString(R.string.str_source_data_url));
        Ocean.newRequest("http://gitlab.oceanmanaus.com/snippets/1/raw", this).get().send();

    }

    public StringBuilder getDataFile() {
        try {
            AssetManager assetManager = getResources().getAssets();
            System.setIn(assetManager.open("jsonLibraryBook.txt"));
            InputStreamReader inputStreamReader = new InputStreamReader(System.in, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = bufferedReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            return responseStrBuilder;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onRequestOk(String result, JSONObject jsonObject, int code) {
        if (code == Request.NENHUM_ERROR) {
            //getListBook(result);
            stringToGson(result);
            createAdapter();
        }
    }

    private void stringToGson(String result) {
        mBookList = new ArrayList<>();
        Gson gson = new Gson();
        BookStore bookStore = gson.fromJson(result, BookStore.class);
        ArrayList<Item> itens = bookStore.getOcean();

        for (Item item : itens) {
            mBookList.addAll(item.getLivros());
        }
    }

    private void createAdapter() {
        BookAdapter adapter = new BookAdapter(this, mBookList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recicler_view_book);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setAdapterListener(this);

        //hideLoadProgressBar(lista);
    }

    @Override
    public void onItemClick(View view, int pos) {
        Book book = mBookList.get(pos);
        Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);

    }

/*    private void hideLoadProgressBar(ArrayList<Book> lista) {
        if (lista.size() > 0) {
            progressBar.setVisibility(View.GONE);
        }
    }*/

    //metodo tradicional de pegar as informações do JSON
    public void getListBook(String result) {
        Log.d(TAG, "Request: " + result);
        //ArrayList<Book> lista = new ArrayList<>();

        if (result != null) {
            try {
                JSONObject jsObject = new JSONObject(result);
                JSONArray arrayOcean = jsObject.getJSONArray("ocean");

                for (int i = 0; i < arrayOcean.length(); i++) {
                    JSONObject item = arrayOcean.getJSONObject(i);
                    JSONArray livros = item.getJSONArray("livros");

                    for (int j = 0; j < livros.length(); j++) {

                        JSONObject livro = livros.getJSONObject(j);
                        String titulo = livro.getString("titulo");
                        String autor = livro.getString("autor");
                        int ano = livro.getInt("ano");
                        int paginas = livro.getInt("paginas");
                        String capa = livro.getString("capa");

                        Book mBook = new Book();
                        mBook.setTitulo(titulo);
                        mBook.setAutor(autor);
                        mBook.setAno(ano);
                        mBook.setPagina(paginas);
                        mBook.setCapa(capa);

                        mBookList.add(mBook);

                        Log.i(TAG, "Titulo: " + titulo);
                        Log.i(TAG, "Autor: " + autor);
                        Log.i(TAG, "Ano: " + String.format("%s", ano));
                        Log.i(TAG, "pagina: " + String.format("%s", paginas));
                        Log.i(TAG, "capa: " + j);
                    }
                }
                createAdapter();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.source_data_file) {
            setTitle(getResources().getString(R.string.str_source_data_file));
            getListBook(getDataFile().toString());
            return true;
        }
        if (id == R.id.source_data_url) {
            setTitle(getResources().getString(R.string.str_source_data_url));
            Ocean.newRequest("http://gitlab.oceanmanaus.com/snippets/1/raw", this).get().send();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
