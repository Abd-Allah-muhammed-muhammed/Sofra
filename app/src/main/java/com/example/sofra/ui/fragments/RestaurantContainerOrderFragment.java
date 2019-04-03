package com.example.sofra.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.adapter.TapLayoutAdapterEdit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantContainerOrderFragment extends Fragment {


    @BindView(R.id.Restaurant_Container_Order_Tb)
    TabLayout RestaurantContainerOrderTb;
    @BindView(R.id.Restaurant_Container_Order_Rv)
    ViewPager RestaurantContainerOrderVp;
    Unbinder unbinder;

    public RestaurantContainerOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_container_order, container, false);
        unbinder = ButterKnife.bind(this, view);


//        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
//            try {
//                NewRestaurantOrderFragment foodListFragment = (NewRestaurantOrderFragment) fragment;
//
//
//                getFragmentManager().beginTransaction().remove(fragment).commit();
//            }finally {
//
//            }
//
//            try {
//                CurrentRestaurantOrderFragment foodListFragment = (CurrentRestaurantOrderFragment) fragment;
//
//
//                getFragmentManager().beginTransaction().remove(fragment).commit();
//            }finally {
//
//            }
//
//            try {
//                CompletRestaurantOrderFragment foodListFragment = (CompletRestaurantOrderFragment) fragment;
//
//
//                getFragmentManager().beginTransaction().remove(fragment).commit();
//            }finally {
//
//            }
//
//            try {
//
//                MenuEditFragment menuEditFragment = (MenuEditFragment) fragment;
//
//                getFragmentManager().beginTransaction().remove(fragment).commit();
//
//            }finally {
//
//            }
//
//
//            try {
//
//                RestaurantDetailsEditFragment menuEditFragment = (RestaurantDetailsEditFragment) fragment;
//
//                getFragmentManager().beginTransaction().remove(fragment).commit();
//
//            }finally {
//
//            }
//
//
//
//            try {
//
//                RestaurantReviewsEditFragment menuEditFragment = (RestaurantReviewsEditFragment) fragment;
//
//                getFragmentManager().beginTransaction().remove(fragment).commit();
//
//            }finally {
//
//            }




      //  }


//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
//        if(fragment != null)
//            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        RestaurantContainerOrderVp.setAdapter(new TapLayoutAdapterEdit(getActivity().getSupportFragmentManager(),2));

        RestaurantContainerOrderTb.setupWithViewPager(RestaurantContainerOrderVp);




        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
