package com.example.myapplication.Activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.PostRecyclerAdapter;
import com.example.myapplication.Data.Post;
import com.example.myapplication.Interface.RetroClient;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    int i = 1;
    FloatingActionButton fab;
    PostRecyclerAdapter adapter;
    SwipeRefreshLayout swipeRefreshLo;
    private ArrayList<Post> items = new ArrayList<>();
    private static final String TAG = MainActivity.class.getName();
    RetroClient retroClient;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.plus, getApplicationContext().getTheme()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 Create new user
                 **/
                Post post = new Post((i++) + "th Post!", "An", "this is my first post!!");
                Call<Post> call1 = retroClient.apiService.createPost(post);
                call1.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {

                        Log.d(TAG, "onResponse: " + response);
                        Toast.makeText(MainActivity.this, "message : " + response.message() + "\n" +
                                "body : " + response.body() + "\n" +
                                "isSuccessful : " + response.isSuccessful(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        swipeRefreshLo = findViewById(R.id.swipeRefreshLo);
        swipeRefreshLo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: in runrunrunrun");
                        Call<ArrayList<Post>> call = retroClient.apiService.doGetListResources("posts");
                        call.enqueue(new Callback<ArrayList<Post>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                                if (response.isSuccessful()) {
                                    adapter.refreshData(response.body());
                                    adapter.notifyDataSetChanged();
                                    Snackbar.make(mRecyclerView, "Refresh Success", Snackbar.LENGTH_SHORT).show();
                                    swipeRefreshLo.setRefreshing(false);
                                } else {
                                    Log.d(TAG, "onResponse: Error");
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                                Log.d(TAG, "onFailure: " + t.toString());
                            }
                        });
                    }
                }, 500);

            }
        });
        retroClient = RetroClient.getInstance(this).createBaseApi();
        System.out.println("asf asdf asdf sf ");


        /**
         GET List Resources
         **/
        Call<ArrayList<Post>> call = retroClient.apiService.doGetListResources("posts");
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    Log.d(TAG, "onResponse: " + items);
                    adapter = new PostRecyclerAdapter(items);
                    mRecyclerView.setAdapter(adapter);

                } else {
                    Log.d(TAG, "onResponse: Error");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });

    }
}