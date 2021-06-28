package com.example.athiya.ctrlcoffee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {


    private LinearLayout Prof_Section;
    private ImageView SignOut, MapButton, InfoButton, MusicButton;
    private SignInButton SignIn;
    private TextView Name,Email;
    private ImageView Prof_Pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE =9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Prof_Section = (LinearLayout) findViewById(R.id.prof_section);
        SignOut = (ImageView) findViewById(R.id.bn_logout);
        SignIn = (SignInButton) findViewById(R.id.bn_login);
        Name = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);
        MapButton =(ImageView) findViewById(R.id.map);
        InfoButton =(ImageView) findViewById(R.id.info);
        MusicButton = (ImageView) findViewById(R.id.music);
        Prof_Pic = (ImageView) findViewById(R.id.imageView);

        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        Prof_Section.setVisibility(View.GONE);
        MapButton.setVisibility(View.GONE);
        InfoButton.setVisibility(View.GONE);
        MusicButton.setVisibility(View.GONE);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
        Prof_Section.setVisibility(View.GONE);


        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(p);
                setContentView(R.layout.activity_maps);
            }
        });
        MusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(MainActivity.this, MusicActivity.class);
                startActivity(r);


            }
        });



    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.bn_login:
        signIn();
        break;

        case R.id.bn_logout:
        signOut();
        break;

        case R.id.info:
             info(InfoButton);
        break;

            case R.id.music:
                music(MusicButton);

        }
    }



    public void info(ImageView infoButton) {
        Intent linku = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tripadvisor.com/Restaurants-g297710-c8-Malang_East_Java_Java.html"));
        startActivity(linku);
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    private void signOut(){

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });

    }

    private void handleResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account =result.getSignInAccount();
            String name =account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
            Name.setText(name);
            Email.setText(email);
            Glide.with(this).load(img_url).into(Prof_Pic);
            updateUI(true);


        }
        else{
            updateUI(false);
        }
    }

    private void updateUI (boolean isLogin){
        if (isLogin){
            Prof_Section.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
            MapButton.setVisibility(View.VISIBLE);
            InfoButton.setVisibility(View.VISIBLE);
            MusicButton.setVisibility(View.VISIBLE);
        }
        else {
            Prof_Section.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
            MapButton.setVisibility(View.GONE);
            InfoButton.setVisibility(View.GONE);
            MusicButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }

    }


    public void music(View view) {

    }
}
