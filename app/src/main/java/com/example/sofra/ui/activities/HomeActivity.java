package com.example.sofra.ui.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sofra.MyProductFragment;
import com.example.sofra.OrderCartFragment;
import com.example.sofra.R;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.ui.fragments.ClintOffersFragment;
import com.example.sofra.ui.fragments.LoginFragment;
import com.example.sofra.ui.fragments.MyOffersRestFragment;
import com.example.sofra.ui.fragments.MyOrderContainerFragment;
import com.example.sofra.ui.fragments.RestaurantContainerOrderFragment;
import com.example.sofra.ui.fragments.RestaurantFragment;
import com.example.sofra.ui.fragments.RestaurantsdetialsAndInfoEditFragment;
import com.rey.material.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.helper.HelperMethod.replaceFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.Home_tv_title)
    TextView HomeTvTitle;
    @BindView(R.id.Home_img_cart)
    ImageView HomeImgCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.Home_replace_fragments)
    FrameLayout HomeReplaceFragments;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private FragmentManager manger;
    private int user_id;
    private String api_token_clint;
    private String api_token_restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manger = getSupportFragmentManager();

        user_id = getIntent().getIntExtra("user_id", 0);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getHeaderView(0).findViewById(R.id.icon_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new LoginFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }

        });


        String name_clint = SharedPreferencesManger.LoadStringData(HomeActivity.this, "name_clint");
        api_token_clint = SharedPreferencesManger.LoadStringData(HomeActivity.this, "api_token_clint");
        if (user_id == 1) {
            replaceFragment(new RestaurantFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

            if (api_token_clint != null) {
                TextView nameClint = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
                nameClint.setText(name_clint);
            }
        } else {
            api_token_restaurant = SharedPreferencesManger.LoadStringData(HomeActivity.this, "api_token_restaurant");

            if (api_token_restaurant != null) {
                replaceFragment(new RestaurantsdetialsAndInfoEditFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

                String name_restaurant = SharedPreferencesManger.LoadStringData(HomeActivity.this, "name_restaurant");
                TextView nameClint = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
                nameClint.setText(name_restaurant);
            } else {
                replaceFragment(new LoginFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

            }
        }

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_My_products) {
            if (user_id == 1) {
                if (api_token_clint == null) {
                    replaceFragment(new LoginFragment(), R.id.Home_replace_fragments, manger.beginTransaction());
                } else {
                    replaceFragment(new MyOrderContainerFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

                }
            } else {

                replaceFragment(new MyProductFragment(), R.id.Home_replace_fragments, manger.beginTransaction());
            }


        } else if (id == R.id.nav_order_submitted) {

            if (user_id == 1) {

            } else {
                replaceFragment(new RestaurantContainerOrderFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

            }

        } else if (id == R.id.nav_Commission) {

        } else if (id == R.id.nav_my_offer) {

            if (user_id == 1) {

replaceFragment(new ClintOffersFragment() , R.id.Home_replace_fragments,manger.beginTransaction());
            } else {
                replaceFragment(new MyOffersRestFragment(), R.id.Home_replace_fragments, manger.beginTransaction());
            }
        } else if (id == R.id.nav_about_app) {


        } else if (id == R.id.nav_logout) {

            SharedPreferencesManger.clean(HomeActivity.this);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @OnClick(R.id.Home_img_cart)
    public void onViewClicked() {
        HomeTvTitle.setText("سلة الطلبات");
        replaceFragment(new OrderCartFragment(),R.id.Home_replace_fragments,manger.beginTransaction());
    }
}
