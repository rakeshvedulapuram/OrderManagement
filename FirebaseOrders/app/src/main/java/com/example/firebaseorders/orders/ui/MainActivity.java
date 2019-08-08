package com.example.firebaseorders.orders.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseorders.R;
import com.example.firebaseorders.orders.adapter.OrdersAdapter;
import com.example.firebaseorders.orders.model.Order;
import com.example.firebaseorders.orders.repository.OrderRepository;
import com.example.firebaseorders.orders.utils.Constants;
import com.example.firebaseorders.orders.utils.DialogUtils;

import java.util.ArrayList;

/**
 * Author : Rakesh.V
 * Date : 08/08/2019
 * Display all orders from Firebase
 */
public class  MainActivity extends AppCompatActivity implements OrdersAdapter.EditDeleteOrders {

    private OrderRepository mOrderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final ImageView emptyCart = (ImageView) findViewById(R.id.empty_layout);
        mOrderRepository = new OrderRepository();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ProgressBar initiation
        DialogUtils.getINSTANCE().showProgressDialog(this,getString(R.string.please_wait));

        mOrderRepository.getOrders(new OrderRepository.CompletionInterface() {
            @Override
            public void taskCompleted() {
                // Do Nothing
            }

            @Override
            public void taskCompleted(ArrayList<Order> orders) {
                // Stop loading ProgressBar and show retrieve data from Firebase
                DialogUtils.getINSTANCE().dismissProgressDialog();
                mOrderRepository.setmOrderList(orders);
                OrdersAdapter ordersAdapter = new OrdersAdapter(mOrderRepository.getmOrderList(), MainActivity.this);
                if(mOrderRepository.getmOrderList().size() == 0){
                    emptyCart.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else{
                    emptyCart.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(ordersAdapter);
                }
            }

            @Override
            public void failed() {
                DialogUtils.getINSTANCE().displayToastMessage(getApplicationContext(),getString(R.string.please_try_again));
            }
        });

    }

    /**
     * Edit order functionality
     */
    @Override
    public void editOrder(Order data) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(Constants.BUNDLE_DATA,data);
        startActivity(intent);
    }

    /**
     * Delete order functionality
     * @param data
     */
    @Override
    public void deleteOrder(final Order data) {
        // confirmation dialog to Delete order
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.alert_dilog_title))
                .setMessage(getString(R.string.alert_dilog_message))

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();
                        mOrderRepository.deleteOrder(data, new OrderRepository.CompletionInterface() {
                            @Override
                            public void taskCompleted() {
                                DialogUtils.getINSTANCE().displayToastMessage(getApplicationContext(),getResources().getString(R.string.deleted_successfully));
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
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.btn_add_order:
                    Intent intent = new Intent(this, OrderActivity.class);
                    startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
