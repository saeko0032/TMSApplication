package com.example.fukuisaeko.tmsapplication.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fukuisaeko.tmsapplication.Medicine;
import com.example.fukuisaeko.tmsapplication.R;
import com.example.fukuisaeko.tmsapplication.favorite.FavoriteActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Medicine> medicineList;
    ArrayList<String> favList;
    private MedicineAdapter adapter;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private SearchView mySearchView;

    private TextView mTextMessage;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    public List<Medicine> getMedicineList() {
        return this.medicineList;
    }

    public void updateMedicineList(Medicine medicine) {
        for(int i = 0; i< medicineList.size(); i ++) {
            if (medicineList.get(i).getMedicineName().equals(medicine.getMedicineName())) {
                medicineList.set(i, medicine);
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                  //  mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    favList = new ArrayList<>();
                    Intent i = new Intent(MainActivity.this, FavoriteActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if(medicineList.size() != 0) {
                        for(Medicine medicine:medicineList) {
                            if (medicine.isFavorite()) {
                                favList.add(medicine.getMedicineName());
                            }
                        }
                    }

                    i.putStringArrayListExtra("favList", favList);
                    i.putParcelableArrayListExtra("medicineList", (ArrayList<? extends Parcelable>) medicineList);
                    startActivity(i);
                    return true;
                case R.id.navigation_notifications:
                //    mTextMessage.setText(R.string.title_share);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner)findViewById(R.id.order_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String order = (String)parent.getItemAtPosition(pos);
                if (order.equals("asc")) {
                    Collections.sort(medicineList, new Comparator<Medicine>() {
                        @Override
                        public int compare(Medicine medicine, Medicine t1) {
                            return medicine.getMedicineName().compareTo(t1.getMedicineName());
                        }
                    });
                    adapter.notifyDataSetChanged();
                } else {
                    //medicineList.
                    Collections.sort(medicineList, new Comparator<Medicine>() {
                        @Override
                        public int compare(Medicine medicine, Medicine t1) {
                            return t1.getMedicineName().compareTo(medicine.getMedicineName());
                        }});
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        medicineList = new ArrayList<>();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("medicines");

        //prepareMedicineData();

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Medicine medicine = dataSnapshot.getValue(Medicine.class);
                // check
                String filename = "favorite";
                String string = medicine.getMedicineName();
                File file = new File(getApplicationContext().getFilesDir(), filename);
                FileInputStream inputStream;
                //medicine.setFavorite(false);

                    try {
                        inputStream = getApplicationContext().openFileInput(filename);
                        String lineBuffer = null;
                        String tempStr = string + "\n";
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        while((lineBuffer = reader.readLine()) != null) {
                            if (lineBuffer.equals(string)) {
                                medicine.setFavorite(true);
                                break;
                               // updateMedicineList(medicine);
                            } else {
                                medicine.setFavorite(false);
                            }
                        }
                        inputStream.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                medicineList.add(medicine);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);

       // prepareMedicineData();
        recyclerView = (RecyclerView) findViewById(R.id.medicine_recycler);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MedicineAdapter(medicineList, this.getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        String filename = "favorite";
        File file = new File(getApplicationContext().getFilesDir(), filename);
        FileInputStream inputStream;
        List<String> favMedicneName = new ArrayList<>();
        try {
            inputStream = getApplicationContext().openFileInput(filename);
            String lineBuffer = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            while((lineBuffer = reader.readLine()) != null) {
                favMedicneName.add(lineBuffer);
            }
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Medicine medicine: medicineList) {
            for(String name: favMedicneName) {
                if (name.equals(medicine.getMedicineName())) {
                    medicine.setFavorite(true);
                    break;
                } else {
                    medicine.setFavorite(false);
                }
            }
            updateMedicineList(medicine);
        }

        adapter.notifyDataSetChanged();
    }

    private void prepareMedicineData() {
        // String medicineName, boolean isFavorite,
        //Medicine(String medicineName,
        Medicine medicine = new Medicine("Aimix HD","アンジオテンシンIIのタイプ1受容体に対して競合的に拮抗するとともに、細胞内へのCaイオンの流入を減少させて末梢血管の平滑筋を弛緩させることにより、血圧を低下させます。\n" +
                "通常、高血圧症の治療に用いられます。", R.drawable.medicine1,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "https://www.shionogi.co.jp/med/download.php?h=3d5008fc84a74d75624a8e858225c84b");
       // medicineList.add(medicine);
        mMessagesDatabaseReference.push().setValue(medicine);
        medicine = new Medicine("Medicine2","Description sample", R.drawable.medicine1,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "https://www.shionogi.co.jp/med/download.php?h=3d5008fc84a74d75624a8e858225c84b");

        //  medicineList.add(medicine);
        mMessagesDatabaseReference.push().setValue(medicine);
        medicine = new Medicine("Medicine3","Description sample 3", R.drawable.medicine1,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "https://www.shionogi.co.jp/med/download.php?h=3d5008fc84a74d75624a8e858225c84b");

        //   medicineList.add(medicine);
        mMessagesDatabaseReference.push().setValue(medicine);
        medicine = new Medicine("Medicine4","Description sample 4", R.drawable.medicine1,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "https://www.shionogi.co.jp/med/download.php?h=3d5008fc84a74d75624a8e858225c84b");

        //medicineList.add(medicine);
        mMessagesDatabaseReference.push().setValue(medicine);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Set Menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem menuItem = menu.findItem(R.id.toolbar_menu_search);

        mySearchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        // whether display Magnifying Glass Icon at first
        mySearchView.setIconifiedByDefault(true);

        // whether display Submit Button
        mySearchView.setSubmitButtonEnabled(false);

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
