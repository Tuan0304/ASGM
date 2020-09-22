package org.o7planning.bai1_lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class xulychuoi extends AppCompatActivity {
    EditText chuoi;
    Button btntim;
    EditText charcantim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xulychuoi);

        chuoi=findViewById(R.id.edts);
        btntim=findViewById(R.id.tim);
        charcantim=findViewById(R.id.charcantim);


        final Intent intent=new Intent(xulychuoi.this,xulychuoiserv.class);
        btntim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(xulychuoi.this, "run service", Toast.LENGTH_SHORT).show();
                intent.putExtra("chuoi",chuoi.getText().toString().trim());
                intent.putExtra("char",charcantim.getText().toString().trim());
                startService(intent);
            }
        });

    }
}