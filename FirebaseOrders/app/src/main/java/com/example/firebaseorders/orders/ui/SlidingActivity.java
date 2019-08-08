package com.example.firebaseorders.orders.ui;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.firebaseorders.R;
import com.example.firebaseorders.orders.model.Order;
import com.example.firebaseorders.orders.repository.OrderRepository;
import com.example.firebaseorders.orders.utils.Constants;
import com.example.firebaseorders.orders.utils.DialogUtils;

import java.util.ArrayList;

/**
 * Author : Rakesh.V
 * Date : 08/08/2019
 * Slider view activity implementation
 */
public class SlidingActivity extends AppCompatActivity {
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private PageListener page_listener;
    private  int currentPage;
    int selected_index;

    private OrderRepository mOrderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);
        selected_index  = getIntent().getIntExtra(Constants.BUNDLE_ORDER_ID, 0);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mOrderRepository = new OrderRepository();
        mOrderRepository.getOrders(new OrderRepository.CompletionInterface() {
            @Override
            public void taskCompleted() {

            }

            @Override
            public void taskCompleted(ArrayList<Order> orders) {
                DialogUtils.getINSTANCE().displayToastMessage(getApplicationContext(),getResources().getString(R.string.swipe_left_and_right_to_check_orders));
                mOrderRepository.setmOrderList(orders);
                mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), mOrderRepository.getmOrderList());
                mPager.setAdapter(mPagerAdapter);

                page_listener = new PageListener();
                mPager.setOnPageChangeListener(page_listener);
                mPager.setCurrentItem(selected_index);

                mPager.setClipToPadding(false);
                mPager.setPadding(50,0, 50,0);

            }

            @Override
            public void failed() {

            }
        });

    }

    /**
     * Adapter class implementation
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Order> orders;
        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<Order> orders) {
            super(fm);
            this.orders = orders;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.BUNDLE_ORDER, orders.get(position));
            // set Fragmentclass Arguments
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return orders.size();
        }
    }

    /**
     * Page listeners added
     */
    private class PageListener extends ViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {
            currentPage = position;
        }
    }

}