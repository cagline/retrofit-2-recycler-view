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

    private RepositoryService mService;

    private RepositoryController mRepositoryController = new RepositoryController(MainActivity.this);

    private ProgressDialog progressDaialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = ApiUtils.getRepositoryService();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        listItems = new ArrayList<>();
//        mAdapter = new RepositoryAdapter(this, listItems);
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

        mRepositoryController = new RepositoryController(MainActivity.this);
        loadRecyclerViewData();
        mRepositoryController.startFetching();

    }

    private void loadRecyclerViewData() {
        progressDaialog = new ProgressDialog(this);
    }

    // ===== RepositoryCallbackListener start =====
    @Override
    public void onFetchStart() {

        progressDaialog.show();
    }

    @Override
    public void onFetchProgress(Repository Repository) {
    }

    @Override
    public void onFetchProgress(List<Repository> properties) {
        mAdapter.updateProperties(properties);
        Toast.makeText(MainActivity.this, "Get Repository Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFetchComplete() {
        progressDaialog.dismiss();
    }

    @Override
    public void onFetchedFailured() {

    }
    // ===== RepositoryCallbackListener end =====

}
