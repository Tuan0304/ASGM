package org.o7planning.bai1_lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnrun,btnstop,bai3btn;
    EditText editText;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnrun=findViewById(R.id.run);
        btnstop=findViewById(R.id.stop);
        bai3btn=findViewById(R.id.bai3);
        //alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        final Intent intent=new Intent(MainActivity.this,Dichvu.class);

        btnrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle b=new Bundle();
                b.putInt("StuId",1);
                b.putString("StuName","Tuan");
                b.putString("Class","LTMT");
                intent.putExtra("student",b);
                Toast.makeText(MainActivity.this, "Khoi tao service", Toast.LENGTH_SHORT).show();
                startService(intent);
               // intent.putExtra("extra",editText.getText().toString());
                /*pendingIntent=PendingIntent.getBroadcast(
                        MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT
                );*/
               // alarmManager.set(AlarmManager.RTC_WAKEUP,1,pendingIntent);

            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Dung service", Toast.LENGTH_SHORT).show();
                stopService(intent);

                //alarmManager.cancel(pendingIntent);
            }
        });
        bai3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, xulychuoi.class));
                Toast.makeText(MainActivity.this, "Halo", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "gfjtdhjd", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this,"BlaNla",Toast.LENGTH_SHORT).show();
                //ffsfsfgit

            }
        });
    }
}