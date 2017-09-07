package com.example.fukuisaeko.tmsapplication.detail;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fukuisaeko.tmsapplication.MainActivity;
import com.example.fukuisaeko.tmsapplication.Medicine;
import com.example.fukuisaeko.tmsapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

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
        favoriteView.setOnClickListener(new View.OnClickListener(){
            String filename = "favorite";
            String string = medicine.getMedicineName();
            File file = new File(getApplicationContext().getFilesDir(), filename);
            FileInputStream inputStream;
            FileOutputStream outputStream;

            @Override
            public void onClick(View view) {

                if(medicine.isFavorite()) {
                    favoriteView.setProgress(0f);


                    medicine.setFavorite(false);
                    StringBuffer stringBuffer = new StringBuffer("");
                    try {
                        inputStream = getApplicationContext().openFileInput(filename);
                        String lineBuffer = null;
                        String tempStr = string + "\n";
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        while((lineBuffer = reader.readLine()) != null) {
                            if (!lineBuffer.equals(string)) {
                                stringBuffer.append(lineBuffer+"\n");
                            }
                        }
                        inputStream.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        outputStream = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(stringBuffer.toString().getBytes());

                        outputStream.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    favoriteView.playAnimation();
                    medicine.setFavorite(true);
                    try {
                        String tempStr = string + "\n";
                        outputStream = getApplicationContext().openFileOutput(filename, Context.MODE_APPEND);
                        outputStream.write(tempStr.getBytes());

                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("key", "Hello World");
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
            }
        });

        referenceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MedicineInfoFragment wvf = new MedicineInfoFragment("https://www.drugs.com/international/aimix-hd.html");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.detail_frame, wvf);
                ft.commit();
            }
        });
    }

}
