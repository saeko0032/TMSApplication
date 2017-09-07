package com.example.fukuisaeko.tmsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.AppLaunchChecker;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.fukuisaeko.tmsapplication.list.MainActivity;

/**
 * Created by SF on 2017-08-07.
 * Reference:http://blog.techium.jp/entry/2016/05/30/090000
 * This class handle whether users use app forst class or not.
 * If it's first time for user, user can see firstview
 */
public class FirstViewActivity extends AppCompatActivity {
    private ImageView startView;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.first_view);

        if (AppLaunchChecker.hasStartedFromLauncher(this)) {
            // not 1st time
            // go to medicine list view directory
            Intent intent = new Intent(FirstViewActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            // 1st time
            //set Tutorial view
            startView = (ImageView) findViewById(R.id.start);
            startView.setOnClickListener(new View.OnClickListener(){


                @Override
                public void onClick(View view) {
                    // if user click screen(anywhere), they can go next screen
                    Intent intent = new Intent(FirstViewActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
