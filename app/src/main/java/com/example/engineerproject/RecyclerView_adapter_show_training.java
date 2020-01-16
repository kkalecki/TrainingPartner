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

public class RecyclerView_adapter_show_training extends RecyclerView.Adapter<RecyclerView_adapter_show_training.ViewHolder>{

    private ArrayList<String> mNames = new ArrayList<>();
    private Context mContext;

    public RecyclerView_adapter_show_training(Context mContext,ArrayList<String> Names) {
        mNames = Names;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView_adapter_show_training.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   //wywolanie viewholder i ustawienie listy treningow
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_training_item_layout,parent,false);
        RecyclerView_adapter_show_training.ViewHolder holder = new RecyclerView_adapter_show_training.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_adapter_show_training.ViewHolder holder, final int position) {
        // Log.d(TAG, "onBindViewHolder: called.");

        holder.name.setText(mNames.get(position));    //     tekst
        //holder.image.setImageResource(mImage.get(position));    // zdjecie
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {    //klikniecie ktoregos rodzaju cwiczen
            @Override
            public void onClick(View v) {



            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {   //trzymanie kazedgo elementu listy w pamieci

        public TextView name;
        public RelativeLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView1);
            parent_layout = itemView.findViewById(R.id.parent_layout1);
        }
    }
}
