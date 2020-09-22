package org.o7planning.bai1_lab1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Objects;

public class xulychuoiserv extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "service dang chay", Toast.LENGTH_SHORT).show();
        String nhanchuoi=intent.getExtras().getString("chuoi");
        String nhanchar=intent.getExtras().getString("char");
        //char somechar=nhanchar.charAt(1);
        int count=0;
        for (int i=0;i<nhanchuoi.length();i++){
            if(nhanchuoi.charAt(i)==nhanchar.charAt(0)){
                count++;
            }
        }
      //  Toast.makeText(this, nhanchar, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, nhanchuoi, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "số ký tự cần tìm là:"+count, Toast.LENGTH_SHORT).show();
                super.onStart(intent, startId);
    }
}
