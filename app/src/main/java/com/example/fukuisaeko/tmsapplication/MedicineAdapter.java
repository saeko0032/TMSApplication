package com.example.fukuisaeko.tmsapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import static android.R.attr.animation;

/**
 * Created by fukuisaeko on 2017-08-07.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private List<Medicine> medicineList;
    private int numberOfRows;
    private Context context;

    public MedicineAdapter(List<Medicine> medicineList, Context context) {

        this.medicineList= medicineList;
        this.context = context;
    }

    class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView medicineTitle;
        LottieAnimationView favoriteView;

        MedicineViewHolder(View itemViews) {
            super(itemViews);
            medicineTitle = (TextView)itemView.findViewById(R.id.medicine_title);
            favoriteView = (LottieAnimationView) itemView.findViewById(R.id.animation_view);
        }

        public void bind(int index) {
            medicineTitle.setText(String.valueOf(medicineList.get(index).getMedicineName()));
            if(medicineList.get(index).isFavorite()) {
                favoriteView.playAnimation();
            } else {
                favoriteView.setProgress(0f);
            }
        }

    }

    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_list_rows, parent, false);
        MedicineViewHolder viewHolder = new MedicineViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MedicineViewHolder holder, final int position) {
        holder.bind(position);

        holder.favoriteView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(medicineList.get(position).isFavorite()) {
                    holder.favoriteView.setProgress(0f);
                    medicineList.get(position).setFavorite(false);
                } else {
                    holder.favoriteView.playAnimation();
                    medicineList.get(position).setFavorite(true);
                }
            }
        });
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

    @Override
    public int getItemCount() {
        return medicineList.size();
    }
}
