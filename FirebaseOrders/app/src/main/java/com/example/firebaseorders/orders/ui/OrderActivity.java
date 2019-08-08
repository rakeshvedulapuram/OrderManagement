package com.example.firebaseorders.orders.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebaseorders.R;
import com.example.firebaseorders.orders.model.Order;
import com.example.firebaseorders.orders.repository.OrderRepository;
import com.example.firebaseorders.orders.utils.Constants;
import com.example.firebaseorders.orders.utils.DialogUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Author : Rakesh.V
 * Date : 08/08/2019
 * Order activity screen to Insert/Update orders
 */
public class OrderActivity extends AppCompatActivity {

    private EditText orderId, cutomerName, phoneNumber, address, cityName, countryName, dueDate, totalValue;
    private Button button_add;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Views initialization
        orderId = (EditText) findViewById(R.id.order_number);
        cutomerName = (EditText) findViewById(R.id.customer_name);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        address = (EditText) findViewById(R.id.customer_address);
        cityName = (EditText) findViewById(R.id.city_name);
        countryName = (EditText) findViewById(R.id.country_name);
        dueDate = (EditText) findViewById(R.id.due_date);
        totalValue = (EditText) findViewById(R.id.total_value);
        button_add = (Button) findViewById(R.id.btn_add);
        ImageView datePicker = (ImageView) findViewById(R.id.date_picker);

        //Calender instance
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        // Order from Intent data
        final Intent intent = getIntent();
        final Order data = (Order) intent.getParcelableExtra(Constants.BUNDLE_DATA);
        if(data != null){
            updateData(data);
            isUpdate = true;
            orderId.setEnabled(false);
        }

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        // Date picker
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(OrderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dueDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day).show();
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OrderRepository orderRepository = new OrderRepository();
                // Validation check for a User Input
                final String postOrderNumber = orderId.getText().toString().trim();
                String postName = cutomerName.getText().toString().trim();
                String postPhoneNumber = phoneNumber.getText().toString().trim();
                String postAddress = address.getText().toString().trim();
                String postCity = cityName.getText().toString().trim();
                String postCountry = countryName.getText().toString().trim();
                String postDueDate = dueDate.getText().toString().trim();
                String postTotalValue = totalValue.getText().toString().trim();

                if(!(validationCheck(postOrderNumber) && validationCheck(postName) &&
                        validationCheck(postPhoneNumber) && validationCheck(postAddress) &&
                        validationCheck(postCity) && validationCheck(postCountry) &&
                        validationCheck(postDueDate) && validationCheck(postTotalValue))) {
                    return;
                }

                // Creating assigning Order object and iserting into Firebase repository
                Order order = new Order(postOrderNumber,
                        postName,
                        postPhoneNumber,
                        postAddress,
                        postCity,
                        postCountry,
                        postDueDate,
                        postTotalValue);


                // Start loading animation
                DialogUtils.getINSTANCE().dismissProgressDialog();

                if(isUpdate){
                    // update order
                    updateOrder(order,orderRepository);
                }else{
                    // insert order
                    addorder(order,orderRepository);
                }


            }
        });

    }

    /**
     * To insert new orders in the Firebase
     * @param order
     * @param orderRepository
     */
    private void addorder(Order order, OrderRepository orderRepository){
        orderRepository.addOrder(order,
                new OrderRepository.CompletionInterface() {
                    @Override
                    public void taskCompleted() {
                        DialogUtils.getINSTANCE().dismissProgressDialog();
                        DialogUtils.getINSTANCE().displayToastMessage(getApplicationContext(),getResources().getString(R.string.order_inserted_successfully));
                        finish();
                    }

                    @Override
                    public void taskCompleted(ArrayList<Order> orders) {
                        // Do Nothing
                    }

                    @Override
                    public void failed() {
                        //Stop loading and display error
                        DialogUtils.getINSTANCE().dismissProgressDialog();
                        DialogUtils.getINSTANCE().displayToastMessage(getApplicationContext(),getResources().getString(R.string.please_try_again));
                    }
                });
    }

    /**
     * To update existing orders in the Firebase
     * @param order
     * @param orderRepository
     */
    private void updateOrder(Order order,OrderRepository orderRepository){
        orderRepository.updateOrder(order, new OrderRepository.CompletionInterface() {
            @Override
            public void taskCompleted() {
                DialogUtils.getINSTANCE().dismissProgressDialog();
                DialogUtils.getINSTANCE().displayToastMessage(getApplicationContext(),getResources().getString(R.string.order_updated_successfully));
                finish();
            }

            @Override
            public void taskCompleted(ArrayList<Order> orders) {
                // Do Nothing
            }

            @Override
            public void failed() {
                DialogUtils.getINSTANCE().displayToastMessage(getApplicationContext(),getResources().getString(R.string.operation_failed));
            }
        });
    }


    /**
     * Assign data to views when user clicks on Edit
     * @param data
     */
    private void updateData(Order data) {
        orderId.setText(data.getOrderId());
        cutomerName.setText(data.getCustomerName());
        phoneNumber.setText(data.getCustomerPhoneNumber());
        address.setText(data.getCustomerAddress());
        cityName.setText(data.getCustomerCity());
        countryName.setText(data.getCustomerCountry());
        dueDate.setText(data.getDueDate());
        totalValue.setText(data.getTotalValue());
    }

    /**
     * Validation for views
     * @param fieldText
     * @return
     */
    private boolean validationCheck(String fieldText){
        if(fieldText == null || fieldText.isEmpty() || fieldText.equals("")){
            DialogUtils.getINSTANCE().displayToastMessage(this,getResources().getString(R.string.please_enter_valid_input));
            return false;
        }
        return true;
    }
}
