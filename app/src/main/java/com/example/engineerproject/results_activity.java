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

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class results_activity extends AppCompatActivity {
    private TextView date;
    private TextView training_name;
    private ArrayList<String> exercise_names = new ArrayList<>();
    ArrayList<Integer> series = new ArrayList<>();
    ArrayList<Integer> reps = new ArrayList<>();
    ArrayList<Integer> weights = new ArrayList<>();
    private ArrayList<Training> trainings = new ArrayList<>();
    private Integer check=1;    //do przeskakiwania pomiedzy treningami, ustawia sie gdy klikne guzik
    private Integer check2 =0;  //zmienna przechowujaca pozycje na ktorej jest trening
    private Integer position=0;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FloatingActionMenu fam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);
        date = findViewById(R.id.date);
        training_name = findViewById(R.id.namet);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("results");
        fam = findViewById(R.id.fabmenu);

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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                series = new ArrayList<>();
                reps = new ArrayList<>();
                weights = new ArrayList<>();
                trainings = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    trainings.add(dataSnapshot1.getValue(Training.class));  //zapisanie z bazy danych treningow do listy


                }
                position = (trainings.size()-check);
                Log.d("f", "trening size :"+trainings.size());
                Log.d("f", "check :"+check);
                Log.d("f", "pozycja :"+position);
                Log.d("f", "nazwa treningu "+trainings.get(position).getName());
                date.setText(trainings.get(position).getDate());
                training_name.setText(trainings.get(position).getName());
                for(int i=0;i<trainings.get(position).getListOfWorkouts().size();i++)
                {

                    for(int j=0;j<trainings.get(position).getListOfWorkouts().get(i).getSeries();j++)
                    {
                        exercise_names.add(trainings.get(position).getListOfWorkouts().get(i).getName());
                        series.add(j+1);
                        reps.add(trainings.get(position).getListOfWorkouts().get(i).getSets().get(j).getReps());
                        weights.add(trainings.get(position).getListOfWorkouts().get(i).getSets().get(j).getWeight());
                    }

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
        Log.d("f", "InitRecyclerView: result activity");
        //Log.d(TAG, "InitRecyclerView: InitRecyclerView");
        RecyclerView recyclerView = findViewById(R.id.recyclerView12);
        RecyclerView_adapter_results adapter = new RecyclerView_adapter_results(this,exercise_names,series,reps,weights);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void last_training(View view) {
        if(check==trainings.size())
        {
            Toast.makeText(this,"To jest Twój najstarszy zapis",Toast.LENGTH_SHORT).show();

        }
        else {


            check++;        //zwiekszam zmienna odpowiadajaca za wybor treningu
            onStart();
        }

    }

    public void next_training(View view) {
        if(check==1)
        {
            Toast.makeText(this,"To jest Twój najnowszy zapis",Toast.LENGTH_SHORT).show();
        }
        else
        {
            check--;            //zmniejszam zmienna odpowiadajaca za wybor treningu
            onStart();
        }
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
