package com.example.fukuisaeko.tmsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Medicine> medicineList = new ArrayList<>();
    ArrayList<String> favList;
    private MedicineAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView mySearchView;

    private TextView mTextMessage;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

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

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Medicine medicine = dataSnapshot.getValue(Medicine.class);
                medicineList.add(medicine);
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

        prepareMedicineData();
        recyclerView = (RecyclerView) findViewById(R.id.medicine_recycler);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MedicineAdapter(medicineList, this.getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private void prepareMedicineData() {
        // String medicineName, boolean isFavorite,
        Medicine medicine = new Medicine("Medicine1", false, R.drawable.medicine1,
                false, null, true, null, false, null, true, null);
        medicineList.add(medicine);
        medicine = new Medicine("Medicine2", false, R.drawable.medicine1,
                false, null, false, null, true, null, false, null);
        medicineList.add(medicine);
        medicine = new Medicine("Medicine3", false, R.drawable.medicine1,
                false, null, true, null, false, null, true, null);
        medicineList.add(medicine);
        medicine = new Medicine("Medicine4", true, R.drawable.medicine1,
                false, null, true, null, true, null, true, null);
        medicineList.add(medicine);

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
