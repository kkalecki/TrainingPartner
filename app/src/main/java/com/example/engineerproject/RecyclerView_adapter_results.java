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

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerView_adapter_results extends RecyclerView.Adapter<RecyclerView_adapter_results.ViewHolder>{

    private ArrayList<Integer> series = new ArrayList<>();
    private ArrayList<Integer> reps = new ArrayList<>();
    private ArrayList<Integer> weights = new ArrayList<>();
    private ArrayList<String> exercise_names = new ArrayList<>();
    private Context mContext;

    public RecyclerView_adapter_results(Context mContext,ArrayList<String> exercise_names,ArrayList<Integer> series,ArrayList<Integer> reps,ArrayList<Integer> weights) {
        this.exercise_names = exercise_names;
        this.series = series;
        this.reps = reps;
        this.weights = weights;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView_adapter_results.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   //wywolanie viewholder i ustawienie listy treningow
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item_layout,parent,false);
        RecyclerView_adapter_results.ViewHolder holder = new RecyclerView_adapter_results.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_adapter_results.ViewHolder holder, final int position) {
        // Log.d(TAG, "onBindViewHolder: called.");
        holder.exercise_names.setText(exercise_names.get(position));
        holder.series.setText("seria :"+series.get(position));    //     tekst
        holder.reps.setText("ilość powtórzeń : "+reps.get(position));
        holder.weight.setText("ciężar :"+weights.get(position)+" kg");
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {    //klikniecie ktoregos rodzaju cwiczen
            @Override
            public void onClick(View v) {



            }
        });

    }

    @Override
    public int getItemCount() {
        return series.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {   //trzymanie kazedgo elementu listy w pamieci

        public TextView series;
        public TextView reps;
        public TextView weight;
        public TextView exercise_names;
        public RelativeLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            series = itemView.findViewById(R.id.tv1);
            reps = itemView.findViewById(R.id.tv2);
            weight = itemView.findViewById(R.id.tv3);
            exercise_names = itemView.findViewById(R.id.tv0);
            parent_layout = itemView.findViewById(R.id.parent_layout2);
        }
    }
}
