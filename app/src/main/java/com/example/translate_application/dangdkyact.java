package com.example.translate_application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

public class dangdkyact extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    SharedPreferences MyAccount;
    int checkpass=1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky);

        //make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //end make full screen

        //khai bao ID
         final EditText ID=findViewById(R.id.edtid);
         final EditText EMAIL=findViewById(R.id.edtemail);
         final EditText PASS=findViewById(R.id.edtpass);
        TextView hienpass=findViewById(R.id.showpass2);
        final CardView Taotk=findViewById(R.id.dky);
        //end khai bao id


        //khai bao database
        databaseHelper=new DatabaseHelper(this,"danhsachid3.sqllite",null,1);
        databaseHelper.Uploaddata("CREATE TABLE IF NOT EXISTS NguoiDung(ID INTEGER PRIMARY KEY AUTOINCREMENT,Ten COLLATE NOCASE,Email COLLATE NOCASE,Pass VARCHAR(100) )");
        //end database

        //ham show pass tren edittext:
        hienpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkpass==1)
                {
                    PASS.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    checkpass=2;

                }else{

                    PASS.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    checkpass=1;
                }
            }
        });
        //end ham show pass tren edittext:

        //ham tao tai khoan:
        Taotk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final EditText ID=findViewById(R.id.edtid);
                final EditText EMAIL=findViewById(R.id.edtemail);
                final EditText PASS=findViewById(R.id.edtpass);

                if(ID.getText().toString().equals("")||EMAIL.getText().toString().equals("")  || PASS.getText().toString().equals("")){
                    Animation shake = AnimationUtils.loadAnimation(dangdkyact.this, R.anim.shake);
                    view.startAnimation(shake);
                    Toast.makeText(dangdkyact.this, "Bạn Chưa Nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    final Cursor dataselect=databaseHelper.GetData("select * from NguoiDung where Email='"+EMAIL.getText().toString()+"'");

                    if(dataselect.getCount()>0) {
                        Animation shake = AnimationUtils.loadAnimation(dangdkyact.this, R.anim.shake);
                        view.startAnimation(shake);
                        Toast.makeText(dangdkyact.this, "Email này đã có người dùng, vui lòng chọn Email khác!", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseHelper.Uploaddata("insert into NguoiDung values(null,'" + ID.getText().toString() + "','" + EMAIL.getText().toString() + "','" + PASS.getText().toString() + "')");
                        Animation shake = AnimationUtils.loadAnimation(dangdkyact.this, R.anim.shake2);
                        view.startAnimation(shake);
                        Toast.makeText(dangdkyact.this, "Đăng Ký Thành Công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(dangdkyact.this, DangNhap.class);
                        startActivity(intent);
                        Log.d("tuan", "" + PASS.getText().toString());
                    }
                }
            }
        });
        //end ham tao tai khoan

        //day regex cho dang ky
        final  String vldten="[a-zA-Z]+";
        final String vldemail="^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
        final  String vldpass="[a-z0-9_-]{6,12}$";
        //end regex

        //ham so sánh regex
        ID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().matches(vldten) && charSequence.length()>=5 && charSequence.length()<=50){


                    ID.setError(null);


                }else{
                    ID.setError("Username phai lon hon 5 ky tu, khong chua ky tu dac biet");


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EMAIL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().matches(vldemail)){


                    EMAIL.setError("Vui Long Nhap Dung Dinh Dang Email");


                }else{


                    EMAIL.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        PASS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().matches(vldpass) && charSequence.length()>=9 && charSequence.length()<=50){


                    PASS.setError(null);


                }else{
                    PASS.setError("Mật Khẩu dài từ 6-12 ký tự, khong chua ky tu dac biet");


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //end ham so sánh regex
    }
}
