package com.example.myapplication.Interface;

import com.example.myapplication.Data.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    final String Base_URL = "http://35.208.218.32:3000/";

    @GET("/test/{data}")
    Call<ArrayList<Post>> doGetListResources(
            @Path("data") String data
    );

    @POST("test/posts")
    Call<Post> createPost(@Body Post post);

}