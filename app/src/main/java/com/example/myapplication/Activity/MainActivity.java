package com.example.myapplication.Activity;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.MultipleResource;
import com.example.myapplication.Data.Post;
import com.example.myapplication.Interface.APIClient;
import com.example.myapplication.Interface.APIInterface;
import com.example.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseText = (TextView) findViewById(R.id.text);
        apiInterface = APIClient.getClient().create(APIInterface.class);


        /**
         GET List Resources
         **/
        Call<MultipleResource> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {


                Log.d("TAG",response.code()+"");

                String displayResponse = "";

                MultipleResource resource = response.body();
                String title = resource.title;
                String author = resource.author;
                String body = resource.body;

                displayResponse += title + " Title\n" + author + " Author\n" + body + " Body\n";

                responseText.setText(displayResponse);

            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                call.cancel();
            }
        });

        /**
         Create new user
         **/
        Post post = new Post("First Post", "An", "this is my first post!!");
        Call<Post> call1 = apiInterface.createPost(post);
        call1.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post1 = response.body();

                Toast.makeText(getApplicationContext(), post1.title + " " + post1.author + " " + post1.id + " " + post1.body, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                call.cancel();
            }
        });

    }
}