package com.example.sofra.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sofra.ui.fragments.clint.order.CompletOrderFragment;
import com.example.sofra.ui.fragments.clint.order.CurentOrderFragment;

public class TapLayoutMyOrderAdapter extends FragmentPagerAdapter {
    public TapLayoutMyOrderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos){
            case 0:
                return new CurentOrderFragment();
            case 1:
                return new CompletOrderFragment();

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "الطلبات الحاليه";
            case 1:
                return "الطلبات السابقه";

        }

        return super.getPageTitle(position);
    }
}