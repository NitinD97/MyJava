package com.example.nitin.myjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity=1;

    public void increaseOrder(View view)
    {
        if(quantity<10) {
            quantity++;
            displayQuantity(quantity);
        }
        else {
            Toast.makeText(this,"More than 10 cups cannot be ordered at once!!!",Toast.LENGTH_SHORT).show();
        }
    }

    public void decreaseOrder(View view)
    {
        if(quantity>1) {
            quantity--;
            displayQuantity(quantity);
        }
        else {
            Toast.makeText(this,"Minimum 1 cup should be ordered!!!",Toast.LENGTH_SHORT).show();
        }
    }


    public void submitOrder(View view) {

        EditText nameField=(EditText) findViewById(R.id.name_field);
        String nameOfPerson=nameField.getText().toString();

        if(nameOfPerson.isEmpty())
        {
            Toast.makeText(this,"Please enter your name!!!",Toast.LENGTH_SHORT).show();
            return;
        }

        CheckBox whippedCreamCheckBox=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream= whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckbox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckbox.isChecked();

        int price=calculatePrice(quantity,hasWhippedCream,hasChocolate);
        String priceMessage= createOrderSummary(price,hasWhippedCream,hasChocolate,nameOfPerson);
        sendOrderSummaryMail(priceMessage);
    }

    public void sendOrderSummaryMail(String message)
    {
        Intent intent= new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,"nitindhiman@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Order Summary!");
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }

    public String createOrderSummary(int price,boolean hasWhippedCream, boolean hasChocolate,String name)
    {
        String message="Hello, "+name;
        message+="\nTotal Price: $"+price;
        message+="\nHas Whipped Cream ($2 per cup): "+hasWhippedCream;
        message+="\nHas Chocolate ($1 per cup): "+hasChocolate;
        message+="\nQuantity: "+quantity;
        message+="\nThank You!";
        return message;
    }

    public int calculatePrice(int quantity,boolean hasWhippedCream,boolean hasChocolate)
    {
        int result;
        int whippedCreamPrice;
        int chocolatePrice;

        if(hasWhippedCream)
            whippedCreamPrice=2;
        else
            whippedCreamPrice=0;

        if(hasChocolate)
            chocolatePrice=1;
        else
            chocolatePrice=0;


        int priceOfCoffee=5;
        result=whippedCreamPrice*quantity+priceOfCoffee*quantity+chocolatePrice*quantity;
        return result;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}