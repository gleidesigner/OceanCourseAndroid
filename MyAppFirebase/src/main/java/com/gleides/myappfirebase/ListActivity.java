package com.gleides.myappfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gleides.myappfirebase.adapter.ListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements ValueEventListener {

    private ArrayList arrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListAdapter adapter = new ListAdapter(this, arrayList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recicler_view_book);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void LerDadosFirebase() {
        FirebaseDatabase.getInstance().getReference().child("livros").addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
            Livro livro = dataSnapshot.getValue(Livro.class);
            arrayList.add(livro);
            Log.d("Livros", "Value is: " + livro.getAutor());
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
