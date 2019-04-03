package com.example.sofra.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sofra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class splashActivity extends AppCompatActivity {


    @BindView(R.id.Splash_logo)
    ImageView SplashLogo;
    @BindView(R.id.Splash_text)
    TextView SplashText;
    @BindView(R.id.Splash_order_food)
    Button SplashOrderFood;
    @BindView(R.id.Splash_Sell_Food)
    Button SplashSellFood;
    @BindView(R.id.Splash_Ic_Twitter)
    ImageView SplashIcTwitter;
    @BindView(R.id.Splash_Ic_Inesta)
    ImageView SplashIcInesta;
    @BindView(R.id.Splash_layout_social)
    LinearLayout SplashLayoutSocial;


    private Intent intent;
    private int User_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.Splash_order_food, R.id.Splash_Sell_Food})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Splash_order_food:

                intent = new Intent(this, HomeActivity.class);
                intent.putExtra("user_id",User_id);
                startActivity(intent);
                break;
            case R.id.Splash_Sell_Food:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
