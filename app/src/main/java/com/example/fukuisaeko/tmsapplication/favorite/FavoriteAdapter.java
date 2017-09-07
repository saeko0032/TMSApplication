package com.example.fukuisaeko.tmsapplication.favorite;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fukuisaeko.tmsapplication.Medicine;
import com.example.fukuisaeko.tmsapplication.R;
import com.example.fukuisaeko.tmsapplication.detail.MedicineDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fukuisaeko on 2017-08-08.
 */

public class FavoriteAdapter  extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>{



    private List<String> favoriteList;
    private ArrayList<? extends Medicine> medicineList;
    private int numberOfRows;
    private Context context;

    public FavoriteAdapter(ArrayList<String> favoriteList, ArrayList<? extends Medicine> medicineList, Context context) {

        this.favoriteList= favoriteList;
        this.medicineList = medicineList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView medicineTitle;

        FavoriteViewHolder(View itemViews) {
            super(itemViews);
            medicineTitle = (TextView)itemView.findViewById(R.id.favoriteText);
        }

        public void bind(int index) {
            medicineTitle.setText(String.valueOf(favoriteList.get(index)));
            //favoriteView.setImageResource(medicineList.get(index).isFavorite());
        }

    }

    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favorite_medicine_list, parent, false);
        FavoriteAdapter.FavoriteViewHolder viewHolder = new FavoriteAdapter.FavoriteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.FavoriteViewHolder holder, final int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MedicineDetailActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Medicine tempMedicine = medicineList.get(position);
                i.putExtra("myObj",tempMedicine);
                context.startActivity(i);
            }
        });


    }
}
