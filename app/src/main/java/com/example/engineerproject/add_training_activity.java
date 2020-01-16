package com.example.engineerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class add_training_activity extends AppCompatActivity {
    private static final String TAG = "add_training_activity";
    public String name;
    public Integer series;
    public TextView training_name;
    public TextView check;
    private Calendar calendar;
    Integer likes=0;
    FirebaseUser user;

    private ArrayList<String> mNames = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    ArrayList<Workout> list_of_workouts = new ArrayList<Workout>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_training_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        training_name = findViewById(R.id.et1);
        check = findViewById(R.id.check);
        if (getIntent().getStringArrayListExtra("names") != null) {
            mNames = getIntent().getStringArrayListExtra("names");
            series = getIntent().getIntExtra("series",3);
            name = getIntent().getStringExtra("name");

            save_workout();

        }
        InitRecyclerView();
    }




    public void add_exercise(View view) {
        Intent intent = new Intent(this,exercises_activity.class);
        intent.putStringArrayListExtra("names1",mNames);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getStringArrayListExtra("names") != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list_of_workouts.clear();
                    int i = 0;

                    for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                        //Workout workout = (Workout) workoutSnapshot.getValue();
                        //trainings.add(workoutSnapshot.getValue(Training.class))

                        list_of_workouts.add(workoutSnapshot.getValue(Workout.class));
                        //Log.d(TAG, "onDataChange: Ondatachange" + workout);
                        Log.d(TAG, "onDataChange: listofworkaout_size" + list_of_workouts.size());
                        Log.d(TAG, "onDataChange: Ondatachange" + list_of_workouts);
                        Log.d(TAG, "onDataChange: name" + list_of_workouts.get(i).getName());
                        Log.d(TAG, "onDataChange: name" + list_of_workouts.get(i).getSeries());
                        i++;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    public void save_training(View view) {
        String name_s = training_name.getText().toString();
        if(name_s.isEmpty())
        {
            Toast.makeText(this,"Prosze podac nazwe treningu!",Toast.LENGTH_SHORT).show();
        }



        else if(list_of_workouts.isEmpty()) {
                Toast.makeText(this, "Lista ćwiczeń jest pusta, dodaj conajmniej jedno ćwiczenie", Toast.LENGTH_SHORT).show();
            }
        else {
            calendar = Calendar.getInstance();
            String current_date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                Training new_training = new Training(name_s,null, list_of_workouts,likes);
                databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("trainings");
                databaseReference.push().setValue(new_training);
                Log.d(TAG, "save_training: koniec aktywnosci");
                Intent intent = new Intent(this, profile_activity.class);
                startActivity(intent);

            databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("workout");
            databaseReference.removeValue();


            }
        }



    private void InitRecyclerView()
    {
        //Log.d(TAG, "InitRecyclerView: InitRecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recyclerView4);
        RecyclerView_adapter_add_exercise_to_training adapter = new RecyclerView_adapter_add_exercise_to_training(this,mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void save_workout()
    {
        ArrayList<One_set> sets = new ArrayList<>(series);
        for(int i=0;i<series;i++)
        {
            One_set set= new One_set(0,0);
            sets.add(set);
        }
        Workout workout = new Workout(name,series,sets);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("workout");
        databaseReference.push().setValue(workout);

    }
    public void logout(View view) { //wylogowanie z profilu
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("workout");
        databaseReference.removeValue();
        firebaseAuth.signOut();
        Intent intent = new Intent(this,login_activity.class);
        startActivity(intent);
    }

    public void all_trainings_button(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("workout");
        databaseReference.removeValue();
        Intent intent = new Intent(this,all_trainings_activity.class);
        startActivity(intent);
    }

    public void my_trainings_button(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("workout");
        databaseReference.removeValue();
        Intent intent = new Intent(this,my_trainings_activity.class);
        startActivity(intent);
    }

    public void results_button(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("workout");
        databaseReference.removeValue();
        Intent intent = new Intent(this,pre_results_activity.class);
        startActivity(intent);
    }

    public void own_training(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("workout");
        databaseReference.removeValue();
        Intent intent = new Intent(this,add_training_activity.class);
        startActivity(intent);
    }

    public void main_activity(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("workout");
        databaseReference.removeValue();
        Intent intent = new Intent(this,profile_activity.class);
        startActivity(intent);
    }
}
