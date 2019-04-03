package com.example.sofra.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.example.sofra.ui.fragments.CompletRestaurantOrderFragment;
import com.example.sofra.ui.fragments.CurrentRestaurantOrderFragment;
import com.example.sofra.ui.fragments.MenuEditFragment;
import com.example.sofra.ui.fragments.NewRestaurantOrderFragment;
import com.example.sofra.ui.fragments.RestaurantDetailsEditFragment;
import com.example.sofra.ui.fragments.RestaurantReviewsEditFragment;

import java.util.ArrayList;
import java.util.List;

public class TapLayoutAdapterEdit extends FragmentPagerAdapter {
    private int id ;


    public TapLayoutAdapterEdit(FragmentManager fm , int id ) {
        super(fm);
        this.id = id;

    }

    @Override
    public Fragment getItem(int pos) {

        if (id==1){
            switch (pos){

                case 0:
                    return new MenuEditFragment();
                case 1:
                    return new RestaurantReviewsEditFragment();
                case 2 :
                    return  new RestaurantDetailsEditFragment();
                default:
                    return null;
            }
        }else {
            switch (pos){

                case 0:
                    return new NewRestaurantOrderFragment();
                case 1:
                    return new CurrentRestaurantOrderFragment();
                case 2 :
                    return  new CompletRestaurantOrderFragment();
                default:
                    return null;
            }
        }

    }
    @Override
    public int getCount() {
        return 3;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (id==1){
            switch (position){
                case 0:
                    return "قائمة الطعام";
                case 1:
                    return "التعليقات و التقييم";
                case 2 :
                    return  "معلومات المتجر";

            }
           // return super.getPageTitle(position);
        }else {
            switch (position){
                case 0:
                    return "الطلبات الجديدة";
                case 1:
                    return "الطلبات الحالية";
                case 2 :
                    return  "الطلبات السابقة";

        }



    }
        return super.getPageTitle(position);
}
    }