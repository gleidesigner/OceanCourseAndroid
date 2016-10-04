package com.gleidesilva.oceancourseandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int PRICE_BY_CUP = 5;
    int quantity;
    EditText name;
    TextView summary;
    CheckBox whippedCream;
    CheckBox chocolate;
    TextView quantityItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }

    public void submitOrder(View view) {
        int price = calculatePrice(quantity, chocolate.isChecked(), whippedCream.isChecked());
        String summaryOrder = createdOrderSummary(quantity, price, chocolate.isChecked(), whippedCream.isChecked());
        summary.setText(summaryOrder);
        Log.i("MainActivity","log: " + price);

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

    public void displayPrice() {

    }

    public void increment(View view) {
        if(quantity >= 100) {
            Toast.makeText(this, "Não pode ser mais que 100 copos", Toast.LENGTH_LONG).show();
            return;
        }
        quantity++;
        displayQuantity();
    }

    public void decrement(View view) {
        if(quantity < 0) return;
        quantity--;
        displayQuantity();
    }

    public void displayQuantity() {
        quantityItem.setText(Integer.toString(quantity));
    }

    public String createdOrderSummary(int quantity, int price, boolean chocolateChecked, boolean whippedCreamChecked) {
        String msg = name.getText().toString();
        msg += "\nAdd Chocolate? " + chocolateChecked;
        msg += "\nAdd Whipped Cream? " + whippedCreamChecked;
        msg += "\nQuantity: " + quantity;
        msg += "\nTotal: " + price;
        return msg;
    }

    private int calculatePrice(int quantity, boolean isChocolate, boolean isCream) {
        if (isChocolate)
            quantity++;

        if (isCream)
            quantity++;

        return quantity * PRICE_BY_CUP;
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
