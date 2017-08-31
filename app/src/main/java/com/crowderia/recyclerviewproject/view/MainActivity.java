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
import com.crowderia.recyclerviewproject.controller.RepositoryController;
import com.crowderia.recyclerviewproject.model.RepositoryAdapter;
import com.crowderia.recyclerviewproject.R;
import com.crowderia.recyclerviewproject.model.Repository;
import com.crowderia.recyclerviewproject.model.RepositoryResponse;
import com.crowderia.recyclerviewproject.service.RepositoryService;
import com.crowderia.recyclerviewproject.utilities.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RepositoryController.RepositoryCallbackListener {

    private RecyclerView mRecyclerView;
    private RepositoryAdapter mAdapter;

    private List<Repository> listItems;

    private RepositoryController mRepositoryController = new RepositoryController(MainActivity.this);

    private ProgressDialog progressDaialog;

    private final static String TAG = "Main Activity -";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepositoryController = new RepositoryController(MainActivity.this);
        initView();
        mRepositoryController.startFetching();
    }

    private void initView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mAdapter = new RepositoryAdapter(this, new ArrayList<Repository>(0), new RepositoryAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        progressDaialog = new ProgressDialog(this);
    }

    // ===== RepositoryCallbackListener start =====
    @Override
    public void onFetchStart() {
        progressDaialog.show();
    }

    @Override
    public void onFetchProgress(Repository property) {
        Log.d(TAG, property.toString());
    }

    @Override
    public void onFetchProgress(List<Repository> properties) {
        Log.d(TAG, properties.toString());
        mAdapter.updateProperties(properties);
    }

    @Override
    public void onFetchComplete() {
        progressDaialog.dismiss();
    }

    @Override
    public void onFetchedFailured() {
        progressDaialog.dismiss();
        Toast.makeText(MainActivity.this, "Something whent wrong", Toast.LENGTH_SHORT).show();
    }
    // ===== RepositoryCallbackListener end =====

}
