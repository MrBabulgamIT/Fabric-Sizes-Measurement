package com.example.fabricsizemeasure;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ImageAccess extends AppCompatActivity {
    Button btn_take_image, btn_load_image;
    final static String message_key = "message.message"; //connects 2 messages

    public static File image;
    public static String mcurpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageaccess);


        btn_take_image =  findViewById(R.id.btn_take_image);
        btn_load_image =  findViewById(R.id.btn_load_image);

        ShapeDrawable shapedrawable = new ShapeDrawable();
        shapedrawable.setShape(new RectShape());
        shapedrawable.getPaint().setColor(Color.LTGRAY);
        shapedrawable.getPaint().setStrokeWidth(10f);
        shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
        btn_take_image.setBackground(shapedrawable);
        btn_load_image.setBackground(shapedrawable);
        btn_take_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);

            }

        });

        btn_load_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

                    //Starting activity (ImageViewActivity in my code) to preview image
                    Intent intent = new Intent(this, MeasureActivity.class);
                    intent.putExtra("BitmapImage", photo);
                    startActivity(intent);


                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {

                    if (data.getData() != null) {
                        Uri imageUri = data.getData();
                        //Starting activity (ImageViewActivity in my code) to preview image
                        Intent intent = new Intent(this, MeasureActivity.class);
                        intent.putExtra("ImageUri", imageUri.toString());
                        startActivity(intent);
                    }
                }
                break;
        }
    }
}
