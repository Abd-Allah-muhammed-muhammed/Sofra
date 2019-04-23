package com.example.sofra.ui.fragments.login;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.categories.Categories;
import com.example.sofra.model.categories.CategoriesDatum;
import com.example.sofra.model.general.list_of_cities.ListOfCities;
import com.example.sofra.model.general.list_of_cities.ListOfCitiesDatum;
import com.example.sofra.model.general.list_of_cities.list_of_region.ListOfRegion;
import com.example.sofra.model.general.list_of_cities.list_of_region.ListOfRegionDatum;
import com.example.sofra.model.restaurant_register.RestaurantRegestr;
import com.example.sofra.ui.fragments.restaurant.food_item.Add_new_productFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

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
import static com.example.sofra.helper.HelperMethod.hidView;
import static com.example.sofra.helper.HelperMethod.openAlbum;
import static com.example.sofra.helper.HelperMethod.replaceFragment;
import static com.example.sofra.helper.HelperMethod.showView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistreResturantFragment extends Fragment {

    @BindView(R.id.layout_register_one)
    RelativeLayout layoutRegisterOne;
    @BindView(R.id.layout_register_two)
    RelativeLayout layoutRegisternext;
    Unbinder unbinder;

    @BindView(R.id.rest_name_et)
    EditText restNameEt;
    @BindView(R.id.rest_City_sp)
    Spinner restCitySp;
    @BindView(R.id.Layout_city)
    RelativeLayout LayoutCity;
    @BindView(R.id.rest_region_sp)
    Spinner restRegionSp;
    @BindView(R.id.Layout_region)
    RelativeLayout LayoutRegion;
    @BindView(R.id.rest_mail_et)
    EditText restMailEt;
    @BindView(R.id.rest_pass_et)
    EditText restPassEt;
    @BindView(R.id.rest_active_pass_et)
    EditText restActivePassEt;
    @BindView(R.id.rest_types_sp)
    com.example.sofra.helper.MultiSelectionSpinner restTypesSp;
    @BindView(R.id.Layout2_types)
    RelativeLayout Layout2Types;
    @BindView(R.id.rest_Minimum_Order_et)
    EditText restMinimumOrderEt;
    @BindView(R.id.rest_delivery_fee_et)
    EditText restDeliveryFeeEt;
    @BindView(R.id.info_commenecation)
    TextView infoCommenecation;
    @BindView(R.id.rest_phon)
    EditText restPhon;
    @BindView(R.id.rest_whats)
    EditText restWhats;
    @BindView(R.id.layout_info_commenecation)
    RelativeLayout layoutInfoCommenecation;
    @BindView(R.id.rest_next)
    Button restNext;
    @BindView(R.id.rest_register)
    Button restRegister;
    @BindView(R.id.rest_img)
    ImageView restImg;
    private int REQUEST_GALLERY = 12;
    ApiServices services;
    private long REGION_ID;
    private long ID_CATOGERIES;
    public static final String TAG = "tag";
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();

    private List<CategoriesDatum> data;
    private AlertDialog alertDialog;


    public RegistreResturantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_registre_resturant, container, false);
        unbinder = ButterKnife.bind(this, view);
        showView(layoutRegisterOne);
        hidView(layoutRegisternext);
        services = getClint().create(ApiServices.class);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();

        getCity();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.rest_next, R.id.rest_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rest_next:
                layoutRegisterOne.setVisibility(View.INVISIBLE);
                layoutRegisternext.setVisibility(View.VISIBLE);
                getCatogries();
                break;
            case R.id.rest_register:
                alertDialog.show();
                restaurantRegister();
                Log.d(TAG, "onViewClicked: " + restNameEt.getText().toString());
                break;
        }
    }

    private void restaurantRegister() {
        String name = viewR(restNameEt);
        final String mail = viewR(restMailEt);
        String pass = viewR(restPassEt);
        String rPass = viewR(restActivePassEt);
        String delivery_cost = viewR(restDeliveryFeeEt);
        String MinimumOrderEt = viewR(restMinimumOrderEt);
        String phone = viewR(restPhon);
        String whats = viewR(restWhats);


        MultipartBody.Part partPhoto = convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo");
        //  MultipartBody.Part photo = HelperMethod.convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo");


        RequestBody requestBody_name = convertToRequestBody(name);
        RequestBody requestBody_mail = convertToRequestBody(mail);
        RequestBody requestBody_pass = convertToRequestBody(pass);
        RequestBody requestBody_r_pass = convertToRequestBody(rPass);
        RequestBody requestBody_delivery_cost = convertToRequestBody(delivery_cost);
        RequestBody requestBody_MinimumOrderEt = convertToRequestBody(MinimumOrderEt);
        RequestBody requestBody_phone = convertToRequestBody(phone);
        RequestBody requestBody_whats = convertToRequestBody(whats);
        RequestBody requestBody_region_id = convertToRequestBody(String.valueOf(REGION_ID));

        List<String> selectedStrings = restTypesSp.getSelectedStrings();
        List<String> listselected = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (selectedStrings.contains(data.get(i).getName())) {
                listselected.add(data.get(i).getId().toString());
            }
        }
        List<RequestBody> requestBody_listCatogries = new ArrayList<>();
        for (int i = 0; i < listselected.size(); i++) {
            requestBody_listCatogries.add(convertToRequestBody(listselected.get(i)));

            Log.d(TAG, "list: " + requestBody_listCatogries.toArray().toString());
        }

        services.restaurantRegister(requestBody_name,requestBody_mail,requestBody_pass,requestBody_r_pass
        ,requestBody_phone,requestBody_whats,requestBody_region_id,requestBody_listCatogries,requestBody_delivery_cost
        ,requestBody_MinimumOrderEt,partPhoto,convertToRequestBody("open")).enqueue(new Callback<RestaurantRegestr>() {
            @Override
            public void onResponse(Call<RestaurantRegestr> call, Response<RestaurantRegestr> response) {

                if (response.body().getStatus()==1) {
                    alertDialog.cancel();
                    Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    String apiToken = response.body().getData().getApiToken();
                    SharedPreferencesManger.SaveData(getActivity(),"api_token_restaurant_new_acc",apiToken);
replaceFragment(new Add_new_productFragment() , R.id.Home_replace_fragments, getActivity().getSupportFragmentManager().beginTransaction());
                }else {
                    Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<RestaurantRegestr> call, Throwable t) {
alertDialog.cancel();
            }
        });

    }

    private String viewR(EditText restMailEt) {

        return restMailEt.getText().toString();
    }


    @OnClick(R.id.rest_img)
    public void onViewClicked() {

        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(@NonNull ArrayList<AlbumFile> result) {

                ImagesFiles.clear();
                ImagesFiles.addAll(result);

                Glide.with(getContext()).load(ImagesFiles.get(0).getPath()).into(restImg);


            }
        };

        openAlbum(3, getActivity(), ImagesFiles, action);


    }


    private void getCatogries() {
        alertDialog.show();
        services.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                Long status = response.body().getStatus();
                if (status==1) {
                    alertDialog.cancel();
                    data = response.body().getData();
                    List<String> listnames = new ArrayList<>();
                    final List<Integer> listIds = new ArrayList<>();
                    listnames.add("التصنيفات");
                    for (int i = 0; i < data.size(); i++) {
                        String name = data.get(i).getName();
                        int idCato = data.get(i).getId();
                        listnames.add(name);
                        listIds.add(idCato);

                    }
                    restTypesSp.setItems(listnames);

                }else {
                    alertDialog.cancel();
                }


            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
alertDialog.cancel();
            }
        });
    }


    private void getCity() {
        services.getCities().enqueue(new Callback<ListOfCities>() {
            @Override
            public void onResponse(Call<ListOfCities> call, Response<ListOfCities> response) {
                List<ListOfCitiesDatum> data = response.body().getData().getData();
                //2  lists  to save names and ids of cities
                List<String> listCitiesNames = new ArrayList<>();
                final List<Long> listCitiesIds = new ArrayList<>();
                listCitiesNames.add("المدينة");
                listCitiesIds.add((long) 0);
                for (int i = 0; i < data.size(); i++) {
                    String cityName = data.get(i).getName();
                    Long cityId = data.get(i).getId();

                    // set the names and ids to lists
                    listCitiesNames.add(cityName);
                    listCitiesIds.add(cityId);

                }


                // set Adapter spinner
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, listCitiesNames);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                restCitySp.setAdapter(spinnerAdapter);
                restCitySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        setRegion(listCitiesIds.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ListOfCities> call, Throwable t) {

            }
        });

    }

    private void setRegion(Long cityId) {

        services.getRegion(cityId).enqueue(new Callback<ListOfRegion>() {
            @Override
            public void onResponse(Call<ListOfRegion> call, Response<ListOfRegion> response) {
                List<ListOfRegionDatum> data = response.body().getData().getData();
                List<String> listRegionNames = new ArrayList<>();
                final List<Long> listRegionIds = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    String regionName = data.get(i).getName();
                    Long regionId = data.get(i).getId();
                    listRegionNames.add(regionName);
                    listRegionIds.add(regionId);

                }

                // set adapter
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item, listRegionNames);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                restRegionSp.setAdapter(spinnerAdapter);
                restRegionSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        REGION_ID = listRegionIds.get(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onFailure(Call<ListOfRegion> call, Throwable t) {

            }
        });


    }


}
