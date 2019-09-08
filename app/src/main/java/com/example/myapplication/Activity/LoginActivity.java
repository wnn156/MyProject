package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.User;
import com.example.myapplication.Interface.RetroClient;
import com.example.myapplication.Interface.SharedPreference;
import com.example.myapplication.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    SignInButton Google_Login;
    private int REGIST = 3000;
    EditText ID, PW;
    Button regist, login;
    private static final int RC_SIGN_IN = 1000;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private int requestCode;
    private int resultCode;
    private Intent data;
    private User user;
    private RetroClient retroClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_login);


        User user1 = SharedPreference.getAttribute(LoginActivity.this);
        if(user1 != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        retroClient = RetroClient.getInstance(this).createBaseApi();


        ID = findViewById(R.id.idEditText);
        PW = findViewById(R.id.pwEditText);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        Google_Login = findViewById(R.id.Google_Login);
        Google_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent,RC_SIGN_IN);
            }
        });
        regist = findViewById(R.id.registerButton);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = ID.getText().toString();
                String pw = PW.getText().toString();
                if(id != null && pw != null){
                    user = new User();
                    user.setId(id);
                    user.setPw(pw);
                    Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                    intent.putExtra("user", user);
                    startActivityForResult(intent, REGIST);
                }
                else{
                    Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                    intent.putExtra("user",new User());
                    startActivityForResult(intent,REGIST);
                }
            }
        });

        login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = ID.getText().toString();
                final String pw = PW.getText().toString();

                user = new User(id,pw);

                Call<User> call = retroClient.apiService.getUser(id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("login", "onResponse: " + response.body());
                        Log.d("login", "onResponse: " + user);
                        if(response.body() == null){
                            Log.d("logd", "onResponse: " + response.body());
                            call.cancel();
                            Toast.makeText(LoginActivity.this, "Id or Pw wrong!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(response.body().getId().equals(user.getId())&& response.body().getPw().equals(user.getPw())){
                            user = response.body();
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            SharedPreference.setAttribute(LoginActivity.this, user);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Id or Pw wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Server error...", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                //구글 로그인 성공해서 파베에 인증
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else{
                //구글 로그인 실패
                Toast.makeText(this, "Fail login...", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REGIST) {
            user = data.getParcelableExtra("user");
        }
    }
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show();
                            final User user = new User();
                            user.setId(acct.getEmail());
                            user.setName(acct.getDisplayName());
                            SharedPreference.setAttribute(LoginActivity.this, user);

                            Call<User> call1 = retroClient.apiService.createUser(user);
                            call1.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Log.d("regist", "onResponse: " + response);
                                    Toast.makeText(LoginActivity.this, "message : " + response.message() + "\n" +
                                            "body : " + response.body() + "\n" +
                                            "isSuccessful : " + response.isSuccessful(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    call.cancel();
                                }
                            });


                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
