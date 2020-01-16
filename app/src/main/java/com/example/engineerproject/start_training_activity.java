package com.example.engineerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class start_training_activity extends AppCompatActivity {
    private TextView training_name;
    private TextView workout_name;
    private TextView series;
    private Button button;
    private EditText reps;
    private EditText weight;
    private TextView save_reps;
    private TextView save_weight;
    private DatabaseReference databaseReference;
    private DatabaseReference resultsReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ArrayList<Training> trainings = new ArrayList<>();
    private Integer position;
    private Integer number_of_workouts;
    private Integer i=0;
    private Integer check=0;
    private Integer i2=0;
    private Integer check2=0;
    private Chronometer chronometer;
    private Integer timer=0;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_training_activity);
        training_name = findViewById(R.id.textView3);
        workout_name = findViewById(R.id.textView4);
        series = findViewById(R.id.textView5);
        button = findViewById(R.id.b2);
        reps = findViewById(R.id.editText3);
        weight = findViewById(R.id.editText4);
        save_reps = findViewById(R.id.textView6);
        save_weight = findViewById(R.id.textView8);
        position = getIntent().getIntExtra("pos",0);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("trainings"); //sciezka do treningow
        chronometer = findViewById(R.id.chronometer);
        calendar = Calendar.getInstance();

    }

    protected void onStart() {
        super.onStart();
        reps = findViewById(R.id.editText3);
        weight = findViewById(R.id.editText4);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    trainings.add(dataSnapshot1.getValue(Training.class));  //zapisanie z bazy danych treningow do listy


                }
                if (timer == 1 && check<number_of_workouts) //start timera (przerwa pomiedzy cwiczeniami, drugi warunek po to, aby na koncu treningu nie robic przerwy)
                {
                    chronometer.setFormat(null);
                    chronometer.setBase(SystemClock.elapsedRealtime() + 90000); //przerwa 1,5 minuty
                    chronometer.start();
                    timer = 0;
                    series.setText("Przerwa : ");
                    save_reps.setText("");
                    save_weight.setText("");
                    reps.setVisibility(View.INVISIBLE);
                    weight.setVisibility(View.INVISIBLE);
                    chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometer) {
                            if((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 0)    //jak timer dojdzie do 0:00 to stopuje
                            {
                                chronometer.stop();
                            }
                        }
                    });
                }
                else {
                    reps.setVisibility(View.VISIBLE);
                    weight.setVisibility(View.VISIBLE);
                    save_reps.setText("Zapisz ilość powtórzeń :");
                    save_weight.setText("Zapisz ciężar :");
                    chronometer.setFormat("");  //ustawiam aby timer byl niewidoczny
                    timer = 1;
                    chronometer.stop();
                    chronometer.setBase(SystemClock.elapsedRealtime());

                        training_name.setText(trainings.get(position).getName());       //pobieram nazwe treningu
                        number_of_workouts = trainings.get(position).getListOfWorkouts().size();    //pobieram ilosc cwiczen w danym treningu
                        if (check < number_of_workouts)   //sprawdzam czy nie wyszedlem poza ilosc cwiczen w treningu
                        {
                            int number_of_sets = trainings.get(position).getListOfWorkouts().get(check).getSeries();   //ilosc serii w danym cwiczeniu
                            if (i < number_of_sets)   //sprawdzam czy nie wyszedlem poza ilosc serii w danym cwiczeniu
                            {
                                series.setText("Seria : " + (i + 1));       //wyswietlam serie
                                workout_name.setText(trainings.get(position).getListOfWorkouts().get(check).getName()); //wyswietlam nazwe cwiczenia

                                i++;   //przejscie do nastepnej serii
                            }
                            if (i == number_of_sets)  //sprawdzam czy przeszedlem przez wszystkie serie w danym cwiczeniu, jezeli tak:
                            {
                                check++;       //przechodze do kolejnego cwiczenia
                                i = 0;       //zaczynam zliczanie serii od poczatku
                            }
                        }
                        else    //przejscie przez wszystkie cwiczenia, koniec zapisu danych
                        {
                            series.setText("");
                            workout_name.setText("Trening ukonczony Gratulacje!");
                            button.setText("Koniec");
                            save_reps.setText("");
                            save_weight.setText("");
                            reps.setVisibility(View.INVISIBLE);
                            weight.setVisibility(View.INVISIBLE);
                        }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void start_training(View view) {
        String current_date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        if(timer==1 && workout_name.getText()!= "Trening ukonczony Gratulacje!")    //jezeli timer jest wylaczony to mozna zapisywac dane
        {       //sprawdzenie, czy nie zostaly juz zapisane wszystkie dane


            if(weight.getText().toString().isEmpty())   //nalezy podac dane
            {
                Toast.makeText(this,"Proszę podać ciężar",Toast.LENGTH_SHORT).show();
                if(reps.getText().toString().isEmpty())
                {
                    Toast.makeText(this,"Proszę podać ilość powtórzeń",Toast.LENGTH_SHORT).show();
                }
            }
            else    // zapis repsow i weight do bazy danych
                {
                int reps_s = Integer.parseInt(reps.getText().toString());
                int weight_s = Integer.parseInt(weight.getText().toString());

                if(check2<number_of_workouts)   //sprawdzam czy nie wyszedlem poza ilosc cwiczen w treningu
                {
                    int number_of_sets = trainings.get(position).getListOfWorkouts().get(check2).getSeries();   //ilosc serii w danym cwiczeniu
                    if(i2<number_of_sets)   //sprawdzam czy nie wyszedlem poza ilosc serii w danym cwiczeniu
                    {
                        Log.d("f", "check " + check2);
                        Log.d("f", "i2 " + i2);
                        trainings.get(position).getListOfWorkouts().get(check2).getSets().get(i2).setReps(reps_s);  //zapis danych
                        trainings.get(position).getListOfWorkouts().get(check2).getSets().get(i2).setWeight(weight_s);
                        i2++;   //przejscie do nastepnej serii
                    }
                    if(i2==number_of_sets)  //sprawdzam czy przeszedlem przez wszystkie serie w danym cwiczeniu, jezeli tak:
                    {
                        check2++;       //przechodze do kolejnego cwiczenia
                        i2=0;       //zaczynam zliczanie serii od poczatku
                    }
                }
                onStart();  //po zapisaniu danych odpadlamy onStart
            }
        }
        else {  //jezeli timer dziala, nigdy nie zapisujemy repsow i weight. Zapis do bazy danych i wyjscie z aktywnosci.
            if (workout_name.getText() == "Trening ukonczony Gratulacje!") {
                Log.d("f", "wyjscie 2");
                Training results = new Training(trainings.get(position).getName(), current_date, trainings.get(position).getListOfWorkouts(),trainings.get(position).getLikes());
                resultsReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("results");
                resultsReference.push().setValue(results);
                Intent intent = new Intent(getApplicationContext(), profile_activity.class);
                startActivity(intent);
            }
            else {
                reps.setText("");
                weight.setText("");
                onStart();
            }
        }
    }
}
