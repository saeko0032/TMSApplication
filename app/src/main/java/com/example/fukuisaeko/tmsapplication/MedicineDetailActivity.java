package com.example.fukuisaeko.tmsapplication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebViewFragment;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.w3c.dom.Text;

/**
 * Created by fukuisaeko on 2017-08-07.
 */

public class MedicineDetailActivity extends FragmentActivity {
    private TextView medicineName;
    private TextView medicineDescription;
    private ImageView combineImage;
    private ImageView crashImage;
    private ImageView parentImage;
    private ImageView lactationImage;
    LottieAnimationView favoriteView;
    private Button referenceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        Intent intent = getIntent();
        final Medicine medicine = (Medicine) intent.getSerializableExtra("myObj");

        medicineName = (TextView) findViewById(R.id.medicine_name);
        medicineDescription = (TextView) findViewById(R.id.medicine_description_textView);
        combineImage = (ImageView) findViewById(R.id.combineImage);
        crashImage = (ImageView) findViewById(R.id.crashImage);
        parentImage = (ImageView) findViewById(R.id.parentalImage);
        lactationImage = (ImageView) findViewById(R.id.lactarentImage);
        favoriteView = (LottieAnimationView) findViewById(R.id.detail_animationView);
        referenceBtn = (Button) findViewById(R.id.reference_btn);

        medicineName.setText(medicine.getMedicineName());
        medicineDescription.setText(medicine.getMedicineDescription());
        if (!medicine.isAbleCrash()) {
            crashImage.setImageResource(R.drawable.ic_close_black_24dp);
        }
        if (!medicine.isAbleCombine()) {
            combineImage.setImageResource(R.drawable.ic_close_black_24dp);
        }
        if (!medicine.isForLactation()) {
            lactationImage.setImageResource(R.drawable.ic_close_black_24dp);

        }
        if (!medicine.isForParenatal()) {
            parentImage.setImageResource(R.drawable.ic_close_black_24dp);
        }
        if (medicine.isFavorite()) {
            favoriteView.playAnimation();
        } else {
            favoriteView.setProgress(0f);
        }

        referenceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MedicineInfoFragment miFragment = new MedicineInfoFragment();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


                transaction.replace(R.id.detail_frame, miFragment);

                // Commit the transaction
                transaction.commit();


            }
        });
    }

}
