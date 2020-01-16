package com.example.engineerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class my_trainings_activity extends AppCompatActivity {

    private ArrayList<Training> training = new ArrayList<>();
    private ArrayList<String> training_names = new ArrayList<>();
    private ArrayList<String > mImages= new ArrayList<>();
    FloatingActionMenu fam;

    DatabaseReference databasetraining;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_trainings_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        fam = findViewById(R.id.fabmenu);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databasetraining = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("trainings");

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

    protected void onStart() {
        super.onStart();
        databasetraining.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                training_names.clear();
                int i=0;
                for(DataSnapshot trainingSnapshot : dataSnapshot.getChildren())
                {
                    training.add(trainingSnapshot.getValue(Training.class));
                    training_names.add((training.get(i).getName()));
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
        Log.d("my training", "InitRecyclerView: InitRecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recyclerview_my);
        RecyclerView_adapter_my_training adapter = new RecyclerView_adapter_my_training(this,training_names,mImages);
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
