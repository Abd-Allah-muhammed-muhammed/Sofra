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
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.api.ApiServices;
import com.example.sofra.model.notifications.NotificationClint;
import com.example.sofra.ui.fragments.clint.notifications.NotificationsClintFragment;
import com.example.sofra.ui.fragments.login.UserNewAccFragment;
import com.example.sofra.ui.fragments.restaurant.food_item.MyProductFragment;
import com.example.sofra.ui.fragments.clint.order.OrderCartFragment;
import com.example.sofra.R;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.ui.fragments.clint.order.ClintOffersFragment;
import com.example.sofra.ui.fragments.login.LoginFragment;
import com.example.sofra.ui.fragments.restaurant.offers.MyOffersRestFragment;
import com.example.sofra.ui.fragments.general.MyOrderContainerFragment;
import com.example.sofra.ui.fragments.general.RestaurantContainerOrderFragment;
import com.example.sofra.ui.fragments.clint.order.restaurant.RestaurantFragment;
import com.example.sofra.ui.fragments.restaurant.RestaurantsdetialsAndInfoEditFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.inflate;
import static com.example.sofra.api.RetrofitClient.getClint;
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
    private ApiServices apiServices;
    private String refreshedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        manger = getSupportFragmentManager();
        api_token_clint = SharedPreferencesManger.LoadStringData(HomeActivity.this, "api_token_clint");
        api_token_restaurant = SharedPreferencesManger.LoadStringData(HomeActivity.this, "api_token_restaurant");

        user_id = getIntent().getIntExtra("user_id", 0);
        HomeTvTitle.setText("الرئيسية");
        apiServices = getClint().create(ApiServices.class);

        refreshedToken = FirebaseInstanceId.getInstance().getToken();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);



        if (user_id == 1) {
            navigationView.inflateMenu(R.menu.activity_home_drawer_clint);
            pushNotifications();
           // navigationView.inflateMenu(R.menu.activity_home_drawer_clint);
        } else {
            navigationView.inflateMenu(R.menu.activity_home_drawer);
        }

        navigationView.getHeaderView(0).findViewById(R.id.icon_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (user_id == 1) {


                    if (api_token_clint != null) {
                        replaceFragment(new UserNewAccFragment(), R.id.Home_replace_fragments, manger.beginTransaction());
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        replaceFragment(new LoginFragment(), R.id.Home_replace_fragments, manger.beginTransaction());
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    }
                } else {
                    if (api_token_restaurant != null) {
                        // replaceFragment(new );
                    } else {
                        replaceFragment(new LoginFragment(), R.id.Home_replace_fragments, manger.beginTransaction());
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }

            }

        });


        String name_clint = SharedPreferencesManger.LoadStringData(HomeActivity.this, "name_clint");

        if (user_id == 1) {
            replaceFragment(new RestaurantFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

            if (api_token_clint != null) {
                TextView nameClint = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
                nameClint.setText(name_clint);
            }
        } else {

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

    private void pushNotifications() {

        apiServices.pushClintNotifications(refreshedToken, "android", api_token_clint).enqueue(new Callback<NotificationClint>() {
            @Override
            public void onResponse(Call<NotificationClint> call, Response<NotificationClint> response) {
                String msg = response.body().getMsg();
                Long status = response.body().getStatus();
                if (status == 1) {

                } else {


                }
            }

            @Override
            public void onFailure(Call<NotificationClint> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

        } else if (id == R.id.nav_home_clint) {


        } else if (id == R.id.nav_My_order_clint) {
            HomeTvTitle.setText("طلباتي");
            if (api_token_clint == null) {
                replaceFragment(new LoginFragment(), R.id.Home_replace_fragments, manger.beginTransaction());
            } else {
                replaceFragment(new MyOrderContainerFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

            }
        } else if (id == R.id.nav_My_products) {

            HomeTvTitle.setText("منتجاتي");
            replaceFragment(new MyProductFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

        } else if (id == R.id.nav_order_submitted) {

            HomeTvTitle.setText("الطلبات المقدمة");
            replaceFragment(new RestaurantContainerOrderFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

        } else if (id == R.id.nav_Commission) {

        } else if (id == R.id.nav_new_offers_clint) {
            replaceFragment(new ClintOffersFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

        } else if (id == R.id.nav_my_offer) {

            replaceFragment(new MyOffersRestFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

        } else if (id == R.id.nav_about_app) {


        }else if (id==R.id.nav_logout_clint){

            SharedPreferencesManger.clean(HomeActivity.this);
            finish();
        } else if (id == R.id.nav_logout) {

            SharedPreferencesManger.clean(HomeActivity.this);
            removeToken();
            finish();
        }else if (id==R.id.nav_order_notifications_clint){

            replaceFragment(new NotificationsClintFragment(),R.id.Home_replace_fragments,manger.beginTransaction());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void removeToken() {
        apiServices.removClintNotifications(refreshedToken,api_token_clint).enqueue(new Callback<NotificationClint>() {
            @Override
            public void onResponse(Call<NotificationClint> call, Response<NotificationClint> response) {

            }

            @Override
            public void onFailure(Call<NotificationClint> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.Home_img_cart)
    public void onViewClicked() {
        HomeTvTitle.setText("سلة الطلبات");
        replaceFragment(new OrderCartFragment(), R.id.Home_replace_fragments, manger.beginTransaction());
    }


}
