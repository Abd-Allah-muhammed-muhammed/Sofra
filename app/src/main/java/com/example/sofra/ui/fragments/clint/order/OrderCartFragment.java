package com.example.sofra.ui.fragments.clint.order;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.adapter.AdapterRoomCart;
import com.example.sofra.model.room.AppDatabase;
import com.example.sofra.model.room.RoomCartModel;
import com.example.sofra.ui.fragments.clint.order.CompleteOrderClitnFragment;
import com.example.sofra.ui.fragments.clint.order.restaurant.RestaurantFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.sofra.helper.HelperMethod.replaceFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderCartFragment extends Fragment {


    @BindView(R.id.Order_Cart_Rv)
    RecyclerView OrderCartRv;
    Unbinder unbinder;
    List<RoomCartModel> listOfCart = new ArrayList<>();
    @BindView(R.id.OrderCart_TV_total)
    TextView OrderCartTVTotal;
    @BindView(R.id.OrderCart_btn_Make_request)
    Button OrderCartBtnMakeRequest;
    @BindView(R.id.OrderCart_btn_AddMoor)
    Button OrderCartBtnAddMoor;
    @BindView(R.id.Order_Cart_layout)
    RelativeLayout OrderCartLayout;

    public OrderCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_order_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        AdapterRoomCart adapterRoomCart = new AdapterRoomCart(getContext(), listOfCart, getActivity(), OrderCartTVTotal);
        OrderCartRv.setLayoutManager(new LinearLayoutManager(getContext()));

        OrderCartRv.setAdapter(adapterRoomCart);
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "db").allowMainThreadQueries()
                .build();
        List<RoomCartModel> allData = db.cartDeo().getAllData();

        listOfCart.addAll(allData);
//       float totalAll =  AdapterRoomCart.totalAll;
//       OrderCartTVTotal.setText(""+totalAll);

        adapterRoomCart.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.OrderCart_btn_Make_request, R.id.OrderCart_btn_AddMoor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.OrderCart_btn_Make_request:
                replaceFragment(new CompleteOrderClitnFragment(),R.id.Home_replace_fragments,getActivity().getSupportFragmentManager().beginTransaction());
                break;
            case R.id.OrderCart_btn_AddMoor:
                replaceFragment(new RestaurantFragment(), R.id.Home_replace_fragments,getActivity().getSupportFragmentManager().beginTransaction());
                break;
        }
    }
}
