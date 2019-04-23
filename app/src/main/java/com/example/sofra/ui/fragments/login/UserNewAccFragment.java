package com.example.sofra.ui.fragments.login;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.client_register.RegisterClint;
import com.example.sofra.model.edit_profile.EditProfile;
import com.example.sofra.model.general.list_of_cities.ListOfCities;
import com.example.sofra.model.general.list_of_cities.ListOfCitiesDatum;
import com.example.sofra.model.general.list_of_cities.list_of_region.ListOfRegion;
import com.example.sofra.model.general.list_of_cities.list_of_region.ListOfRegionDatum;
import com.example.sofra.ui.fragments.clint.order.restaurant.RestaurantFragment;
import com.example.sofra.ui.fragments.general.RestaurantContainerOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.helper.HelperMethod.replaceFragment;
import static com.example.sofra.helper.HelperMethod.showSweetDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserNewAccFragment extends Fragment {


    @BindView(R.id.New_Acc_name_et)
    EditText NewAccNameEt;
    @BindView(R.id.New_Acc_mail_et)
    EditText NewAccMailEt;
    @BindView(R.id.New_Acc_phone_et)
    EditText NewAccPhoneEt;
    @BindView(R.id.New_Acc_City_sp)
    Spinner NewAccCitySp;
    @BindView(R.id.New_Acc_region_sp)
    Spinner NewAccRegionSp;
    @BindView(R.id.New_Acc_address_et)
    EditText NewAccAddressEt;
    @BindView(R.id.New_Acc_pass_et)
    EditText NewAccPassEt;
    @BindView(R.id.New_Acc_active_pass_et)
    EditText NewAccActivePassEt;
    Unbinder unbinder;
    @BindView(R.id.New_Acc_next_btn)
    Button NewAccNextBtn;
    ApiServices services;
    private Long CITY_ID;
    private AlertDialog alertDialog;
    private String api_token_clint;
    private String api_token_restaurant;
    private int userId;
    private String name_clint;
    private String email_clint;
    private String phone_clint;
    private String address_clint;
    private String pass_clint;
    private long id_city_clint;

    public UserNewAccFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user_new_acc, container, false);
        unbinder = ButterKnife.bind(this, view);
        services = getClint().create(ApiServices.class);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();

        userId = getActivity().getIntent().getIntExtra("user_id", 0);
         api_token_clint = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_clint");
         api_token_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_restaurant");


         name_clint = SharedPreferencesManger.LoadStringData(getActivity(), "name_clint");
         email_clint = SharedPreferencesManger.LoadStringData(getActivity(), "email_clint");
         id_city_clint = SharedPreferencesManger.LoadLongData(getActivity(), "id_city_clint");
         phone_clint = SharedPreferencesManger.LoadStringData(getActivity(), "phone_clint");
         address_clint = SharedPreferencesManger.LoadStringData(getActivity(), "address_clint");
         pass_clint = SharedPreferencesManger.LoadStringData(getActivity(), "pass_clint");
         getCity();
        if (userId==1){
           // getCity();
             if (api_token_clint!=null){
                 NewAccNameEt.setText(name_clint);
                 NewAccActivePassEt.setText(pass_clint);
                 NewAccAddressEt.setText(address_clint);
                 NewAccMailEt.setText(email_clint);
                 NewAccPassEt.setText(pass_clint);
                 NewAccPhoneEt.setText(phone_clint);
                 NewAccNextBtn.setText("تعديل");
                 setRegion(id_city_clint);

             }
         }else {

             if (api_token_restaurant!=null){


             }
         }





        return view;
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
                NewAccCitySp.setAdapter(spinnerAdapter);
                NewAccCitySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                NewAccRegionSp.setAdapter(spinnerAdapter);
                NewAccRegionSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CITY_ID = listRegionIds.get(position);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.New_Acc_next_btn)
    public void onViewClicked() {

        if (api_token_clint!=null){

            editeProfile();

        }else {
            alertDialog.show();
            registerClint();
        }


    }

    private void editeProfile() {
        try {


            services.editProfile(api_token_clint, NewAccNameEt.getText().toString(), NewAccPhoneEt.getText().toString()
                    , NewAccMailEt.getText().toString(), NewAccPassEt.getText().toString(),
                    NewAccPassEt.getText().toString(), NewAccAddressEt.getText().toString(), CITY_ID).enqueue(new Callback<EditProfile>() {
                @Override
                public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {

                    String msg = response.body().getMsg();
                    Long status = response.body().getStatus();
                    if (status == 1) {
                        showSweetDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE, "#3C9B27",
                                msg, true);
                        replaceFragment(new RestaurantFragment(), R.id.Home_replace_fragments
                                , getActivity().getSupportFragmentManager().beginTransaction());
                    } else {
                        showSweetDialog(getContext(), SweetAlertDialog.ERROR_TYPE, "#D42D2C",
                                msg, true);
                    }
                }

                @Override
                public void onFailure(Call<EditProfile> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void registerClint() {
        String name = NewAccNameEt.getText().toString();
        String address = NewAccAddressEt.getText().toString();
        String activePass = NewAccActivePassEt.getText().toString();
        String mail = NewAccMailEt.getText().toString();
        String pass = NewAccPassEt.getText().toString();
        String phone = NewAccPhoneEt.getText().toString();

        services.registerClint(name,mail,pass,activePass,phone,address,CITY_ID).enqueue(new Callback<RegisterClint>() {
            @Override
            public void onResponse(Call<RegisterClint> call, Response<RegisterClint> response) {

                if (response.body() != null) {
                    if (response.body().getStatus()==1){
                        alertDialog.cancel();
                        Toast.makeText(getContext(), "successful", Toast.LENGTH_SHORT).show();

                        String apiToken = response.body().getData().getApiToken();
                        SharedPreferencesManger.SaveData(getActivity(),"api_token_clint_new_acc",apiToken);


                    }else {

                        alertDialog.cancel();
                        Toast.makeText(getContext(), "error"+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterClint> call, Throwable t) {

                alertDialog.cancel();
            }
        });
    }
}

// 1728 ريال