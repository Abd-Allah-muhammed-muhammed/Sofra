package com.example.sofra.ui.fragments.general;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.adapter.TapLayoutAdapter;
import com.example.sofra.adapter.TapLayoutMyOrderAdapter;
import com.example.sofra.ui.fragments.clint.order.CompletOrderFragment;
import com.example.sofra.ui.fragments.clint.order.CurentOrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderContainerFragment extends Fragment {


    @BindView(R.id.My_Order_tap_Orders)
    TabLayout MyOrderTapOrders;
    @BindView(R.id.My_Order_pager_Orders)
    ViewPager MyOrderPagerOrders;
    Unbinder unbinder;

    public MyOrderContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order_container, container, false);
        unbinder = ButterKnife.bind(this, view);
        MyOrderPagerOrders.setAdapter(new TapLayoutMyOrderAdapter(getActivity().getSupportFragmentManager()));


        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                CurentOrderFragment currentOrderFragment = (CurentOrderFragment) fragment;
                getFragmentManager().beginTransaction().remove( fragment ).commit();
            } catch (Exception e) {

            }
        }
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                CompletOrderFragment completeOrderFragment = (CompletOrderFragment) fragment;
                getFragmentManager().beginTransaction().remove( fragment ).commit();
            } catch (Exception e) {

            }
        }
        MyOrderTapOrders.setupWithViewPager(MyOrderPagerOrders);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
