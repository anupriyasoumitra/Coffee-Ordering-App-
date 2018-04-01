/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String priceMessage, name="";
        boolean hasWhippedCream, hasChocolate;
        if (quantity == 0)
            priceMessage = "No orders.";
        if (quantity > 100)
            priceMessage = "Order Limit exceeded.";
        else {
            CheckBox WC = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
            hasWhippedCream = WC.isChecked();
            CheckBox Chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
            hasChocolate = Chocolate.isChecked();
            EditText namefield = (EditText) findViewById(R.id.plain_text_input);
            name = namefield.getText().toString();
            //Log.v("MainActivity", "Whipped Cream" + hasWhippedCream);
            int p = calculatePrice(hasWhippedCream, hasChocolate);
            priceMessage = createOrderSummary(p, hasWhippedCream, hasChocolate, name);

        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            displayMessage(priceMessage);


        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean WC, boolean C) {
        int price = 0;
        if (WC == true)
            price = (1 * quantity);
        if (C == true)
            price = (2 * quantity);
        price += (quantity * 5);
        return price;
    }

    /**
     * summary of the order made.
     *
     * @param price of a cup of coffee
     * @param w     for has whipped cream or not
     * @param name  for name entered by user
     *              returns name, quanting, price and a thank you message
     */
    public String createOrderSummary(int price, boolean w, boolean c, String name) {
        String s = "Name: " + name + "\nQuantity: " + quantity + "\nWhipped Cream Ordered? " + w + "\nChocolate Topping Ordered? " + c + "\nTotal: " + price + "\nThank You!";
        return s;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }


    /* this method is called when + button is clicked*/
    public void increament(View view) {
        if (quantity != 100)
            quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /* this method is called when - button is clicked*/
    public void decreament(View view) {
        if (quantity != 0)
            quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
