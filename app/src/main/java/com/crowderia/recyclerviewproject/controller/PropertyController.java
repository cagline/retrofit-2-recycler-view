package com.crowderia.recyclerviewproject.controller;

import android.util.Log;

import com.crowderia.recyclerviewproject.model.Property;
import com.crowderia.recyclerviewproject.model.PropertyResponse;
import com.crowderia.recyclerviewproject.service.PropertyService;

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


        public PropertyController(PropertyCallbackListener listener){
            mListener = listener;

        }

        public void startFetching(){
            mService.getProperties().enqueue(new Callback<PropertyResponse>() {
                @Override
                public void onResponse(Call<PropertyResponse> call, Response<PropertyResponse> response) {
                    if(response.isSuccessful()) {

//                        mAdapter.updateProperties(response.body().getData());
                        Log.d("MainActivity", "posts loaded from API");
                        mListener.onFetchProgress(response.body().getData());

                    }else {
                        int statusCode  = response.code();
                    }
                    mListener.onFetchComplete();
                }

                @Override
                public void onFailure(Call<PropertyResponse> call, Throwable t) {
                    mListener.onFetchComplete();
                }
            });
        }

        public interface PropertyCallbackListener {

            void onFetchStart();
            void onFetchProgress(Property property);
            void onFetchProgress(List<Property> properties);
            void onFetchComplete();
        }
}
