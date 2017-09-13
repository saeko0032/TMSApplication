package com.example.fukuisaeko.tmsapplication.favorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.fukuisaeko.tmsapplication.Medicine;
import com.example.fukuisaeko.tmsapplication.R;
import com.example.fukuisaeko.tmsapplication.itemhelper.OnStartDragListener;
import com.example.fukuisaeko.tmsapplication.itemhelper.SimpleItemTouchHelperCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by fukuisaeko on 2017-08-08.
 */

public class FavoriteActivity extends AppCompatActivity implements OnStartDragListener {
    private ItemTouchHelper mItemTouchHelper;
    ArrayList<Medicine> favoriteList;
    ArrayList<? extends Medicine> medicineList;
    private FavoriteAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstaneState) {
        super.onCreate(savedInstaneState);
        setContentView(R.layout.favorite_view);
        recyclerView = (RecyclerView) findViewById(R.id.favorite_recycler);
        favoriteList = new ArrayList<>();

        Intent intent = getIntent();
        medicineList = intent.getParcelableArrayListExtra("medicineList");

            String filename = "favorite";
            File file = new File(getApplicationContext().getFilesDir(), filename);
            FileInputStream inputStream;
            FileOutputStream outputStream;
            StringBuffer stringBuffer = new StringBuffer("");
        try {
            inputStream = getApplicationContext().openFileInput(filename);
            String lineBuffer = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((lineBuffer = reader.readLine()) != null) {
                for (Medicine medicine : medicineList) {
                    if (medicine.isFavorite()) {
                        String string = medicine.getMedicineName();
                        if (lineBuffer.equals(string)) {
                            favoriteList.add(medicine);
                            break;
                        }
                    }
                }
            }
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayoutManager linearMng = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearMng);

        adapter = new FavoriteAdapter(favoriteList, this.getApplicationContext(), this);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    protected void onStop() {
        super.onStop();

        String filename = "favorite";
        File file = new File(getApplicationContext().getFilesDir(), filename);
        FileInputStream inputStream;
        FileOutputStream outputStream;
        // update favolist order by user custom
        StringBuffer stringBuffer = new StringBuffer("");
        for (Medicine medicine:favoriteList) {
            stringBuffer.append(medicine.getMedicineName() + "\n");
        }
        try {
            outputStream = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(stringBuffer.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        favoriteList.clear();
        String filename = "favorite";
        File file = new File(getApplicationContext().getFilesDir(), filename);
        FileInputStream inputStream;
        FileOutputStream outputStream;
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            inputStream = getApplicationContext().openFileInput(filename);
            String lineBuffer = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((lineBuffer = reader.readLine()) != null) {
                for (Medicine medicine : medicineList) {
                    if (medicine.isFavorite()) {
                        String string = medicine.getMedicineName();
                        if (lineBuffer.equals(string)) {
                            favoriteList.add(medicine);
                            break;
                        }
                    }
                }
            }
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }
}
