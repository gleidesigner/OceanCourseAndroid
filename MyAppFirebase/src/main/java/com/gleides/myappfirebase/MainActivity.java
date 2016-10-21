package com.gleides.myappfirebase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    ;
    DatabaseReference mDbLivro = firebaseDatabase.getReference("livros");

    private EditText edtTitle;
    private EditText edAutor;
    private EditText edPagina;
    private EditText edAno;
    private Livro livro;
    private Spinner spCategoria;
    //private ImageView imgCapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initialViews();

        // Create an ArrayAdapter using the string array and a default categoria layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.book_array,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.activity_list_item);
        // Apply the adapter to the categoria
        spCategoria.setAdapter(adapter);


    }

    public void initialViews() {

        //Recupera as referencias do layout
        spCategoria = (Spinner) findViewById(R.id.book_spinner);
        edtTitle = (EditText) findViewById(R.id.detalhesTitulo);
        edAutor = (EditText) findViewById(R.id.detalhesAutor);
        edPagina = (EditText) findViewById(R.id.detalhesPagina);
        edAno = (EditText) findViewById(R.id.detalhesAno);
        //imgCapa = (ImageView) findViewById(R.id.detalhesCapa);
    }


    public void salvaBookFirebase() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Enviando dandos...");
        progressDialog.show();

        String titulo = edtTitle.getText().toString();
        String autor = edAutor.getText().toString();
        Integer pagina = Integer.parseInt(edPagina.getText().toString());
        Integer ano = Integer.parseInt(edAno.getText().toString());
        String categoria = spCategoria.getSelectedItem().toString();

        livro = new Livro(categoria, titulo, autor, pagina, ano);

        mDbLivro.push().setValue(livro).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    clearEditText();

                }else{
                    progressDialog.setMessage("Error de envio de dandos!");
                }
            }
        });
    }

    public void clearEditText() {
        edtTitle.setText("");
        edAutor.setText("");
        edPagina.setText("");
        edAno.setText("");
    }

/*    public void LerDadosFirebase(View view) {

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
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.salvar_data) {
            salvaBookFirebase();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
