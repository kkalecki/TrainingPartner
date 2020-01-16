package com.example.engineerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class amount_of_series_activity extends AppCompatActivity {
    public String name;
    public EditText series;
    private static final String TAG = "amount";
    public ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amount_of_series_activity);
        Log.d(TAG, "onCreate: name :"+getIntent().getStringExtra("name"));
        Log.d(TAG, "onCreate: names :"+getIntent().getStringArrayListExtra("names"));
        name = getIntent().getStringExtra("name");

        series=findViewById(R.id.editText);
        names = getIntent().getStringArrayListExtra("names");
        names.add(name);


    }

    public void add_exer(View view) {
        Integer series_int = Integer.parseInt(series.getText().toString());
        Intent i = new Intent(this, add_training_activity.class);
        i.putStringArrayListExtra("names",names);
        i.putExtra("name",name);
        i.putExtra("series",series_int);
        Log.d(TAG, "add_exer: nazwy w series" +names);
        Log.d(TAG, "add_exer: " +series_int);
        startActivity(i);
    }
}
