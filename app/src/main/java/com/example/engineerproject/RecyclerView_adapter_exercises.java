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

public class RecyclerView_adapter_exercises extends RecyclerView.Adapter<RecyclerView_adapter_exercises.ViewHolder> { //adaptacja elementu listy do recyclerview
    private static final String TAG = "RecyclerView_adapter";
    public String name;
    private ArrayList<String> mImagenames = new ArrayList<>();
    private ArrayList<Integer> mImage = new ArrayList<>();
    private ArrayList<Integer> mNames = new ArrayList<>();
    private ArrayList<String> mmNames = new ArrayList<>();
    private Context mContext;
    private int check;

    public RecyclerView_adapter_exercises(Context mContext, ArrayList<String> mImagenames, ArrayList<Integer> mImage,ArrayList<String>Names,Integer check) {
        this.mImagenames = mImagenames;

        Log.d(TAG, "2 recycler doszlo :"+Names);
        this.mImage = mImage;
        this.mContext = mContext;
        mmNames = Names;
        this.check = check;
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
        holder.image.setImageResource(mImage.get(position));    // zdjecie
        Log.d(TAG, "onBindViewHolder: "+mImagenames);
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {    //klikniecie ktoregos rodzaju cwiczen
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Log.d(TAG, "onClick: clicked on " + mImagenames.get(position));
                name=mImagenames.get(position); //zapisanie imagename do zmiennej
                Intent intent = new Intent(context,show_onebody_exercise.class);
                Log.d(TAG, "2 recycler wyslij :"+mmNames);
                intent.putExtra("check",check); //wywylam dana sprawdzajaca z jakiej aktywnosci przyszedlem tutaj i wysylam ja dalej
                Log.d(TAG, "onClick: check"+check);
                intent.putExtra("name",name);   //wyslanie imagename do nastepnej aktywnosci
                intent.putStringArrayListExtra("names",mmNames);
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