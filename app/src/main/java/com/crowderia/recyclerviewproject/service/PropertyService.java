package com.crowderia.recyclerviewproject.service;

/**
 * Created by crowderia on 8/29/17.
 */

import com.crowderia.recyclerviewproject.model.PropertyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PropertyService {

    @GET("/api/houses")
    Call<PropertyResponse> getProperties();

    @GET("/api/houses/{id}")
    Call<PropertyResponse> getProperty(@Query("id") String id);
}
