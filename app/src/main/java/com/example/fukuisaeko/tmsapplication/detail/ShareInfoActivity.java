package com.example.fukuisaeko.tmsapplication.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.fukuisaeko.tmsapplication.Medicine;
import com.example.fukuisaeko.tmsapplication.R;
import com.example.fukuisaeko.tmsapplication.list.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStreamWriter;

/**
 * Created by saeko on 9/14/2017.
 */

public class ShareInfoActivity extends AppCompatActivity {
    EditText medicineName;
    EditText medicineDescription;
    Switch ableCrash;
    Switch ableCombine;
    Switch isForParenatal;
    Switch isForLactation;
    EditText medicineReference;
    EditText medicineComments;
    Button sendBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareinfo);

        medicineName = (EditText)findViewById(R.id.medicine_name_edit);
        medicineDescription = (EditText)findViewById(R.id.medicine_description_edit);
        ableCrash = (Switch)findViewById(R.id.ableCrash);
        ableCombine = (Switch)findViewById(R.id.ableCombine);
        isForParenatal = (Switch)findViewById(R.id.isForParenatal);
        isForLactation = (Switch)findViewById(R.id.isForLactation);
        medicineReference = (EditText)findViewById(R.id.medicine_reference_edit);
        medicineComments = (EditText)findViewById(R.id.medicine_comments_edit);
        sendBtn = (Button)findViewById(R.id.send_info_btn);


        sendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put("medicineName",medicineName.getText().toString());
                    jsonObj.put("medicineDescription",medicineDescription.getText().toString());
                    jsonObj.put("ableCrash",ableCrash.isChecked());
                    jsonObj.put("ableCombine", ableCombine.isChecked());
                    jsonObj.put("forParental", isForParenatal.isChecked());
                    jsonObj.put("forLactation", isForLactation.isChecked());
                    jsonObj.put("infoUrl", medicineReference.getText().toString());
                    // default value
                    jsonObj.put("imgUrlId", R.drawable.medicine_type_a);
                    jsonObj.put("isFavorite", false);
                    jsonObj.put("crashWarnings", "");
                    jsonObj.put("combineWarnings", "");
                    jsonObj.put("parenatalWarnings", "");
                    jsonObj.put("lactationWarnings", "");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"tmsappmedicine@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Medicine info");
                intent.putExtra(Intent.EXTRA_TEXT, jsonObj.toString() + "\n" + medicineComments.getText().toString());

                startActivity(Intent.createChooser(intent, "Send Email"));

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // clear area
        medicineName.setText("");
        medicineDescription.setText("");
        ableCrash.setChecked(true);
        ableCombine.setChecked(true);
        isForLactation.setChecked(true);
        isForParenatal.setChecked(true);
        medicineReference.setText("");
        medicineComments.setText("");
    }
}
