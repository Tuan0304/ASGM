package com.example.translate_application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Manhinhchao extends AppCompatActivity {
    SharedPreferences MyAccount;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinhchao);

        //make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //end make full screen

        //khai bao id
        CardView Dangky=findViewById(R.id.dkybtn);
        CardView Dangnhap=findViewById(R.id.dnbtn);
        //end khai bao id

        //local key
        MyAccount=getApplicationContext().getSharedPreferences("CusACCC",MODE_PRIVATE);
        final String IDlogin=MyAccount.getString("id","");
        final String PASSlogin=MyAccount.getString("pass","");
        //end local key

        //hàm tự đăng nhập nếu đã có login sẵn
        if(IDlogin!="" && PASSlogin!=""){
            finish();
            Intent list=new Intent(Manhinhchao.this,MainActivity.class);


            startActivity(list);
        }
        //end tự đăng nhập

        //event + animation cho nut đăng ký và đăng nhập
    Dangky.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Animation shake = AnimationUtils.loadAnimation(Manhinhchao.this, R.anim.fly2);
            view.startAnimation(shake);
            Intent intent = new Intent(Manhinhchao.this, dangdkyact.class);
            startActivity(intent);
        }
    });
        Dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation shake = AnimationUtils.loadAnimation(Manhinhchao.this, R.anim.fly2);
                view.startAnimation(shake);
                Intent intent = new Intent(Manhinhchao.this, DangNhap.class);
                startActivity(intent);

            }
        });
        //end event + animation cho nut đăng ký và đăng nhập
}}