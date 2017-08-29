package com.crowderia.recyclerviewproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

//import com.android.volley.Response;
import com.crowderia.recyclerviewproject.api.RestApiManager;
import com.crowderia.recyclerviewproject.model.Property;
import com.crowderia.recyclerviewproject.model.PropertyResponse;
import com.crowderia.recyclerviewproject.service.remote.PropertyService;
import com.crowderia.recyclerviewproject.utilities.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "c";

    private RecyclerView mRecyclerView;
    private PropertyAdapter mAdapter;

    private List<Property> listItems;

    public RestApiManager mRestApiManager;

    private PropertyService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = ApiUtils.getPropertyService();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        listItems = new ArrayList<>();
        mAdapter = new PropertyAdapter(listItems, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        loadRecyclerViewData();

    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDaialog = new ProgressDialog(this);
        progressDaialog.setMessage("Loading Data");
        progressDaialog.show();

        mService.getProperties().enqueue(new Callback<PropertyResponse>() {
            @Override
            public void onResponse(Call<PropertyResponse> call, Response<PropertyResponse> response) {

                if(response.isSuccessful()) {

                    mAdapter.updateProperties(response.body().getData());
                    Log.d("MainActivity", "posts loaded from API");

                }else {
                    int statusCode  = response.code();
                }
                progressDaialog.dismiss();
            }

            @Override
            public void onFailure(Call<PropertyResponse> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
                progressDaialog.dismiss();
            }
        });
    }
}
