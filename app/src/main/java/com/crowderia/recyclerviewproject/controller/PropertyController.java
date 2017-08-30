package com.crowderia.recyclerviewproject.controller;

import android.util.Log;

import com.crowderia.recyclerviewproject.model.Property;
import com.crowderia.recyclerviewproject.model.PropertyResponse;
import com.crowderia.recyclerviewproject.service.PropertyService;
import com.crowderia.recyclerviewproject.utilities.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

/**
 * Created by crowderia on 8/29/17.
 */

public class PropertyController {

    private PropertyCallbackListener mListener;
    private PropertyService mService;


    public PropertyController(PropertyCallbackListener listener) {
        mListener = listener;
        mService = ApiUtils.getPropertyService();
    }

    public void startFetching() {

        mService.getProperties().enqueue(new Callback<PropertyResponse>() {
            @Override
            public void onResponse(Call<PropertyResponse> call, Response<PropertyResponse> response) {
                if (response.isSuccessful()) {
                    mListener.onFetchProgress(response.body().getData());
                } else {
                    int statusCode = response.code();
                    mListener.onFetchedFailured();
                }
                mListener.onFetchComplete();
            }

            @Override
            public void onFailure(Call<PropertyResponse> call, Throwable t) {
                mListener.onFetchedFailured();
            }
        });
    }

    public interface PropertyCallbackListener {

        void onFetchStart();

        void onFetchProgress(Property property);

        void onFetchProgress(List<Property> properties);

        void onFetchComplete();

        void onFetchedFailured();
    }
}
