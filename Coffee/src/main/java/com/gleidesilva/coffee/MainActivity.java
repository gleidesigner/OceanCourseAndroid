package com.gleidesilva.coffee;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    int PriceByCup = 5;
    int quantity = 0;
    EditText name;
    TextView summary;
    CheckBox whippedCream;
    CheckBox chocolate;
    TextView quantityItem;
    ArrayList<Pedido> mPedidos = new ArrayList<>();
    Pedido mPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setLayout();
    }

    public void setLayout(){
        name = (EditText) findViewById(R.id.name);
        summary = (TextView) findViewById(R.id.summaryOrder);
        whippedCream = (CheckBox) findViewById(R.id.whippedCream);
        chocolate = (CheckBox) findViewById(R.id.chocolate);
        quantityItem = (TextView) findViewById(R.id.quantity_text_view);
        quantityItem.setText(Integer.toString(quantity));
    }

    public void submitOrder(View view) {
        int price = calculatePrice();
        String summaryOrder = createdOrderSummary(price);
        summary.setText(summaryOrder);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        //mPedido = new Pedido(quantity,price,chocolate.isChecked(),whippedCream.isChecked(),name.getText().toString(), System.currentTimeMillis());
        mPedidos.add(mPedido);
        Log.i(TAG,"Price: " + price);

/*        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:992640530"));
        intent.putExtra("sms_body", summaryOrder);
        startActivity(intent);*/

/*        Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
        intentEmail.setData(Uri.parse("malito:gleidesigner@gmail.com"));
        intentEmail.putExtra(intentEmail.EXTRA_SUBJECT, "Ocean Coffee");
        intentEmail.putExtra(intentEmail.EXTRA_TEXT, summaryOrder);
        startActivity(intentEmail);*/


    }

    public void increment(View view) {
        if(quantity < 100) {
            quantity++;
            Log.i(TAG,"incrementQty: " + quantity);
            displayQuantity();
        }else {
            Toast.makeText(this, "Não pode ser mais que 100 copos", Toast.LENGTH_LONG).show();
        }
    }

    public void decrement(View view) {
        if(quantity > 0){
            quantity--;
            Log.i(TAG,"decrementQty: " + quantity);
            displayQuantity();
        }else {
            Toast.makeText(this, "Não pode ter valor negativo", Toast.LENGTH_LONG).show();
        }
    }

    public void displayQuantity() {
        Log.i(TAG,"Qty: " + quantity);
        quantityItem.setText(Integer.toString(quantity));
    }

    public String createdOrderSummary(int price) {
        String msg = name.getText().toString();
        msg += "\nAdd Chocolate? " + chocolate.isChecked();
        msg += "\nAdd Whipped Cream? " + whippedCream.isChecked();
        msg += "\nQuantity: " + quantity;
        msg += "\nTotal: " + price;
        return msg;
    }

    private int calculatePrice() {
        if (chocolate.isChecked())
            PriceByCup += 2;

        if (whippedCream.isChecked())
            PriceByCup++;

        return quantity * PriceByCup;
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
