package com.example.translate_application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DangNhap extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    SharedPreferences MyAccount;
    int checkpass=1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

        //make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //end make full screen

        //khai bao id
        final CardView Login=findViewById(R.id.login);
        TextView clear=findViewById(R.id.clremail);
        final EditText User=findViewById(R.id.dnemail);
        final EditText Pass=findViewById(R.id.dnpass);
        final TextView Show=findViewById(R.id.showpass);
        //end khai bao id

        // khai bao database + local key
        databaseHelper=new DatabaseHelper(this,"danhsachid3.sqllite",null,1);
        databaseHelper.Uploaddata("CREATE TABLE IF NOT EXISTS NguoiDung(ID INTEGER PRIMARY KEY AUTOINCREMENT,Ten COLLATE NOCASE ,Email COLLATE NOCASE ,Pass VARCHAR(100) )");
        MyAccount=getApplicationContext().getSharedPreferences("CusACCC",MODE_PRIVATE);
        // end khai bao database



        //ham clear mail + show pass
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.getText().clear();
            }
        });
        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkpass==1)
                {
                    Pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    checkpass=2;

                }else{

                    Pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    checkpass=1;
                }

            }
        });
        //end ham clear mail + show pass

        //ham login
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TenID=User.getText().toString();
                String MatKhau=Pass.getText().toString();
                if(TenID.equals("") || MatKhau.equals("")){
                    Animation shake = AnimationUtils.loadAnimation(DangNhap.this, R.anim.shake);
                    view.startAnimation(shake);
                    Toast.makeText(DangNhap.this, "Bạn Chưa Nhập Tài Khoản", Toast.LENGTH_SHORT).show();
                }else{
                    final Cursor dataselect=databaseHelper.GetData("select * from NguoiDung where Email='"+TenID+"' and Pass='"+MatKhau+"'");

                    if(dataselect.getCount()>0) {
                        SharedPreferences.Editor myeditor = MyAccount.edit();
                            myeditor.putString("id", TenID);
                            myeditor.putString("pass", MatKhau);
                            myeditor.commit();

                        Animation shake = AnimationUtils.loadAnimation(DangNhap.this, R.anim.shake2);
                        view.startAnimation(shake);

                            Intent list = new Intent(DangNhap.this, MainActivity.class);
                            startActivity(list);
                            finish();

                    }else{
                        Animation shake = AnimationUtils.loadAnimation(DangNhap.this, R.anim.shake);
                        view.startAnimation(shake);
                        Toast.makeText(DangNhap.this, "Tài Khoản Đăng Nhập Sai!", Toast.LENGTH_SHORT).show();
                    }




                }

            }
        });
        //end ham login
    }}