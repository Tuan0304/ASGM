package org.o7planning.bai1_lab1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String chuoi=intent.getExtras().getString("extra");

        Intent myintent=new Intent(context,Dichvu.class);
        myintent.putExtra("extra",chuoi);
        context.startService(myintent);
    }
}
