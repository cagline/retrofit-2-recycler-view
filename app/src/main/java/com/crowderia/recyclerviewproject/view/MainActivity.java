package com.crowderia.recyclerviewproject.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.volley.Response;
import com.crowderia.recyclerviewproject.controller.RepositoryController;
import com.crowderia.recyclerviewproject.utilities.CheckNetwork;
import com.crowderia.recyclerviewproject.view.adapter.RepositoryAdapter;
import com.crowderia.recyclerviewproject.R;
import com.crowderia.recyclerviewproject.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RepositoryController.RepositoryCallbackListener, NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private RepositoryAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Repository> listItems;

    private RepositoryController mRepositoryController = new RepositoryController(MainActivity.this);

    private ProgressDialog progressDaialog;
    private AlertDialog dialogSetSearchKey;

    private final static String TAG = "Main Activity -";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepositoryController = new RepositoryController(MainActivity.this);
        checkNetwork();
        initView();
        initNavigationView();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String searchKey= preferences.getString("SEARCH_KEY", "Android");
        Toast.makeText(getApplicationContext(), "SEARCH_KEY -" + searchKey, Toast.LENGTH_SHORT).show();

        mRepositoryController.startFetching(searchKey);

    }

    private void checkNetwork() {
//        if (PermissionUtil.checkSelfPermission(this,"INTERNET")){
//            PermissionUtil.requestPermissions(this,"INTERNET");
//        }
        if(!CheckNetwork.isInternetAvailable(this))
        {
            Toast.makeText(this,"No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Init navigation view
     */
    private void initNavigationView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
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
        swipeRefreshLayout.setRefreshing(true);
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
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onFetchedFailured() {
        progressDaialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        final EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                String searchKey = searchEditText.getText().toString().trim();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mRepositoryController.startFetching(searchKey);
                }

                InputMethodManager in = (InputMethodManager)getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
                // searchEditText.clearFocus(); not working forcuse again
                searchView.clearFocus();
                return false;
            }
        });

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
            AlertDialog.Builder builderSetSearchKey = setSaarchKey();
            dialogSetSearchKey = builderSetSearchKey.create();
            dialogSetSearchKey.show();

            final Button buttonSave = dialogSetSearchKey.getButton(AlertDialog.BUTTON_POSITIVE);
            final EditText searchKey = (EditText)dialogSetSearchKey.findViewById(R.id.set_search_key);

            buttonSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View v){
                    String searchkey = searchKey.getText().toString();

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("SEARCH_KEY", searchkey);
                    editor.apply();
                    dialogSetSearchKey.dismiss();
                }
            });
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

    @Override
    public void onRefresh() {
        Log.d(TAG,"onRefresh");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String searchKey= preferences.getString("SEARCH_KEY", "");
        mRepositoryController.startFetching(searchKey);
    }

    public AlertDialog.Builder setSaarchKey() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
//        LayoutInflater inflater = this.getLayoutInflater();
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view = inflater.inflate(R.layout.dialog_search_key, null);
        builder.setTitle(getResources().getString(R.string.action_search_key));
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("save",dialog.toString()+"-"+id);

                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("cancel",dialog.toString()+"-"+id);
                    }
                });

        return builder;
    }
}
