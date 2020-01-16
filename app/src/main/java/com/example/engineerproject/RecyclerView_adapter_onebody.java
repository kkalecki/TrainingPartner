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

public class RecyclerView_adapter_onebody extends RecyclerView.Adapter<RecyclerView_adapter_onebody.ViewHolder>{
    private static final String TAG = "RecyclerView_adapter";
    public String name;
    private ArrayList<String> mImagenames = new ArrayList<>();
    private ArrayList<Integer> mImage = new ArrayList<>();
    private ArrayList<String> mmNames = new ArrayList<>();
    private Context mContext;
    private Integer check;  //sprawdzam z jakiej aktywnosci przyszedlem

    public RecyclerView_adapter_onebody(Context mContext, ArrayList<String> mImagenames, ArrayList<Integer> mImage,ArrayList<String> mNames,Integer check) {
        this.mImagenames = mImagenames;
        this.mImage = mImage;
        Log.d(TAG, "3. Recycler doszlo "+mNames);
        mmNames = mNames;
        this.mContext = mContext;
        this.check = check;
    }

    @NonNull
    @Override
    public RecyclerView_adapter_onebody.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   //wywolanie viewholder i ustawienie listy treningow
        Log.d(TAG, "onCreateViewHolder: onebody");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_item_layout,parent,false);
        RecyclerView_adapter_onebody.ViewHolder holder = new RecyclerView_adapter_onebody.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_adapter_onebody.ViewHolder holder, final int position) {
        // Log.d(TAG, "onBindViewHolder: called.");


        Log.d(TAG, "onBindViewHolder: onebody");
        holder.imagename.setText(mImagenames.get(position));    //     tekst
        //holder.image.setImageResource(mImage.get(position));    // zdjecie
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {    //klikniecie ktoregos rodzaju cwiczen
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                //Log.d(TAG, "onClick: clicked on " + mImagenames.get(position));
                name=mImagenames.get(position); //zapisanie imagename do zmiennej

                Log.d(TAG, "onClick: onebody name = " +name);
                Log.d(TAG, "3. recycler wysylam :"+mmNames);
                Log.d(TAG, "onClick: check : "+check);
                if(check==1)
                {
                    Intent i = new Intent(context, draw_diagram_activity.class);
                    i.putExtra("name", name);
                    context.startActivity(i);
                }
                else {
                    Intent intent = new Intent(context, amount_of_series_activity.class);
                    intent.putStringArrayListExtra("names", mmNames);
                    intent.putExtra("name", name);   //wyslanie imagename do nastepnej aktywnosci
                    context.startActivity(intent);
                }

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
            image = itemView.findViewById(R.id.image);
            imagename = itemView.findViewById(R.id.textView1);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
