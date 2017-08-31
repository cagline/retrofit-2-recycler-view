package com.crowderia.recyclerviewproject.service;

/**
 * Created by crowderia on 8/29/17.
 */

import com.crowderia.recyclerviewproject.model.RepositoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RepositoryService {

    @GET("/search/repositories?q=android")
    Call<RepositoryResponse> getProperties();

    @GET("/search/repos/{full_name}")
    Call<RepositoryResponse> getRepository();
}
