package com.example.engineerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class all_trainings_activity extends AppCompatActivity {

    private static final String TAG = "all_trainings_activity";
    DatabaseReference databasetraining;
    ArrayList<Training> trainings = new ArrayList<>();
    ArrayList<String> training_names = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FloatingActionMenu fam;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String > mImagesURLS= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_trainings_activity);
        Log.d(TAG, "onCreate: Started.");
        fam = findViewById(R.id.fabmenu);

        databasetraining = FirebaseDatabase.getInstance().getReference().child("all_trainings");
        firebaseAuth = FirebaseAuth.getInstance();

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
                    Log.d(TAG, "onClick: kliknalem!");
                    fam.open(true);
                    fam.setElevation(2);
                }

            }
        });


    }
    protected void onStart() {
        trainings = new ArrayList<>();
        training_names = new ArrayList<>();
        super.onStart();
        databasetraining.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                training_names.clear();
                int i = 0;
                for (DataSnapshot trainingSnapshot : dataSnapshot.getChildren()) {
                    trainings.add(trainingSnapshot.getValue(Training.class));
                    training_names.add((trainings.get(i).getName()));
                    //String training_name_string = (String)trainingSnapshot.getValue();
                    //training_names.add(training_name_string);
                    //mImages.add(R.drawable.silacz);
                    i++;


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
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerView_adapter_all_training adapter = new RecyclerView_adapter_all_training(this,training_names);
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

    public void mainclick(View view) {
        Toast.makeText(this,"kliknalem",Toast.LENGTH_SHORT);
        Log.d(TAG, "mainclick:jestem w tym kliku ");
        //fam.setElevation(100);
        //fam.invalidate();
        //onStart();
    }
}
