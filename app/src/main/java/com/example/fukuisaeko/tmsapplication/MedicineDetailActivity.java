package com.example.fukuisaeko.tmsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.w3c.dom.Text;

/**
 * Created by fukuisaeko on 2017-08-07.
 */

public class MedicineDetailActivity extends AppCompatActivity {
    private TextView medicineName;
    private TextView medicineDescription;
    private ImageView combineImage;
    private ImageView crashImage;
    private ImageView parentImage;
    private ImageView lactationImage;
    LottieAnimationView favoriteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        Intent intent = getIntent();
        Medicine medicine = (Medicine) intent.getSerializableExtra("myObj");

        medicineName = (TextView)findViewById(R.id.medicine_name);
        medicineDescription = (TextView)findViewById(R.id.medicine_description_textView);
        combineImage = (ImageView)findViewById(R.id.combineImage);
        crashImage = (ImageView)findViewById(R.id.crashImage);
        parentImage = (ImageView)findViewById(R.id.parentalImage);
        lactationImage = (ImageView)findViewById(R.id.lactarentImage);
        favoriteView = (LottieAnimationView)findViewById(R.id.detail_animationView);

        medicineName.setText(medicine.getMedicineName());
        medicineDescription.setText(medicine.getMedicineDescription());
        if(!medicine.isAbleCrash()) {
            crashImage.setImageResource(R.drawable.ic_close_black_24dp);
        }
        if(!medicine.isForLactation()) {
            lactationImage.setImageResource(R.drawable.ic_close_black_24dp);

        }
        if(!medicine.isForParenatal()) {
            parentImage.setImageResource(R.drawable.ic_close_black_24dp);
        }
        if(medicine.isFavorite()) {
            favoriteView.playAnimation();
        } else {
            favoriteView.setProgress(0f);
        }
    }
}
