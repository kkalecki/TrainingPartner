package com.example.engineerproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class exercises_activity extends AppCompatActivity {


    private static final String TAG = "exercises";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImages= new ArrayList<Integer>();
    public Integer check;
    public ArrayList<String> Names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_activity);
        check = getIntent().getIntExtra("check",0);
        Log.d("f", "exercises check : "+check);
        Names = getIntent().getStringArrayListExtra("names1");
        Log.d(TAG, "2 doszlo :"+Names);
        InitImageBitmaps();

    }




    private void InitImageBitmaps()
    {
        //Log.d(TAG, "InitImageBitmaps: Preparing bitmaps.");
        //mImagesURLS.add("https://www.reddit.com/r/PuppySmiles/comments/dz2lhd/i_think_he_likes_roadtrips/");
        mNames.add("Plecy");
        mImages.add(R.drawable.plecy);

        mNames.add("Klatka");
        mImages.add(R.drawable.klata);

        mNames.add("Nogi");
        mImages.add(R.drawable.nogi);

        mNames.add("Biceps");
        mImages.add(R.drawable.biceps);

        mNames.add("Triceps");
        mImages.add(R.drawable.triceps);

        mNames.add("Pośladki");
        mImages.add(R.drawable.posladki);

        mNames.add("Barki");
        mImages.add(R.drawable.barki);

        mNames.add("Cardio");
        mImages.add(R.drawable.cardio);
        Log.d(TAG, "InitImageBitmaps: ");
        InitRecyclerView();
    }

    private void InitRecyclerView()
    {
        Log.d(TAG, "InitRecyclerView: InitRecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerView_adapter_exercises adapter = new RecyclerView_adapter_exercises(this,mNames,mImages,Names,check);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
