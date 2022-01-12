package com.example.calender.network;

import com.example.calender.models.GetSupplierModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("api/SupplierApi/GetFFCSupplierByUser")
    Call<List<GetSupplierModel>> getSupplier(@Query("User_Type")String userType, @Query("UserId")int userID, @Query("Region_Id")int regionID);
}
