package com.crowderia.recyclerviewproject.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

//import com.android.volley.Response;
import com.crowderia.recyclerviewproject.controller.PropertyController;
import com.crowderia.recyclerviewproject.model.PropertyAdapter;
import com.crowderia.recyclerviewproject.R;
import com.crowderia.recyclerviewproject.model.Property;
import com.crowderia.recyclerviewproject.model.PropertyResponse;
import com.crowderia.recyclerviewproject.service.PropertyService;
import com.crowderia.recyclerviewproject.utilities.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PropertyController.PropertyCallbackListener{

    private RecyclerView mRecyclerView;
    private PropertyAdapter mAdapter;

    private List<Property> listItems;

    private PropertyService mService;

    private PropertyController mPropertyController = new PropertyController(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = ApiUtils.getPropertyService();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        listItems = new ArrayList<>();
//        mAdapter = new PropertyAdapter(this, listItems);
        mAdapter = new PropertyAdapter(this, new ArrayList<Property>(0), new PropertyAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

//        mPropertyController = new PropertyController(MainActivity.this);

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

        mPropertyController.startFetching();

        Log.d("fetching","mPropertyController.startFetching");

//        mService.getProperties().enqueue(new Callback<PropertyResponse>() {
//            @Override
//            public void onResponse(Call<PropertyResponse> call, Response<PropertyResponse> response) {
//
//                if(response.isSuccessful()) {
//
//                    mAdapter.updateProperties(response.body().getData());
//                    Log.d("MainActivity", "posts loaded from API");
//
//                }else {
//                    int statusCode  = response.code();
//                }
//                progressDaialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<PropertyResponse> call, Throwable t) {
//                Log.d("MainActivity", "error loading from API");
//                progressDaialog.dismiss();
//            }
//        });
    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(Property property) {

    }

    @Override
    public void onFetchProgress(List<Property> properties) {
        mAdapter.updateProperties(properties);

    }

    @Override
    public void onFetchComplete() {
        Log.d("fetching","onFetchComplete");
    }
}
