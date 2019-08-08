package com.example.firebaseorders.orders.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebaseorders.orders.model.Order;
import com.example.firebaseorders.orders.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Author : Rakesh.V
 * Date : 08/08/2019
 * Repository class implementation
 */
public class OrderRepository {

    private DatabaseReference mDatabaseReference;
    private ArrayList<Order> mOrderList;


    /**
     * To get total orders list from Firebase
     * @return
     */
    public ArrayList<Order> getmOrderList() {
        return mOrderList;
    }

    /**
     * To set order list in view
     * @param mOrderList
     */
    public void setmOrderList(ArrayList<Order> mOrderList) {
        this.mOrderList = mOrderList;
    }


    /**
     * Constructor for class
     */
    public OrderRepository() {
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.USER);
        this.mOrderList = new ArrayList<>();
    }


    /**
     * To Insert new Order to Firebase
     * @param order
     * @param completionInterface
     */

    public void addOrder(Order order, final CompletionInterface completionInterface) {
        mDatabaseReference.child(order.getOrderId())
                .setValue(new Order(order.getOrderId(),
                        order.getCustomerName(),
                        order.getCustomerPhoneNumber(),
                        order.getCustomerAddress(),
                        order.getCustomerCity(),
                        order.getCustomerCountry(),
                        order.getDueDate(),
                        order.getTotalValue()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            completionInterface.taskCompleted();
                        } else {
                            completionInterface.failed();
                        }
                    }
                });
    }

    /**
     * To Update existing order
     * @param order
     * @param completionInterface
     */
    public void updateOrder(Order order, final CompletionInterface completionInterface) {
        mDatabaseReference.child(order.getOrderId())
                .updateChildren(order.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError == null){
                            completionInterface.taskCompleted();
                        }else{
                            throw databaseError.toException();
                        }
                    }
                });
    }

    /**
     * To Delete existing Order
     * @param order
     * @param completionInterface
     */
    public void deleteOrder(Order order, final CompletionInterface completionInterface) {
        mDatabaseReference.child(order.getOrderId())
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError == null){
                            completionInterface.taskCompleted();
                        }else{
                            throw databaseError.toException();
                        }
                    }
                });
    }

    /**
     * To get All existing orders from Firebase
     * @param completionInterface
     */
    public void getOrders(final CompletionInterface completionInterface) {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(mOrderList != null && !mOrderList.isEmpty()){
                    mOrderList.clear();
                }
                for(DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    if(messageSnapshot != null) {
                        Order order = messageSnapshot.getValue(Order.class);
                        mOrderList.add(order);
                    }
                }
                completionInterface.taskCompleted(mOrderList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Do something about the error
                completionInterface.failed();
            }
        });
    }

    /**
     * Interfaces for Task callbacks
     */
    public interface CompletionInterface {
        void taskCompleted();
        void taskCompleted(ArrayList<Order> orders);
        void failed();
    }
}
