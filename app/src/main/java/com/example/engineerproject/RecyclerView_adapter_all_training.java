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

public class RecyclerView_adapter_all_training extends RecyclerView.Adapter<RecyclerView_adapter_all_training.ViewHolder> { //adaptacja elementu listy do recyclerview
    private static final String TAG = "RecyclerView_adapter_all_training";

    private ArrayList<String> training_names = new ArrayList<>();
    private Context mContext;

    public RecyclerView_adapter_all_training(Context mContext, ArrayList<String> training_names) {
        this.training_names = training_names;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   //wywolanie viewholder i ustawienie listy treningow
        //Log.d(TAG, "onCreateViewHolder: wywolanie viewholder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_item_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //Log.d(TAG, "onBindViewHolder: called.");

        /*Glide.with(mContext)
                .asBitmap()
                .load(mImage.get(position))
                .into(holder.image);  // obrazek
*/
        holder.imagename.setText(training_names.get(position));    //     tekst

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: clicked on " + mImagenames.get(position));
                int check=2;
                int pos=position;
                Context context = v.getContext();
                Intent intent = new Intent(context,show_training_activity.class);
                intent.putExtra("pos2",pos);
                intent.putExtra("check",check);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return training_names.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {   //trzymanie kazedgo elementu listy w pamieci

        CircleImageView image;
        TextView imagename;
        RelativeLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imagename = itemView.findViewById(R.id.textView1);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}