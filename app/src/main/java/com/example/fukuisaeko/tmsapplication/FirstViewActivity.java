package com.example.fukuisaeko.tmsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by fukuisaeko on 2017-08-07.
 */

public class FirstViewActivity extends AppCompatActivity {
    private ImageView startView;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.first_view);

        startView = (ImageView) findViewById(R.id.start);
        startView.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstViewActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
