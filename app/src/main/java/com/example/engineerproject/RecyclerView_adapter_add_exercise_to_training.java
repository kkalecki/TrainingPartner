package com.example.engineerproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerView_adapter_add_exercise_to_training extends RecyclerView.Adapter<RecyclerView_adapter_add_exercise_to_training.ViewHolder>{
    private static final String TAG = "RecyclerView_adapter";

    private ArrayList<String> mNames = new ArrayList<>();
    private Context mContext;

    public RecyclerView_adapter_add_exercise_to_training(Context mContext,ArrayList<String> mNames) {
        this.mNames = mNames;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   //wywolanie viewholder i ustawienie listy treningow
        Log.d(TAG, "onCreateViewHolder: onebody");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_training_item_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
         Log.d(TAG, "onBindViewHolder: called. onebody");

        holder.exercise_name.setText(mNames.get(position));    //     tekst
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {    //klikniecie ktoregos rodzaju cwiczen
            @Override
            public void onClick(View v) {

                //Context context = v.getContext();
                //Log.d(TAG, "onClick: clicked on " + mImagenames.get(position));
                //Intent intent = new Intent(context,amount_of_series_activity.class);
                //intent.putExtra("name",name);   //wyslanie imagename do nastepnej aktywnosci
                //context.startActivity(intent);

                //Toast.makeText(mContext,mImagenames.get(position),Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {   //trzymanie kazedgo elementu listy w pamieci


        public TextView exercise_name;
        public RelativeLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exercise_name = itemView.findViewById(R.id.textView1);
            parent_layout = itemView.findViewById(R.id.parent_layout1);
        }
    }
}
