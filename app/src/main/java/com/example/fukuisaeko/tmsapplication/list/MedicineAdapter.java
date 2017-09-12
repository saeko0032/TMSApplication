package com.example.fukuisaeko.tmsapplication.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fukuisaeko.tmsapplication.Medicine;
import com.example.fukuisaeko.tmsapplication.R;
import com.example.fukuisaeko.tmsapplication.detail.MedicineDetailActivity;
//import com.turingtechnologies.materialscrollbar.INameableAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by fukuisaeko on 2017-08-07.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {//implements INameableAdapter {

    private List<Medicine> medicineList;
    private int numberOfRows;
    private Context context;

    public MedicineAdapter(List<Medicine> medicineList, Context context) {

        this.medicineList= medicineList;
        this.context = context;
    }

//    @Override
//    public Character getCharacterForElement(int element) {
//        Medicine medicine = medicineList.get(element);
//        Character indexAlpha = medicine.getMedicineName().charAt(0);
//        return indexAlpha;
//    }

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
                String filename = "favorite";
                String string = medicineList.get(position).getMedicineName();
                File file = new File(context.getFilesDir(), filename);
                FileInputStream inputStream;
                FileOutputStream outputStream;

                if(medicineList.get(position).isFavorite()) {
                    holder.favoriteView.setProgress(0f);
                    medicineList.get(position).setFavorite(false);
                    StringBuffer stringBuffer = new StringBuffer("");
                    try {
                        inputStream = context.openFileInput(filename);
                        String lineBuffer = null;
                        String tempStr = string + "\n";
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        while((lineBuffer = reader.readLine()) != null) {
                            if (!lineBuffer.equals(string)) {
                                stringBuffer.append(lineBuffer+"\n");
                            }
                        }
                        inputStream.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(stringBuffer.toString().getBytes());

                        outputStream.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    holder.favoriteView.playAnimation();
                    medicineList.get(position).setFavorite(true);
                    try {
                        String tempStr = string + "\n";
                        outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
                        outputStream.write(tempStr.getBytes());

                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
