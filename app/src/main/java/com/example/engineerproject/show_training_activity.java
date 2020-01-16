package com.example.engineerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class show_training_activity extends AppCompatActivity {

    private TextView training_name;
    private Integer pos1;
    private Integer pos2;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    DatabaseReference share_trainingReference;
    DatabaseReference add_trainingReference;
    DatabaseReference add_likeReference;
    private ArrayList<String> workouts_name = new ArrayList<>();
    private ArrayList<Workout> workout_list = new ArrayList<>();
    private ArrayList<Training> training = new ArrayList<>();
    Integer check;
    Button button1;
    Button button2;
    TextView likes;
    Integer likess=0;
    String push_path;
    Integer clicked=0;
    CircleImageView lapka;
    FloatingActionMenu fam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_training_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        training_name= findViewById(R.id.training_name);
        fam = findViewById(R.id.fabmenu);
        button1 = findViewById(R.id.share_training);
        button2 = findViewById(R.id.start_training);
        likes = findViewById(R.id.likes);
        check = getIntent().getIntExtra("check",0);
        pos1=getIntent().getIntExtra("pos",0);
        pos2=getIntent().getIntExtra("pos2",0);
        lapka = findViewById(R.id.lapka);
        Log.d("show traing", "onCreate: pos : "+pos1);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("trainings");
        share_trainingReference = FirebaseDatabase.getInstance().getReference().child("all_trainings");
        add_trainingReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("trainings");
        if(check==1)
        {
            button1.setText("Udostępnij trening");
            button2.setText("Zacznij trening");
        }
        else
        {
            button1.setText("Dodaj do moich treningów");
            button2.setText("Lubię to!");
        }
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
                    fam.setElevation(100);
                }

            }
        });
    }

    protected void onStart() {
        super.onStart();

        if (check == 1) {
            likes.setVisibility(View.INVISIBLE);
            lapka.setVisibility(View.INVISIBLE);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int i = 0;
                    workouts_name = new ArrayList<>();
                    training = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        training.add(dataSnapshot1.getValue(Training.class));
                        //workouts_name.add(dataSnapshot1.getValue(Training.class).getListOfWorkouts().get(i).getName());
                        //workouts_name.add((training.get(pos).getName()));
                        i++;

                    }
                    for (int j = 0; j < training.get(pos1).getListOfWorkouts().size(); j++) {
                        workouts_name.add(training.get(pos1).getListOfWorkouts().get(j).getName());
                    }
                    training_name.setText(training.get(pos1).getName());
                    InitRecyclerView();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else
        {
            likes.setVisibility(View.VISIBLE);
            lapka.setVisibility(View.VISIBLE);
            share_trainingReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int i = 0;
                    if(clicked==0)
                    {
                        workouts_name = new ArrayList<>();
                        training = new ArrayList<>();
                        Log.d("f", "check1 ");
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            training.add(dataSnapshot1.getValue(Training.class));
                            Log.d("f", "check2 ");
                            i++;

                        }
                        Log.d("f", "check3 ");
                        Log.d("f", "ilosc workoutow  " + training.get(pos2).getListOfWorkouts().size());

                        for (int j = 0; j < training.get(pos2).getListOfWorkouts().size(); j++) {
                            workouts_name.add(training.get(pos2).getListOfWorkouts().get(j).getName());
                        }
                    }
                    training_name.setText(training.get(pos2).getName());
                    likess = training.get(pos2).getLikes();
                    likes.setText("Polubienia : " + likess);
                    InitRecyclerView();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void start_training(View view) {

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce);
        button2.startAnimation(animation);
        if (check == 1)
        {
            Intent intent = new Intent(getApplicationContext(), start_training_activity.class);
            intent.putExtra("pos", pos1);
            startActivity(intent);
        }
        else
        {
            clicked=1;
            if(training.get(pos2).getLikes()==null)
            {
                likess=0;
            }
            else
            {
                likess = training.get(pos2).getLikes();
            }
            likess = likess+1;
            training.get(pos2).setLikes(likess);
            Log.d("f", "check4 ");
            share_trainingReference.removeValue();

            for(int i=0;i<training.size();i++)
            {
                share_trainingReference.push().setValue(training.get(i));
                Log.d("f", "check5 ");
            }
            Log.d("f", "check6 ");
            Toast.makeText(this,"Polubiono ten trening",Toast.LENGTH_SHORT).show();
        }


    }

    private void InitRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerview10);
        RecyclerView_adapter_show_training adapter = new RecyclerView_adapter_show_training(this,workouts_name);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("show tren", "InitRecyclerView: wywolanie inita");
    }

    public void share_training(View view) {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce);
        button1.startAnimation(animation);
        if(check==1) {
            share_trainingReference.push().setValue(training.get(pos1));
            Toast.makeText(this,"Udostępniono ten trening",Toast.LENGTH_SHORT).show();
        }
        else {
            add_trainingReference.push().setValue(training.get(pos2));
            Toast.makeText(this,"Dodano do moich treningów",Toast.LENGTH_SHORT).show();
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
