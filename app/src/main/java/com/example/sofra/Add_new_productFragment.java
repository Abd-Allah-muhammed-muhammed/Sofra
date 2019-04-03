package com.example.sofra;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofra.adapter.FoodItemsAdapter;
import com.example.sofra.adapter.FoodItemsAdapterEdit;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.my_items.MyItemsDatum;
import com.example.sofra.model.restaurant_register.RestaurantRegestr;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.BitSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.openAlbum;
import static com.example.sofra.helper.HelperMethod.replaceFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add_new_productFragment extends Fragment {


    @BindView(R.id.Add_product_tv_name)
    EditText AddProductTvName;
    @BindView(R.id.Add_product_tv_dec)
    EditText AddProductTvDec;
    @BindView(R.id.Add_product_tv_price)
    EditText AddProductTvPrice;
    @BindView(R.id.Add_product_tv_waiting)
    EditText AddProductTvWaiting;
    @BindView(R.id.Add_product_img)
    ImageView AddProductImg;
    @BindView(R.id.Add_product_text)
    TextView AddProductText;
    @BindView(R.id.rest_register)
    Button restRegister;
    Unbinder unbinder;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private ApiServices apiServices;
    private String api_token_restaurant;
    private MyItemsDatum data;
    private int id_edit;
    private AlertDialog alertDialog;

    public Add_new_productFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_product, container, false);
        unbinder = ButterKnife.bind(this, view);
         apiServices = getClint().create(ApiServices.class);
         api_token_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_restaurant");
         id_edit = SharedPreferencesManger.LoadIntegerData(getActivity(), "id_edit");
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();

        data = FoodItemsAdapterEdit.data;

if (data!=null&&id_edit==3){
   editProduct();

}else {
    data=null;
}
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Add_product_img, R.id.rest_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Add_product_img:
                sellectPhoto();
                break;
            case R.id.rest_register:
                if (data!=null){
                    Toast.makeText(getContext(), "edit", Toast.LENGTH_SHORT).show();
                    editProduct();

                }else {
                    data=null;
                    alertDialog.show();
                   addProduct();
                }

                break;
        }
    }

    private void editProduct() {
        String name = data.getName();
        String photoUrl = data.getPhotoUrl();
        String preparingTime = data.getPreparingTime();
        String description = data.getDescription();
        String price = data.getPrice();
        AddProductTvWaiting.setText(preparingTime);
        AddProductTvPrice.setText(price);
        AddProductTvDec.setText(description);
        AddProductTvName.setText(name);

    }

    private void addProduct() {
        RequestBody dec = convertToRequestBody(AddProductTvDec.getText().toString());
        RequestBody name = convertToRequestBody(AddProductTvName.getText().toString());
        RequestBody price = convertToRequestBody(AddProductTvPrice.getText().toString());
        RequestBody api_token = convertToRequestBody(api_token_restaurant);

        RequestBody waitting = convertToRequestBody(AddProductTvWaiting.getText().toString());
        MultipartBody.Part partPhoto = convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo");
        apiServices.addNewProduct(dec,price,waitting,name,partPhoto,api_token).enqueue(new Callback<RestaurantRegestr>() {
            @Override
            public void onResponse(Call<RestaurantRegestr> call, Response<RestaurantRegestr> response) {

                String msg = response.body().getMsg();
                Integer status = response.body().getStatus();
                if (status==1){
                    alertDialog.cancel();
                    Toast.makeText(getContext(), ""+msg, Toast.LENGTH_SHORT).show();
                    FragmentManager manager = getFragmentManager();
                    replaceFragment(new MyProductFragment(),R.id.Home_replace_fragments,manager.beginTransaction());
                }else {
                    alertDialog.cancel();
                    Toast.makeText(getContext(), ""+msg, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<RestaurantRegestr> call, Throwable t) {
                alertDialog.cancel();
                Toast.makeText(getContext(), "onFailure"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sellectPhoto() {


        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(@NonNull ArrayList<AlbumFile> result) {

                ImagesFiles.clear();
                ImagesFiles.addAll(result);

                Glide.with(getContext()).load(ImagesFiles.get(0).getPath()).into(AddProductImg);


            }
        };

        openAlbum(3, getActivity(), ImagesFiles, action);


    }
}
