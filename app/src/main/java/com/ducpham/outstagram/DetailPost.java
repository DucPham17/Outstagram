package com.ducpham.outstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailPost extends AppCompatActivity {
    ImageView dataPictureDetail;
    ImageView heartDetail;
    ImageView commentDetail;
    ImageView shareDetail;
    ImageView threedotDetail;
    ImageView saveDetail;
    TextView captionDetail;
    TextView timeDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        dataPictureDetail = findViewById(R.id.dataPictureDetail);
        heartDetail = findViewById(R.id.heartDetail);
        commentDetail = findViewById(R.id.commentDetail);
        shareDetail = findViewById(R.id.shareDetail);
        threedotDetail = findViewById(R.id.threedotDetail);
        saveDetail = findViewById(R.id.saveDetail);
        captionDetail = findViewById(R.id.captionDetail);
        timeDetail = findViewById(R.id.timeDetail);

        Glide.with(this).asBitmap().load(R.drawable.threedot).into(threedotDetail);
        Glide.with(this).asBitmap().load(R.mipmap.heart).into(heartDetail);
        Glide.with(this).asBitmap().load(R.mipmap.paper).into(shareDetail);
        Glide.with(this).asBitmap().load(R.mipmap.bubble).into(commentDetail);
        Glide.with(this).asBitmap().load(R.mipmap.save).into(saveDetail);

        String des = getIntent().getStringExtra("des");
        String time = getIntent().getStringExtra("date");
        String image = getIntent().getStringExtra("image");

        Glide.with(this).asBitmap().load(image).into(dataPictureDetail);
        captionDetail.setText(des);
        timeDetail.setText(time);
    }
}