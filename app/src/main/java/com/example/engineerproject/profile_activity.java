package com.example.engineerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class profile_activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView userEmail;
    private Button logout;
    private Button trening;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        trening = findViewById(R.id.swojTrening);
        userEmail = findViewById(R.id.userEmail);
        logout = findViewById(R.id.logOut);
        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser()==null) //jezeli nikt nie jest zalogowany wracamy do logowania
        {
            Intent intent = new Intent(this,login_activity.class);
            startActivity(intent);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        userEmail.setText("Welcome "+user.getEmail());   //wyswietlanie emaila usera

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

}
