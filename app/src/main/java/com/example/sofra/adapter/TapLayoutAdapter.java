package com.example.sofra.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sofra.ui.fragments.clint.order.MenuFragment;
import com.example.sofra.ui.fragments.clint.order.restaurant.RestaurantDetailsFragment;
import com.example.sofra.ui.fragments.clint.order.restaurant.RestaurantReviewsFragment;

public class TapLayoutAdapter  extends FragmentPagerAdapter {
    public TapLayoutAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos){
            case 0:
                return new MenuFragment();
            case 1:
                return new RestaurantReviewsFragment();
                case 2 :
                    return  new RestaurantDetailsFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "قائمة الطعام";
            case 1:
                return "التعليقات و التقييم";
            case 2 :
                return  "معلومات المتجر";
        }

        return super.getPageTitle(position);
    }
}