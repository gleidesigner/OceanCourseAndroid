package com.gleides.myappfirebase;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;
import com.oceanbrasil.libocean.control.glide.ImageDelegate;

import java.io.File;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ImageDelegate.BytesListener {

    private static final int REQUEST_INTENT_CAMERA = 10;
    private static final String[] PERMISSIONS_READ_WRITE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_PERMISSION = 11;

    private EditText edtTitle;
    private EditText edAutor;
    private EditText edPagina;
    private EditText edAno;
    private Livro livro;
    private Spinner spCategoria;
    private File pathImage;
    private ImageView imgCapa;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRefLivros = database.getReference("livro");

    private byte[] byteImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initialViews();

        // Create an ArrayAdapter using the string array and a default categoria layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.book_array,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the com.gleides.myappfirebase.adapter to the categoria
        spCategoria.setAdapter(adapter);
        //Set Image Default
        imgCapa.setImageResource(R.mipmap.ic_launcher);

        //Listener ao clicar na tecla ENTER
        edAno.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.i("onKey", "Enter or Button OK pressed");
                    //Solicitação de serviço do Helper
                    salvaBookFirebase();
                }
                return false;
            }
        });

        imgCapa.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        abrirCamera();
    }
    /**
     * Abrir a camera verficando se existe Permissao
     */
    private void abrirCamera(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            verificaChamarPermissao();
        } else {
            // tenho permissao, chama a intent de camera
            intentAbrirCamera();
        }
    }

    private void verificaChamarPermissao() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // exibir o motivo de esta precisando da permissao
            Toast.makeText(getApplicationContext(), "Voce precisa dá permissão", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_READ_WRITE, REQUEST_PERMISSION);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_READ_WRITE, REQUEST_PERMISSION);
        }
    }

    private void intentAbrirCamera(){
        String nomeFoto = DateFormat.format("yyyy-mm-dd_hhmmss", new Date()).toString()+"firebase.jpg";
        pathImage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),nomeFoto);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pathImage));
        startActivityForResult(intent, REQUEST_INTENT_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION){
            if (PermissionUtil.verifyPermissions(grantResults)) {
                // tem
                Log.d("Ale","tem permissao");
                intentAbrirCamera();
            } else {
                // nao tem a permissao
                Log.d("Ale","nao tem permissao");
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_INTENT_CAMERA && resultCode == RESULT_OK){
            if (pathImage != null && pathImage.exists()){
                Ocean.glide(this)
                        .load(Uri.fromFile(pathImage))
                        .build(GlideRequest.BYTES)
                        .addDelegateImageBytes(this)
                        .toBytes(300, 300);
            }else{
                Log.e("Ale","FILE null");
            }
        }else{
            Log.d("Ale","nao usou a camera");
        }
    }

    @Override
    public void createdImageBytes(byte[] bytes) {
        byteImage = bytes;
        Bitmap bitmap = Ocean.byteToBitmap(bytes);
        imgCapa.setImageBitmap(bitmap);
    }

    public void initialViews() {

        //Recupera as referencias do layout
        spCategoria = (Spinner) findViewById(R.id.book_spinner);
        edtTitle = (EditText) findViewById(R.id.detalhesTitulo);
        edAutor = (EditText) findViewById(R.id.detalhesAutor);
        edPagina = (EditText) findViewById(R.id.detalhesPagina);
        edAno = (EditText) findViewById(R.id.detalhesAno);
        imgCapa = (ImageView) findViewById(R.id.detalhesCapa);
    }

    public void checkDataLivro(Livro livro){

    }
    public void salvaBookFirebase() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Enviando dandos...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://myappocean.appspot.com")
                .child("livrosImagens")
                .child(pathImage.getName());

        storageReference.putBytes(byteImage).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();

                String titulo = edtTitle.getText().toString();
                String autor = edAutor.getText().toString();
                Integer pagina = Integer.parseInt(edPagina.getText().toString());
                Integer ano = Integer.parseInt(edAno.getText().toString());
                String categoria = spCategoria.getSelectedItem().toString();
                String capa =  taskSnapshot.getDownloadUrl().toString();

                livro = new Livro(categoria, titulo, autor, pagina, ano, capa);

                mRefLivros.push().setValue(livro);
                clearEditText();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.setMessage("Error de envio de dandos!");
            }
        });


        /*livro = new Livro(categoria, titulo, autor, pagina, ano, capa);

        mRefLivros.push().setValue(livro).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    clearEditText();

                }else{
                    progressDialog.setMessage("Error de envio de dandos!");
                }
            }
        });*/
    }

    public void clearEditText() {
        imgCapa.setImageResource(R.mipmap.ic_launcher);
        edtTitle.setText("");
        edAutor.setText("");
        edPagina.setText("");
        edAno.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu, menu);
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
