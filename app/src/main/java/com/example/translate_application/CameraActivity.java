package com.example.translate_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.List;

public class CameraActivity extends AppCompatActivity {
    ImageView img;
    Button capture,detect;
    TextView txt;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        img=(ImageView)findViewById(R.id.image_view);
        capture=(Button)findViewById(R.id.capture_button);
        detect=(Button)findViewById(R.id.detect_button);
        txt=(TextView)findViewById(R.id.textView);




    }


    public void capture_function(View view)
    {
        dispatchTakePictureIntent();
        txt.setText("");
        Toast.makeText(this, "aug", Toast.LENGTH_SHORT).show();
    }

    public void detect_function(View view)
    {

        Toast.makeText(this, "abc", Toast.LENGTH_SHORT).show();
        FirebaseVisionImage firebaseVisionImage=FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector firebaseVisionTextRecognizer= FirebaseVision.getInstance().getVisionTextDetector();
        firebaseVisionTextRecognizer.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                display_text(firebaseVisionText);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CameraActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
        }
    }

    private void display_text(FirebaseVisionText firebaseVisionText)
    {
        List<FirebaseVisionText.Block> blockList=firebaseVisionText.getBlocks();
        if(blockList.size()==0)
        {
            Toast.makeText(this, "No text recognised in image!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for(FirebaseVisionText.Block block: firebaseVisionText.getBlocks())
            {
                String text=block.getText();
                TranslateAPI translateAPI = new TranslateAPI(
                        Language.AUTO_DETECT,
                        Language.VIETNAMESE, text);
                final TextView text2=(TextView)findViewById(R.id.textViewrs);

                translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                    @Override
                    public void onSuccess(String translatedText) {
                        text2.setText(translatedText);
                    }

                    @Override
                    public void onFailure(String ErrorText) {

                    }
                });
                txt.setText(text);
            }
        }
    }
}