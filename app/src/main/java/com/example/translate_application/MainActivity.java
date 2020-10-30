package com.example.translate_application;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.translate_application.voice.VoiceFragment;
import com.example.translate_application.wordbook.WordBookFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity  {
    ConnectivityManager connectivityManager;
    NetworkInfo WIFI,my3G;
    private AppBarConfiguration mAppBarConfiguration;
    ImageView logout;
    TextView ten, email;
    String edtten, edtEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropdown_menu);
        final Intent intent = new Intent(MainActivity.this,Wifichecked.class);
        //anhxa
        Toolbar toolbar = findViewById(R.id.toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        logout = headerview.findViewById(R.id.logout2);
        ten = headerview.findViewById(R.id.name);
        email = headerview.findViewById(R.id.Email);
        SharedPreferences preferences = getSharedPreferences("CusACCC",MODE_PRIVATE);
        edtten =preferences.getString("pass","");
         edtEmail=preferences.getString("id","");
         ten.setText(edtten);
         email.setText(edtEmail);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });


        connectivityManager = (ConnectivityManager ) getSystemService(CONNECTIVITY_SERVICE);
        WIFI = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        my3G = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (WIFI != null && WIFI.isConnected() || my3G != null & my3G.isConnected()) {
            Toast.makeText(this, "Internet Connect", Toast.LENGTH_SHORT).show();

        }else  {
            finish();
            startActivity(intent);
        }


        //end anhxa
//

        //fragment
        setSupportActionBar(toolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



        //showmenu
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_wordbook, R.id.nav_voice, R.id.nav_offline, R.id.nav_setting, R.id.nav_help)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //showmenu
        //end fragment
    }



//hostfragmentview(homefragment)
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    //end

        private void Logout(){
            AlertDialog.Builder logoutDialog = new AlertDialog.Builder(this);
            logoutDialog.setMessage("Bạn có muốn đăng xuất? ");
            logoutDialog.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences preferences = getSharedPreferences("CusACCC",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    finish();
                    Intent list=new Intent(MainActivity.this,Manhinhchao.class);
                    startActivity(list);


                }
            });
            logoutDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
               logoutDialog.show();
    }


}