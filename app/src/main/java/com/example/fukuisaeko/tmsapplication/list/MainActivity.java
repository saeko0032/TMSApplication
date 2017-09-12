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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fukuisaeko.tmsapplication.Medicine;
import com.example.fukuisaeko.tmsapplication.R;
import com.example.fukuisaeko.tmsapplication.favorite.FavoriteActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;
import com.turingtechnologies.materialscrollbar.MaterialScrollBar;

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
    private List<Medicine> orgMedicineList; //
    private int listSize = 15;
    ArrayList<String> favList;
    private MedicineAdapter adapter;
    private Spinner spinner;
    private String sortStr;
    private RecyclerView recyclerView;
    private SearchView mySearchView;

    private static final String FILE_NAME = "favorite";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // prepare header part
        // 1.spinner
        spinner = (Spinner) findViewById(R.id.order_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String order = (String) parent.getItemAtPosition(pos);
                if (order.equals("asc")) {
                    sortStr = "asc";
                    // sort list by asc order
                    Collections.sort(medicineList, new Comparator<Medicine>() {
                        @Override
                        public int compare(Medicine medicine1, Medicine medicine2) {
                            return medicine1.getMedicineName().compareTo(medicine2.getMedicineName());
                        }
                    });
                } else if (order.equals("desc")){
                    sortStr = "desc";
                    // sort list by desc order
                    Collections.sort(medicineList, new Comparator<Medicine>() {
                        @Override
                        public int compare(Medicine medicine1, Medicine medicine2) {
                            return medicine2.getMedicineName().compareTo(medicine1.getMedicineName());
                        }
                    });
                } else {
                    sortStr = "shape";
                    // sort list by shape order
                    Collections.sort(medicineList, new Comparator<Medicine>() {
                        @Override
                        public int compare(Medicine medicine1, Medicine medicine2) {
                            return medicine2.getImgUrlId() - medicine1.getImgUrlId();
                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // default is asc order
                Collections.sort(medicineList, new Comparator<Medicine>() {
                    @Override
                    public int compare(Medicine medicine1, Medicine medicine2) {
                        return medicine1.getMedicineName().compareTo(medicine2.getMedicineName());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        medicineList = new ArrayList<>();
        orgMedicineList = new ArrayList<>();

        // connect to firebase realtime database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("medicines");

        //prepareMedicineData();

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Medicine medicine = dataSnapshot.getValue(Medicine.class);
                // check
                String string = medicine.getMedicineName();
                FileInputStream inputStream;
                //medicine.setFavorite(false);
                    try {
                        inputStream = getApplicationContext().openFileInput(FILE_NAME);
                        String lineBuffer = null;
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        while((lineBuffer = reader.readLine()) != null) {
                            if (lineBuffer.equals(string)) {
                                medicine.setFavorite(true);
                                break;
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
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        ((DragScrollBar)findViewById(R.id.dragScrollBar)).setIndicator(new AlphabetIndicator(this), true);
//        MaterialScrollBar materialScrollBar = new MaterialScrollBar(getApplicationContext(), recyclerView);

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

    public void updateMedicineList(Medicine medicine) {
        for(int i = 0; i< medicineList.size(); i ++) {
            if (medicineList.get(i).getMedicineName().equals(medicine.getMedicineName())) {
                medicineList.set(i, medicine);
            }
        }
    }

    // navigation item
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

    private void prepareMedicineData() {
        // String medicineName, boolean isFavorite,
        //Medicine(String medicineName,
        Medicine medicine = new Medicine("Aimix HD","for high blood pressure\n", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "https://www.drugs.com/international/aimix-hd.html");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Almeta","Alimta is an anti-cancer chemotherapy drug.", R.drawable.medicine_type_c,
                false, "crash warnings", false, "combine warnings", false, "parantal warning", true, "lactation warning", "http://chemocare.com/chemotherapy/drug-info/alimta.aspx");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Isodin 10%","Isodin is used to relieve symptoms of anxiety", R.drawable.medicine_type_b,
                false, "crash warnings", false, "combine warnings", false, "parantal warning", false, "lactation warning", "http://www.ndrugs.com/?s=isodin");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Irbesartan","to treat high blood pressure", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "http://www.ndrugs.com/?s=irtra");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Endoxan"," to treat various cancerous diseases", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "https://www.myvmc.com/drugs/endoxan/");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Grash vista","to repair eye-rush", R.drawable.medicine_type_b,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "http://www.kawaihifuka.com/treatment_list/grash-vista/");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Clarithromycin","to treat bacterial infections affecting the skin", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "https://www.drugs.com/clarithromycin.html");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Claritin 10mg","to treat high blood pressure", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "https://www.drugs.com/claritin.html");
        mMessagesDatabaseReference.push().setValue(medicine);

        // modified pic
        medicine = new Medicine("Cymbalta Cupsle","to treat general anxiety disorder", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "http://www.webmd.com/drugs/2/drug-91491/cymbalta-oral/details");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Symproic"," the treatment of opioid-induced constipation (OIC) in adult patients", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "https://www.drugs.com/symproic.html");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Bakuta","to treat high blood pressure", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "http://mobile.medsfacts.com/report-BAKUTA-causing-ADRENAL%20INSUFFICIENCY.php");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Pirespa","used for the treatment of idiopathic pulmonary fibrosis ", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "http://evaluategroup.com/Universal/View.aspx?type=Story&id=176815");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Fluitran","to treat high blood pressure", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "hhttp://www.medicatione.com/?c=drug&s=fluitran");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("Fermeta","to treat skin trouble", R.drawable.medicine_type_c,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "https://www.drugs.com/international/fermate.html");
        mMessagesDatabaseReference.push().setValue(medicine);

        medicine = new Medicine("prednisolone","to treat conditions such as arthritis", R.drawable.medicine_type_a,
                false, "crash warnings", true, "combine warnings", false, "parantal warning", true, "lactation warning", "http://www.webmd.com/drugs/2/drug-6007-9383/prednisone-oral/prednisone-oral/details");
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
                if (listSize != medicineList.size()) {
                    for (Medicine medicine: orgMedicineList) {
                        medicineList.add(medicine);
                    }
                    // clear substract list data, otherwise might duplicate value
                    orgMedicineList.clear();
                }

                if(newText.equals("")) {
                    if (sortStr.equals("asc")) {
                        sortStr = "asc";
                        // sort list by asc order
                        Collections.sort(medicineList, new Comparator<Medicine>() {
                            @Override
                            public int compare(Medicine medicine1, Medicine medicine2) {
                                return medicine1.getMedicineName().compareTo(medicine2.getMedicineName());
                            }
                        });
                    } else if (sortStr.equals("desc")){
                        sortStr = "desc";
                        // sort list by desc order
                        Collections.sort(medicineList, new Comparator<Medicine>() {
                            @Override
                            public int compare(Medicine medicine1, Medicine medicine2) {
                                return medicine2.getMedicineName().compareTo(medicine1.getMedicineName());
                            }
                        });
                    } else {
                        sortStr = "shape";
                        // sort list by shape order
                        Collections.sort(medicineList, new Comparator<Medicine>() {
                            @Override
                            public int compare(Medicine medicine1, Medicine medicine2) {
                                return medicine2.getImgUrlId() - medicine1.getImgUrlId();
                            }
                        });
                    }
                    adapter.notifyDataSetChanged();
                    return false;
                }

                for(int i = medicineList.size() - 1; i > -1; i--) {
                    Medicine medicine = medicineList.get(i);
                    if (!medicine.getMedicineName().startsWith(newText)) {
                        medicineList.remove(i);
                        orgMedicineList.add(medicine);
                    }
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
