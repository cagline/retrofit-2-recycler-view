package com.crowderia.recyclerviewproject.view;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements RepositoryController.RepositoryCallbackListener, NavigationView.OnNavigationItemSelectedListener {

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
        initNaviView();
        mRepositoryController.startFetching();
    }

    private void initNaviView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
