package com.example.firebaseorders.orders.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseorders.R;
import com.example.firebaseorders.orders.model.Order;
import com.example.firebaseorders.orders.utils.Constants;
import com.example.firebaseorders.orders.ui.SlidingActivity;

import java.util.ArrayList;


/**
 * Author : Rakesh.V
 * Date : 08/08/2019
 * Adapter class to hold and display items in RecyclerView
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private ArrayList<Order> mOrderList;
    private Context mContext;
    EditDeleteOrders editDeleteOrders;

    /**
     * Constructor class
     * @param orderList
     * @param context
     */
    public OrdersAdapter(ArrayList<Order> orderList, Context context) {
        this.mOrderList = orderList;
        this.mContext = context;
        this.editDeleteOrders = (EditDeleteOrders) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.order_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Order data = mOrderList.get(position);
        holder.orderNumber.setText(data.getOrderId());
        holder.customerName.setText(data.getCustomerName());
        holder.customerPhone.setText(data.getCustomerPhoneNumber());
        holder.customerAddress.setText(data.getCustomerAddress());
        holder.customerCity.setText(data.getCustomerCity());
        holder.customerCountry.setText(data.getCustomerCountry());
        holder.duedate.setText(data.getDueDate());
        holder.totalValue.setText(data.getTotalValue());

        // Edit button click listener event to Edit Order
        holder.editOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDeleteOrders.editOrder(data);
            }
        });

        // Delete button click listener event to Delete Order
        holder.deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDeleteOrders.deleteOrder(data);
            }
        });

        // Item click listener ti handle Slider view
        holder.orderCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, SlidingActivity.class)
                        .putExtra(Constants.BUNDLE_ORDER_ID, position));
            }
        });

    }

    /**
     *  To get total number of list items size
     * @return
     */
    @Override
    public int getItemCount() {
        return mOrderList.size();
    }


    /**
     * ViewHolder class to hold values in views.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView orderNumber,customerName,customerPhone,customerAddress,customerCity,customerCountry,duedate,totalValue;
        private ImageView editOrder, deleteOrder;
        private CardView orderCV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.orderCV = (CardView) itemView.findViewById(R.id.orderCV);
            this.orderNumber = (TextView) itemView.findViewById(R.id.order_value);
            this.customerName = (TextView) itemView.findViewById(R.id.name_value);
            this.customerPhone = (TextView) itemView.findViewById(R.id.phone_value);
            this.customerAddress = (TextView) itemView.findViewById(R.id.address_value);
            this.customerCity = (TextView) itemView.findViewById(R.id.city_value);
            this.customerCountry = (TextView) itemView.findViewById(R.id.country_value);
            this.duedate = (TextView) itemView.findViewById(R.id.due_date_value);
            this.totalValue = (TextView) itemView.findViewById(R.id.value);
            this.editOrder = (ImageView) itemView.findViewById(R.id.edit_icon);
            this.deleteOrder = (ImageView) itemView.findViewById(R.id.delete_icon);
        }
    }

    /**
     * Interface to modify orders and update UI
     */
    public interface EditDeleteOrders{
        void editOrder(Order data);
        void deleteOrder(Order data);
    }
}
