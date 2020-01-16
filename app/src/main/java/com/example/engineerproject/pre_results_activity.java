package com.example.engineerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;

public class pre_results_activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FloatingActionMenu fam;
    Integer check=1; // czy przyszedlem z wynikow
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_results_activity);
        firebaseAuth = FirebaseAuth.getInstance();
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
                    fam.setElevation(100);
                }

            }
        });
    }

    public void all_results(View view) {
        Intent intent = new Intent(this,results_activity.class);
        startActivity(intent);
    }

    public void diagrams(View view) {
        Intent intent = new Intent(this,exercises_activity.class);
        Log.d("f", "diagrams check :"+check);
        intent.putExtra("check",check);
        startActivity(intent);
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
