package com.example.translate_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Wifichecked extends AppCompatActivity {
    ConnectivityManager connectivityManager;
    NetworkInfo WIFI,my3G;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi);

        final Intent intent = new Intent(Wifichecked.this,MainActivity.class);

        connectivityManager = (ConnectivityManager ) getSystemService(CONNECTIVITY_SERVICE);
        WIFI = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        my3G = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (WIFI != null && WIFI.isConnected() || my3G != null & my3G.isConnected() ) {


            startActivity(intent);
        }else  {
            Toast.makeText(this, "Kết nối wifi và reload lại app", Toast.LENGTH_SHORT).show();
        }
    }
}