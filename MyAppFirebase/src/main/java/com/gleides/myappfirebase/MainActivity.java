package com.gleides.myappfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();;
    DatabaseReference mDbLivro = firebaseDatabase.getReference("livros");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

    }

    public void AddBookFirebase (View view){
        Livro livro1 = new Livro();
        livro1.setTitulo("Wireshark Guia Prático");
        livro1.setAutor("Robert Shimonski");
        livro1.setAno(2013);
        livro1.setPagina(168);
        livro1.setCapa("http://172.25.1.17/oceanbook/WiresharkGuiaPrático.jpg");
        mDbLivro.setValue(livro1);

       /* Livro livro2 = new Livro();
        livro2.setTitulo("Introdução à Análise Forense em Redes de Computadores");
        livro2.setAutor("Ricardo Kléber M. Galvão");
        livro2.setAno(2013);
        livro2.setPagina(152);
        livro2.setCapa("http://172.25.1.17/oceanbook/IntroduçãoAnáliseForenseRedes.jpg");

        mDbLivro.setValue(livro2);*/

    }

    public void LerDadosFirebase(View view) {

        // Read from the database
        mDbLivro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Livro livro = dataSnapshot.getValue(Livro.class);
                Log.d("Livros", "Value is: " + livro.getAutor());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Livros", "Failed to read value.", error.toException());
            }
        });
    }
}
