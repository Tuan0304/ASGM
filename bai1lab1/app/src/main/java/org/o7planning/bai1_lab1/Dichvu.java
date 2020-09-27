package org.o7planning.bai1_lab1;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Dichvu extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "Service dang chay", Toast.LENGTH_SHORT).show();

        Bundle b=intent.getBundleExtra("student");

        int StuId = b.getInt("StuId");
        String StuName=b.getString("StuName");
        String Class=b.getString("Class");

        String content="Them sinh vien thanh cong. \n Thong tin sinh vien:"+StuId+" "+StuName;
        content += "\nLop"+Class;
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        super.onStart(intent, startId);
    }

  /*  @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String nhanchuoi=intent.getExtras().getString("extra");

        Toast.makeText(this, ""+nhanchuoi, Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, "Service dang chay", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }*/


}
