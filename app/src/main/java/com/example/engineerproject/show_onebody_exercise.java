package com.example.engineerproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class show_onebody_exercise extends AppCompatActivity {

    //private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImages= new ArrayList<Integer>();
    public String name;
    public TextView tv1;
    DatabaseReference databaseexercises;
    private ArrayList<String> mExercises = new ArrayList<>();
    private static final String TAG = "onebody";
    private ArrayList<String> mName = new ArrayList<>();
    private Integer check;
    private FirebaseAuth firebaseAuth;
    FloatingActionMenu fam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_onebody_exercise);
        Log.d(TAG, "3. Doszlo :"+getIntent().getStringArrayListExtra("names"));
        name = getIntent().getStringExtra("name");
        firebaseAuth = FirebaseAuth.getInstance();
        mName = getIntent().getStringArrayListExtra("names");
        check = getIntent().getIntExtra("check",0);
        fam = findViewById(R.id.fabmenu);


        switch(name)
        {
            case "Plecy":
            {
                databaseexercises = FirebaseDatabase.getInstance().getReference("cwiczenia/plecy");
                break;
            }
            case "Klatka":
            {
                databaseexercises = FirebaseDatabase.getInstance().getReference("cwiczenia/klatka");
                break;
            }
            case "Nogi":
            {
                databaseexercises = FirebaseDatabase.getInstance().getReference("cwiczenia/nogi");
                break;
            }
            case "Biceps":
            {
                databaseexercises = FirebaseDatabase.getInstance().getReference("cwiczenia/biceps");
                break;
            }
            case "Triceps":
            {
                databaseexercises = FirebaseDatabase.getInstance().getReference("cwiczenia/Triceps");
                break;
            }
            case "Pośladki":
            {
                databaseexercises = FirebaseDatabase.getInstance().getReference("cwiczenia/Pośladki");
                break;
            }
            case "Barki":
            {
                databaseexercises = FirebaseDatabase.getInstance().getReference("cwiczenia/barki");
                break;
            }
            case "Cardio":
            {
                databaseexercises = FirebaseDatabase.getInstance().getReference("cwiczenia/Cardio");
                break;
            }
        }
        InitRecyclerView();

        fam.setOnMenuButtonClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(fam.isOpened())
                {
                    fam.close(true);
                    fam.setElevation(-2);
                }
                else
                {
                    fam.open(true);
                    fam.setElevation(2);
                }

            }
        });
    }

    @Override

    protected void onStart() {
        super.onStart();
        databaseexercises.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mExercises.clear();
                for(DataSnapshot exerciseSnapshot : dataSnapshot.getChildren())
                {
                    String exercise = (String)exerciseSnapshot.getValue();
                    mExercises.add(exercise);
                    mImages.add(R.drawable.klata);
                    Log.d(TAG, "onDataChange: Ondatachange" + exercise);
                    Log.d(TAG, "onDataChange: Ondatachange" + mExercises);

                }
                InitRecyclerView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void InitRecyclerView()
    {
        Log.d(TAG, "InitRecyclerView: InitRecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recyclerview2);
        RecyclerView_adapter_onebody adapter = new RecyclerView_adapter_onebody(this,mExercises,mImages,mName,check);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void logout(View view) { //wylogowanie z profilu
        firebaseAuth.signOut();
        Intent intent = new Intent(this,login_activity.class);
        startActivity(intent);
    }

    public void all_trainings_button(View view) {
        Intent intent = new Intent(this,all_trainings_activity.class);
        startActivity(intent);
    }

    public void my_trainings_button(View view) {
        Intent intent = new Intent(this,my_trainings_activity.class);
        startActivity(intent);
    }

    public void results_button(View view) {
        Intent intent = new Intent(this,pre_results_activity.class);
        startActivity(intent);
    }

    public void own_training(View view) {
        Intent intent = new Intent(this,add_training_activity.class);
        startActivity(intent);
    }
    public void main_activity(View view) {
        Intent intent = new Intent(this,profile_activity.class);
        startActivity(intent);
    }


}
