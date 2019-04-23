package com.example.sofra.ui.fragments.restaurant.offers;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.add_new_offer_rest.AddNewOfferRest;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
import static com.example.sofra.ui.fragments.login.RegistreResturantFragment.TAG;
import static java.util.Calendar.YEAR;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewOfferskFragment extends Fragment {


    @BindView(R.id.Add_Offer_tv_name)
    EditText AddOfferTvName;
    @BindView(R.id.Add_Offer_tv_dec)
    EditText AddOfferTvDec;
    @BindView(R.id.Add_Offer_tv_price)
    EditText AddOfferTvPrice;
    @BindView(R.id.AddOffers_layout_from_to)
    LinearLayout AddOffersLayoutFromTo;
    @BindView(R.id.Add_Offer_img)
    ImageView AddOfferImg;
    @BindView(R.id.Add_product_text)
    TextView AddProductText;
    @BindView(R.id.Add_Offer_btn_Add)
    Button AddOfferBtnAdd;
    Unbinder unbinder;
    @BindView(R.id.Add_Offer_Tv_Ending_at)
    TextView AddOfferTvEndingAt;
    @BindView(R.id.Add_Offer_Tv_Starting_at)
    TextView AddOfferTvStartingAt;
    private String api_token_restaurant;
    private ApiServices apiServices;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private Calendar myCalendar;
    private AlertDialog alertDialog;


    public AddNewOfferskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_offersk, container, false);
        unbinder = ButterKnife.bind(this, view);
myCalendar=Calendar.getInstance();
        api_token_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_restaurant");
        apiServices = getClint().create(ApiServices.class);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();
        alertDialog.show();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Add_Offer_img, R.id.Add_Offer_btn_Add,R.id.Add_Offer_Tv_Ending_at, R.id.Add_Offer_Tv_Starting_at})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Add_Offer_img:
                sellectPhoto();
                break;
            case R.id.Add_Offer_btn_Add:
                addNewOffer();
                break;
            case R.id.Add_Offer_Tv_Ending_at:

                endingAtDialog();
                break;
            case R.id.Add_Offer_Tv_Starting_at:
                startingAtDialog();
                break;
        }
    }



    private void addNewOffer() {
        alertDialog.show();
        RequestBody dec = convertToRequestBody(AddOfferTvDec.getText().toString());
        RequestBody name = convertToRequestBody(AddOfferTvName.getText().toString());
        RequestBody price = convertToRequestBody(AddOfferTvPrice.getText().toString());
        RequestBody api_token = convertToRequestBody(api_token_restaurant);

        RequestBody starting_at = convertToRequestBody(AddOfferTvStartingAt.getText().toString());
        RequestBody ending_at = convertToRequestBody(AddOfferTvEndingAt.getText().toString());

        MultipartBody.Part partPhoto = convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo");
        apiServices.addNewOfferRest(dec, price, starting_at, name, partPhoto, ending_at, api_token).enqueue(new Callback<AddNewOfferRest>() {
            @Override
            public void onResponse(Call<AddNewOfferRest> call, Response<AddNewOfferRest> response) {
                String msg = response.body().getMsg();
                Long status = response.body().getStatus();
                if (status == 1) {
                    alertDialog.cancel();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    replaceFragment(new MyOffersRestFragment() , R.id.Home_replace_fragments,getActivity().getSupportFragmentManager().beginTransaction());

                } else {
                    alertDialog.cancel();

                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<AddNewOfferRest> call, Throwable t) {
                alertDialog.cancel();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    private void sellectPhoto() {


        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(@NonNull ArrayList<AlbumFile> result) {

                ImagesFiles.clear();
                ImagesFiles.addAll(result);

                Glide.with(getContext()).load(ImagesFiles.get(0).getPath()).into(AddOfferImg);


            }
        };

        openAlbum(3, getActivity(), ImagesFiles, action);


    }

    private void startingAtDialog() {

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                AddOfferTvStartingAt.setText(sdf.format(myCalendar.getTime()));


            }
        };


        new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void endingAtDialog() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                AddOfferTvEndingAt.setText(sdf.format(myCalendar.getTime()));


            }
        };


        new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }


}
