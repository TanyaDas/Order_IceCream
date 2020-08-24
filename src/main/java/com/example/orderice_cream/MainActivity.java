package com.example.orderice_cream;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int noOfQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /* This method is called when the + button is clicked.*/
    public void incrementOrder(View view) {
        if(noOfQuantity == 100)
        {
            Toast.makeText(this,"You cannot have more than 100 icecreams at single order",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            noOfQuantity = noOfQuantity + 1;
            display(noOfQuantity);
        }

    }

    /* This method is called when the - button is clicked.*/
    public void decrementOrder(View view) {
        if(noOfQuantity < 1)
        {
            Toast.makeText(this, "You need to buy atleast 1 icecream", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        else
        {
            noOfQuantity = noOfQuantity - 1;
            display(noOfQuantity);
        }


    }

    /*This method is called when the order button is clicked.*/
    public void submitOrder(View view) {
        int price = calculatePrice(noOfQuantity);
        CheckBox home = (CheckBox) findViewById(R.id.homeDelivery_checkbox);
        boolean needHomeDelivery = home.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_text_input);
        String name = nameField.getText().toString();

        String showSummary = CreateOrderSummary(name,price, needHomeDelivery);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"Order IceCream for "+name);
        intent.putExtra(Intent.EXTRA_TEXT,showSummary);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displaySummary(showSummary);


    }

    public String CreateOrderSummary(String nameField,int cost, boolean delivery) {
        String summary = "Name : "+nameField +"\n";
        summary = summary + "Quantity : " + noOfQuantity + "\n";
        summary = summary + "Total : " + cost + "₹\n";
        //  summary = summary + "Home Delivery : " + delivery + "\n";
        summary = summary + "Thank You!";
        return summary;
    }

    public int calculatePrice(int i) {
        int price = noOfQuantity * 50;
        return price;
    }

    public void displaySummary(String text) {
        TextView t = (TextView) findViewById(R.id.summary_String);
        t.setText("" + text);
    }

   /* public void carrybagCheckBox() {
        CheckBox bag = (CheckBox) findViewById(R.id.carryBag_checkbox);
        boolean needCarryBag = bag.isChecked();
    } */


    /*This method displays the given quantity value on the screen*/
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_no_text_view);
        quantityTextView.setText("" + number);
    }

}
