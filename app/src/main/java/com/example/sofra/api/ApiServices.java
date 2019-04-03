package com.example.sofra.api;

import com.example.sofra.model.accept_order.AcceptOrder;
import com.example.sofra.model.add_new_offer_rest.AddNewOfferRest;
import com.example.sofra.model.add_review.AddReviews;
import com.example.sofra.model.add_to_cart.AddToCart;
import com.example.sofra.model.categories.Categories;
import com.example.sofra.model.chang_password.ChangPassword;
import com.example.sofra.model.client_Login.ClientLogin;
import com.example.sofra.model.client_register.RegisterClint;
import com.example.sofra.model.comments_reviews.CommentsReviews;
import com.example.sofra.model.complete_order.CompleteOrderClint;
import com.example.sofra.model.detailsOffers.DetialsOffers;
import com.example.sofra.model.general.list_of_cities.ListOfCities;
import com.example.sofra.model.general.list_of_cities.list_of_region.ListOfRegion;
import com.example.sofra.model.list_of_restaurants.ListOfRestaurants;
import com.example.sofra.model.my_items.MyItems;
import com.example.sofra.model.my_offers_rest.MyOffers;
import com.example.sofra.model.my_order.MyOrder;
import com.example.sofra.model.payment.PaymentMethod;
import com.example.sofra.model.restaurant_details.RestuarantDetails;
import com.example.sofra.model.restaurant_login.RestaurantLogin;
import com.example.sofra.model.restaurant_register.RestaurantRegestr;
import com.example.sofra.model.restayrant_order.RestaurantOrder;
import com.example.sofra.model.restuarant_food_items.FoodItems;
import com.example.sofra.model.status.Status;
import com.example.sofra.model.update_items.UpdateItems;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServices {

    @POST("client/login")
    @FormUrlEncoded
    Call<ClientLogin> clientLogin(@Field("email") String mail,
                                  @Field("password") String password
    );

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RestaurantLogin> restaurantLogin(@Field("email") String mail,
                                          @Field("password") String password
    );

    @POST("client/register")
    @FormUrlEncoded
    Call<RegisterClint> registerClint(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("password_confirmation") String password_confirmation,
                                      @Field("phone") String phone,
                                      @Field("address") String address,
                                      @Field("region_id") long region_id);


    @Multipart
    @POST("restaurant/register")
    Call<RestaurantRegestr> restaurantRegister(@Part("name") RequestBody name,
                                               @Part("email") RequestBody email,
                                               @Part("password") RequestBody password,
                                               @Part("password_confirmation") RequestBody password_confirmation,
                                               @Part("phone") RequestBody phone,
                                               @Part("whatsapp") RequestBody whatsapp,
                                               @Part("region_id") RequestBody region_id,
                                               @Part("categories[]") List<RequestBody> categories,
                                               @Part("delivery_cost") RequestBody delivery_cost,
                                               @Part("minimum_charger") RequestBody minimum_charger,
                                               @Part MultipartBody.Part File,
                                               @Part("availability") RequestBody availability);


    @GET("cities")
    Call<ListOfCities> getCities();

    @GET("regions")
    Call<ListOfRegion> getRegion(@Query("city_id") long city_id);


    @GET("categories")
    Call<Categories> getCategories();


    @POST("client/new-password")
    Call<ChangPassword> clientNewPassword(@Query("code") String code,
                                          @Query("password") String password,
                                          @Query("password_confirmation") String password_confirmation);


    @POST("client/reset-password")
    Call<ChangPassword> clientResetPassword(@Query("email") String email);


    @POST("restaurant/new-password")
    Call<ChangPassword> restaurantNewPassword(@Query("code") String code,
                                              @Query("password") String password,
                                              @Query("password_confirmation") String password_confirmation);


    @POST("restaurant/reset-password")
    Call<ChangPassword> restaurantResetPassword(@Query("email") String email);

//get restaurants

    @GET("restaurants")
    Call<ListOfRestaurants> getRestaurants(@Query("page") int page);


    @GET("items")
    Call<FoodItems> getRestaurantFoodItems(@Query("restaurant_id") long restaurant_id, @Query("page") String page);


    @GET("restaurant/reviews")
    Call<CommentsReviews> getComments(@Query("restaurant_id") long restaurant_id, @Query("page") int page);

    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<AddReviews> setComment(@Field("rate") int rate,
                                @Field("comment") String comment,
                                @Field("restaurant_id") long restaurant_id,
                                @Field("api_token") String api_token);


    @GET("restaurant")
    Call<RestuarantDetails> restaurantDetails(@Query("restaurant_id") long restaurant_id);


    @GET("restaurant/my-items")
    Call<MyItems> myItems(@Query("api_token") String api_token, @Query("page") int page);


    @POST("restaurant/change-state")
    @FormUrlEncoded
    Call<Status> changStatus(@Field("state") String state, @Field("api_token") String api_token);


    @GET("client/my-orders")
    Call<MyOrder> curentOrder(@Query("api_token") String api_token, @Query("state") String state, @Query("page") int page);


    @Multipart
    @POST("restaurant/new-item")
    Call<RestaurantRegestr> addNewProduct(@Part("description") RequestBody description,
                                          @Part("price") RequestBody price,
                                          @Part("preparing_time") RequestBody preparing_time,
                                          @Part("name") RequestBody name,
                                          @Part MultipartBody.Part File,
                                          @Part("api_token") RequestBody api_token);


    @POST("client/add-item-to-cart")
    @FormUrlEncoded
    Call<AddToCart> addTdCart(@Field("api_token") String api_token,
                              @Field("item_id") long item_id,
                              @Field("quantity") String quantity,
                              @Field("note") String note
    );

    @Multipart
    @POST("restaurant/update-item")
    Call<UpdateItems> updateItems(@Part("api_token") RequestBody api_token,
                                  @Part("description") RequestBody description,
                                  @Part("price") RequestBody price,
                                  @Part("preparing_time") RequestBody preparing_time,
                                  @Part("name") RequestBody name,
                                  @Part MultipartBody.Part photo,
                                  @Part("item_id") RequestBody item_id);


    @GET("restaurant/my-orders")
    Call<RestaurantOrder> restaurantMyOrders (@Query("api_token") String api_token ,
                                              @Query("state") String state ,
                                              @Query("page") int page);

    @GET("restaurant/my-offers")
    Call<MyOffers> myOffersRestaurant (@Query("api_token") String api_token ,
                                       @Query("page") int page );


    @POST("restaurant/accept-order")
    @FormUrlEncoded
    Call<AcceptOrder> acceptOrderRest(@Field("api_token") String apiToken , @Field("order_id") String order_id);

    @POST("restaurant/reject-order")
    @FormUrlEncoded
    Call<AcceptOrder> rejectOrderRest(@Field("order_id") String order_id , @Field("api_token") String api_token);

    @POST("restaurant/confirm-order")
    @FormUrlEncoded
    Call<AcceptOrder> confirmOrderRest(@Field("order_id") String order_id , @Field("api_token") String api_token);

    @POST("client/confirm-order")
    @FormUrlEncoded
    Call<AcceptOrder>  confirmOrderClint (@Field("order_id") String order_id , @Field("api_token") String api_token);

    @POST("client/decline-order")
    @FormUrlEncoded
    Call<AcceptOrder> cancelOrderClint (@Query("api_token") String order_id , @Field("order_id") String api_token);

    @POST("restaurant/delete-item")
    @FormUrlEncoded
    Call<AcceptOrder> deleteItemrest(@Field("item_id") String item_id , @Field("api_token") String api_token);

    @Multipart
    @POST("restaurant/new-offer")
    Call<AddNewOfferRest> addNewOfferRest (@Part("description") RequestBody description,
                                            @Part("price") RequestBody price,
                                            @Part("starting_at") RequestBody starting_at,
                                            @Part("name") RequestBody name,
                                            @Part MultipartBody.Part photo,
                                            @Part("ending_at") RequestBody  ending_at,
                                            @Part("api_token") RequestBody api_token);

    @GET("offers")
    Call<MyOffers> getOffers (@Query("page") String page);

    @GET("offer")
    Call<DetialsOffers> getOfferDetails (@Query("offer_id") String offer_id);


    @POST("client/new-order")
    @FormUrlEncoded
    Call<CompleteOrderClint> completOrder (@Field("restaurant_id") long restaurant_id ,
                                  @Field("note") String note,
                                  @Field("address") String address,
                                  @Field("payment_method_id") long payment_method_id,
                                  @Field("phone") String phone,
                                  @Field("name") String name,
                                  @Field("api_token") String api_token,
                                  @Field("items[]") List<Integer> items,
                                  @Field("quantities[]") List<Integer> quantities,
                                  @Field("notes[]") List<String> notes);

    @GET("payment-methods")
    Call<PaymentMethod> paymentMethods ();
}



