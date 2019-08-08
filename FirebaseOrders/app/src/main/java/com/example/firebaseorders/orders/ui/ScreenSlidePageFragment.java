package com.example.firebaseorders.orders.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firebaseorders.R;
import com.example.firebaseorders.orders.model.Order;
import com.example.firebaseorders.orders.utils.Constants;

/**
 * Author : Rakesh.V
 * Date : 08/08/2019
 * Slider view for all orders
 */

public class ScreenSlidePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        TextView orderNumber = rootView.findViewById(R.id.order_value);
        TextView customerName = rootView.findViewById(R.id.name_value);
        TextView customerPhone = rootView.findViewById(R.id.phone_value);
        TextView customerAddress = rootView.findViewById(R.id.address_value);
        TextView customerCity = rootView.findViewById(R.id.city_value);
        TextView customerCountry = rootView.findViewById(R.id.country_value);
        TextView duedate = rootView.findViewById(R.id.due_date_value);
        TextView totalValue = rootView.findViewById(R.id.value);

        Order order = getArguments().getParcelable(Constants.BUNDLE_ORDER);

        // Assigning existed values to the Views
        orderNumber.setText(order.getOrderId());
        customerName.setText(order.getCustomerName());
        customerPhone.setText(order.getCustomerPhoneNumber());
        customerAddress.setText(order.getCustomerAddress());
        customerCity.setText(order.getCustomerCity());
        customerCountry.setText(order.getCustomerCountry());
        duedate.setText(order.getDueDate());
        totalValue.setText(order.getTotalValue());
        return rootView;

    }

}