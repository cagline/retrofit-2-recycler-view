package com.crowderia.recyclerviewproject.controller;

import android.util.Log;

import com.crowderia.recyclerviewproject.model.Repository;
import com.crowderia.recyclerviewproject.model.RepositoryResponse;
import com.crowderia.recyclerviewproject.service.RepositoryService;
import com.crowderia.recyclerviewproject.utilities.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

/**
 * Created by crowderia on 8/29/17.
 */

public class RepositoryController {

    private RepositoryCallbackListener mListener;
    private RepositoryService mService;


    public RepositoryController(RepositoryCallbackListener listener) {
        mListener = listener;
        mService = ApiUtils.getRepositoryService();
    }

    public void startFetching() {

        mService.getProperties().enqueue(new Callback<RepositoryResponse>() {
            @Override
            public void onResponse(Call<RepositoryResponse> call, Response<RepositoryResponse> response) {
                if (response.isSuccessful()) {
                    mListener.onFetchProgress(response.body().getData());
                } else {
                    int statusCode = response.code();
                    mListener.onFetchedFailured();
                }
                mListener.onFetchComplete();
            }

            @Override
            public void onFailure(Call<RepositoryResponse> call, Throwable t) {
                mListener.onFetchedFailured();
            }
        });
    }

    public interface RepositoryCallbackListener {

        void onFetchStart();

        void onFetchProgress(Repository Repository);

        void onFetchProgress(List<Repository> properties);

        void onFetchComplete();

        void onFetchedFailured();
    }
}
