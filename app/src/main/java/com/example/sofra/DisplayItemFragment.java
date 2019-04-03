package com.example.sofra;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.room.AppDatabase;
import com.example.sofra.model.room.RoomCartModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.helper.HelperMethod.replaceFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayItemFragment extends Fragment {


    @BindView(R.id.Display_item_img)
    ImageView DisplayItemImg;
    @BindView(R.id.Display_item_tv_name)
    TextView DisplayItemTvName;
    @BindView(R.id.Display_item_tv_desc)
    TextView DisplayItemTvDesc;
    @BindView(R.id.Display_item_tv_price)
    TextView DisplayItemTvPrice;
    @BindView(R.id.Display_item_tv_wait)
    TextView DisplayItemTvWait;
    @BindView(R.id.Display_item_tv_special_order)
    TextView DisplayItemTvSpecialOrder;
    @BindView(R.id.Display_item_et_special_order)
    EditText DisplayItemEtSpecialOrder;
    @BindView(R.id.Display_item_tv_count)
    TextView DisplayItemTvCount;
    @BindView(R.id.Display_item_btn_minus)
    Button DisplayItemBtnMinus;
    @BindView(R.id.Display_item_tv_Display_count)
    TextView DisplayItemTvDisplayCount;
    @BindView(R.id.Display_item_btn_plus)
    Button DisplayItemBtnPlus;
    @BindView(R.id.Display_item_btn_addTo_cart)
    Button DisplayItemBtnAddToCart;
    Unbinder unbinder;
    private ApiServices apiServices;
    private String display_photo;
    private String display_desc;
    private String display_wait;
    private String display_name;
    private String display_price;
    int quantity;
    private String api_token_clint;
    private long display_id;
    private List<RoomCartModel> listOfdataCart;
    private String id_restaurant;

    public DisplayItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getClint().create(ApiServices.class);
        getdata();
        setData(display_photo, display_desc, display_name, display_price, display_wait);
        api_token_clint = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_clint");


        return view;
    }

    private void getdata() {
        display_id = SharedPreferencesManger.LoadLongData(getActivity(), "display_id");
        display_photo = SharedPreferencesManger.LoadStringData(getActivity(), "display_photo");
        display_desc = SharedPreferencesManger.LoadStringData(getActivity(), "display_desc");
        display_wait = SharedPreferencesManger.LoadStringData(getActivity(), "display_wait");
        display_price = SharedPreferencesManger.LoadStringData(getActivity(), "display_price");
        display_name = SharedPreferencesManger.LoadStringData(getActivity(), "display_name");
        id_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "id_restaurant");

    }

    private void setData(String display_photo, String display_desc, String display_name, String display_price, String display_wait) {

        DisplayItemTvDesc.setText(display_desc);
        DisplayItemTvName.setText(display_name);
        DisplayItemTvPrice.setText(" السعر\n" + display_price);
        DisplayItemTvWait.setText("وقت التحضير: " + display_wait);
        Glide.with(getContext()).load(display_photo).into(DisplayItemImg);
    }

    private void saveDataToRoom() {

        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class,
                "db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        RoomCartModel cartModel = new RoomCartModel(display_photo,
        DisplayItemTvDisplayCount.getText().toString(),
       display_price, DisplayItemTvName.getText().toString(),id_restaurant);
        db.cartDeo().insertAll(cartModel);
        replaceFragment(new OrderCartFragment(), R.id.Home_replace_fragments,
                getFragmentManager().beginTransaction());
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Display_item_btn_minus, R.id.Display_item_btn_plus, R.id.Display_item_btn_addTo_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Display_item_btn_minus:
                if (quantity == 0) {
                    return;
                }
                quantity = quantity - 1;
                displayQuantity(quantity);

                break;
            case R.id.Display_item_btn_plus:
                if (quantity == 50) {
                    return;
                }
                quantity = quantity + 1;
                displayQuantity(quantity);
                break;
            case R.id.Display_item_btn_addTo_cart:
                saveDataToRoom();
                break;
        }
    }


    private void displayQuantity(int quantity) {
        DisplayItemTvDisplayCount.setText("" + quantity);

    }



}
