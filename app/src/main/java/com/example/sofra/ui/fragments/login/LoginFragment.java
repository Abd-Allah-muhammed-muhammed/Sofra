package com.example.sofra.ui.fragments.login;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.Region;
import com.example.sofra.model.client_Login.ClientLogin;
import com.example.sofra.model.restaurant_login.RestaurantLogin;
import com.example.sofra.ui.fragments.clint.order.restaurant.Restaurants_detialsAndInfoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.helper.HelperMethod.replaceFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    ApiServices services;
    @BindView(R.id.Login_mail_et)
    EditText LoginMailEt;
    @BindView(R.id.Login_pass_et)
    EditText LoginPassEt;
    @BindView(R.id.Login_btn)
    Button LoginBtn;
    @BindView(R.id.Login_did_U_Forget_pass)
    TextView LoginDidUForgetPass;
    @BindView(R.id.Login_btn_new_acc)
    Button LoginBtnNewAcc;
    Unbinder unbinder;
    private int user_id;
    private FragmentManager manager;
    private FragmentManager manger;
    private AlertDialog alertDialog;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        manager = getActivity().getSupportFragmentManager();
        services = getClint().create(ApiServices.class);
         user_id = getActivity().getIntent().getIntExtra("user_id", 0);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();

        manager = getFragmentManager();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Login_btn, R.id.Login_did_U_Forget_pass, R.id.Login_btn_new_acc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Login_btn:
                if (user_id == 1) {
                    alertDialog.show();
                    clintLogin();
                } else {
                    alertDialog.show();
                    restaurantLogin();
                }

                break;
            case R.id.Login_did_U_Forget_pass:
                if (user_id==1){
                    Bundle bundle = new Bundle();
                    bundle.putInt("id_forget",5);
                    ForgetPassFragment passFragment = new ForgetPassFragment();
                    passFragment.setArguments(bundle);
                    replaceFragment(passFragment,R.id.Home_replace_fragments,manager.beginTransaction());
                }else {
                    replaceFragment(new ForgetPassFragment(),R.id.Home_replace_fragments,manager.beginTransaction());

                }
                break;
            case R.id.Login_btn_new_acc:
                if (user_id==1){
                    replaceFragment(new UserNewAccFragment(), R.id.Home_replace_fragments, manager.beginTransaction());
                }else {
                    replaceFragment(new RegistreResturantFragment(), R.id.Home_replace_fragments, manager.beginTransaction());


                }

                break;
        }
    }

    private void restaurantLogin() {
        services.restaurantLogin(LoginMailEt.getText().toString(), LoginPassEt.getText().toString()).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                Long status = response.body().getStatus();
                if (status == 1) {
                    alertDialog.cancel();
                    String apiToken = response.body().getData().getApiToken();
                    String name = response.body().getData().getUser().getName();
                    Long id_res = response.body().getData().getUser().getId();
                    SharedPreferencesManger.SaveData(getActivity(),"id_res",id_res);

                    SharedPreferencesManger.SaveData(getActivity(),"api_token_restaurant",apiToken);
                    SharedPreferencesManger.SaveData(getActivity(),"name_restaurant",name);

                    Toast.makeText(getContext(), "restaurant" + response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    replaceFragment(new Restaurants_detialsAndInfoFragment(), R.id.Home_replace_fragments, manger.beginTransaction());

                } else {
                    alertDialog.cancel();
                    Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<RestaurantLogin> call, Throwable t) {
                alertDialog.cancel();
                Toast.makeText(getContext(), "onFailure" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void clintLogin() {

        String mail = LoginMailEt.getText().toString();
        String pass = LoginPassEt.getText().toString();
        services.clientLogin(mail, pass).enqueue(new Callback<ClientLogin>() {
            @Override
            public void onResponse(Call<ClientLogin> call, Response<ClientLogin> response) {
                if (response.body().getStatus() == 1) {
                    alertDialog.cancel();
                    String apiToken = response.body().getData().getApiToken();
                    String name = response.body().getData().getUser().getName();
                    String phone = response.body().getData().getUser().getPhone();
                    String address = response.body().getData().getUser().getAddress();
                    String email = response.body().getData().getUser().getEmail();
                    Region region = response.body().getData().getUser().getRegion();
                    long idCity = region.getCity().getId();


                    // save data to SharedPreference
                    String pass = LoginPassEt.getText().toString();
                    SharedPreferencesManger.SaveData(getActivity(),"pass_clint",pass);
                    SharedPreferencesManger.SaveData(getActivity(),"api_token_clint",apiToken);
                    SharedPreferencesManger.SaveData(getActivity(),"name_clint",name);
                    SharedPreferencesManger.SaveData(getActivity(),"email_clint",email);
                    SharedPreferencesManger.SaveData(getActivity(),"id_city_clint",idCity);

                    SharedPreferencesManger.SaveData(getActivity(),"phone_clint",phone);
                    SharedPreferencesManger.SaveData(getActivity(),"address_clint",address);

                    Toast.makeText(getActivity(), "clint" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    alertDialog.cancel();
                    Toast.makeText(getActivity(), "error" + response.body().getMsg(), Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<ClientLogin> call, Throwable t) {
                alertDialog.cancel();
                Toast.makeText(getActivity(), "onFailure" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}
