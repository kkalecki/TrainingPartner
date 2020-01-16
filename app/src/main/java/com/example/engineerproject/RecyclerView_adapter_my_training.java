package com.example.engineerproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerView_adapter_my_training extends RecyclerView.Adapter<RecyclerView_adapter_my_training.ViewHolder> { //adaptacja elementu listy do recyclerview
    private static final String TAG = "RecyclerView_adapter";
    public String name;
    public Integer pos;
    private ArrayList<String> mImagenames = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();
    private Context mContext;

    public RecyclerView_adapter_my_training(Context mContext, ArrayList<String> mImagenames, ArrayList<String> mImage) {

        this.mImagenames = mImagenames;
        this.mImage = mImage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   //wywolanie viewholder i ustawienie listy treningow
        Log.d(TAG, "onCreateViewHolder: wywolanie viewholder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_item_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Log.d(TAG, "onBindViewHolder: called.");


        holder.imagename.setText(mImagenames.get(position));    //     tekst
        //holder.image.setImageResource(mImage.get(position));    // zdjecie
        Log.d(TAG, "onBindViewHolder: "+mImagenames);
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {    //klikniecie ktoregos rodzaju cwiczen
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Log.d(TAG, "onClick: clicked on " + mImagenames.get(position));
                pos = position;
                Log.d(TAG, "onClick: pos :"+pos);
                //name=mImagenames.get(position); //zapisanie imagename do zmiennej
                Intent intent = new Intent(context,show_training_activity.class);
                int check=1;
                intent.putExtra("pos",pos);   //wyslanie pozycji do nastepnej aktywnosci
                intent.putExtra("check",check);
                context.startActivity(intent);

                //Toast.makeText(mContext,mImagenames.get(position),Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mImagenames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {   //trzymanie kazedgo elementu listy w pamieci

        public CircleImageView image;
        public TextView imagename;
        public RelativeLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: wewnatrz viewholder");
            image = itemView.findViewById(R.id.image);
            imagename = itemView.findViewById(R.id.textView1);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}