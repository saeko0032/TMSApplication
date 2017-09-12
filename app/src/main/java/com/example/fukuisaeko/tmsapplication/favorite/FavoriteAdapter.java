package com.example.fukuisaeko.tmsapplication.favorite;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fukuisaeko.tmsapplication.Medicine;
import com.example.fukuisaeko.tmsapplication.R;
import com.example.fukuisaeko.tmsapplication.detail.MedicineDetailActivity;

import com.example.fukuisaeko.tmsapplication.itemhelper.ItemTouchHelperAdapter;
import com.example.fukuisaeko.tmsapplication.itemhelper.ItemTouchHelperViewHolder;
import com.example.fukuisaeko.tmsapplication.itemhelper.OnStartDragListener;
import com.example.fukuisaeko.tmsapplication.itemhelper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fukuisaeko on 2017-08-08.
 */

public class FavoriteAdapter  extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> implements ItemTouchHelperAdapter {

    private final OnStartDragListener mDragStartListener;
    private List<String> favoriteList;
    private ArrayList<? extends Medicine> medicineList;
    private int numberOfRows;
    private Context context;

    public FavoriteAdapter(ArrayList<String> favoriteList, ArrayList<? extends Medicine> medicineList, Context context, OnStartDragListener dragStartListener) {

        mDragStartListener = dragStartListener;
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

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        favoriteList.remove(position);
        notifyItemChanged(position);

    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView medicineTitle;
        ImageView medicineImage;

        FavoriteViewHolder(View itemViews) {
            super(itemViews);
            medicineTitle = (TextView)itemView.findViewById(R.id.favoriteText);
            medicineImage = (ImageView)itemView.findViewById(R.id.favoriteImage);
        }

        public void bind(int index) {
            String favoriteMedicine = favoriteList.get(index);
            medicineTitle.setText(String.valueOf(favoriteMedicine));
            for (Medicine medicine:medicineList) {
                if (favoriteMedicine.equals(medicine.getMedicineName())) {
                    medicineImage.setImageResource(medicineList.get(index).getImgUrlId());
                }
            }

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
    public void onBindViewHolder(final FavoriteAdapter.FavoriteViewHolder holder, final int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MedicineDetailActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String medicineName = favoriteList.get(position);
                for (Medicine medicine: medicineList) {
                    if (medicineName.equals(medicine.getMedicineName())) {
                        medicine.setFavorite(true);
                        i.putExtra("myObj",medicine);
                        context.startActivity(i);
                    }
                }
            }
        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });


    }
}
