package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.User;
import com.example.myapplication.Interface.RetroClient;
import com.example.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistActivity extends AppCompatActivity {

    User user;
    EditText id, pw, name;
    Button regist;
    RetroClient retroClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_regist);

        user = (User)getIntent().getParcelableExtra("user");

        id = findViewById(R.id.idEditText);
        pw = findViewById(R.id.pwEditText);
        name = findViewById(R.id.nameEditText);
        regist = findViewById(R.id.registerButton);


        retroClient = RetroClient.getInstance(this).createBaseApi();

        if(user.getId() != null){
            id.setText(user.getId());
            pw.setText(user.getPw());
        }

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setId(id.getText().toString());
                user.setPw(pw.getText().toString());
                Call<User> call = retroClient.apiService.getUser(user.id); // 이미 있는 아이디 인가?
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) { // 이미 있는 아이디
                        if(response.body() == null){
                            user.setName(name.getText().toString());
                            Call<User> call1 = retroClient.apiService.createUser(user);
                            call1.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Log.d("regist", "onResponse: " + response);
                                    Toast.makeText(RegistActivity.this, "message : " + response.message() + "\n" +
                                            "body : " + response.body() + "\n" +
                                            "isSuccessful : " + response.isSuccessful(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent();
                                    intent.putExtra("user",user);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    call.cancel();
                                }
                            }); 
                        }
                        else{
                            Toast.makeText(RegistActivity.this, "Id is already exist..", Toast.LENGTH_SHORT).show();
                        }
                    }
                            

                    @Override
                    public void onFailure(Call<User> call, Throwable t) { // 없는 아이디
                        Toast.makeText(RegistActivity.this, "Server Error!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
