package com.example.engineerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

//tutaj daje do wykresu
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;


import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;


import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class draw_diagram_activity extends AppCompatActivity {
    private String name;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ArrayList<Training> trainings = new ArrayList<>();
    private ArrayList<Integer> reps = new ArrayList<>();
    private ArrayList<Integer> weights = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private Integer average_reps=0;
    private Integer average_weights=0;
    FloatingActionMenu fam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_diagram_activity);
        name = getIntent().getStringExtra("name");
        Log.d("f", "onCreate: name : "+name);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("results");
        fam = findViewById(R.id.fabmenu);
        fam.setBackgroundColor(0xffffffff);

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
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    trainings.clear();

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        trainings.add(dataSnapshot1.getValue(Training.class));

                    }

                    for (int i = 0; i < trainings.size(); i++)
                    {
                        for (int j = 0; j < trainings.get(i).getListOfWorkouts().size(); j++)
                        {
                            if(name.equals(trainings.get(i).getListOfWorkouts().get(j).getName()))
                            {
                                for (int k = 0; k < trainings.get(i).getListOfWorkouts().get(j).getSets().size(); k++) {
                                    if(k==0)
                                    {
                                        //dates.add(trainings.get(i).getDate());
                                    }
                                    average_reps =(average_reps+ trainings.get(i).getListOfWorkouts().get(j).getSets().get(k).getReps());
                                    average_weights =(average_weights+ trainings.get(i).getListOfWorkouts().get(j).getSets().get(k).getWeight());
                                }
                                average_reps/=trainings.get(i).getListOfWorkouts().get(j).getSeries();
                                average_weights/=trainings.get(i).getListOfWorkouts().get(j).getSeries();
                                reps.add(average_reps);
                                weights.add(average_weights);
                                dates.add(trainings.get(i).getDate());
                                average_weights=0;
                                average_reps=0;
                            }
                        }
                    }
                    Log.d("f", "name : "+name);
                    Log.d("f", "data : "+dates);
                    Log.d("f", "reps : "+reps);
                    Log.d("f", "weights : "+weights);
                    draw_chart2();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


    }

    public void draw_chart2()
    {

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title(name);

        cartesian.yAxis(0).title("Wyniki");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        //seriesData.add(new CustomDataEntry("1986", 3.6, 2.3));
        for(int i=0;i<dates.size();i++)
        {
            seriesData.add(new CustomDataEntry(dates.get(i), reps.get(i),weights.get(i)));
        }


        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Powtórzenia");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Ciężar");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);


        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
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

