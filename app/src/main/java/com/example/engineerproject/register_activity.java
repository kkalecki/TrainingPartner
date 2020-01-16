package com.example.engineerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register_activity extends AppCompatActivity {

    private EditText mail;
    private EditText password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        firebaseAuth= FirebaseAuth.getInstance();
        mail = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void confirm(View view) {
        String mailString = mail.getText().toString().trim();
        String passString = password.getText().toString().trim();

        if (mailString.isEmpty())
        {
            Toast.makeText(this, "prosze podac email!", Toast.LENGTH_SHORT).show();       //wyswietlenie komentarza
        }
        else if (passString.isEmpty())
        {
            Toast.makeText(this, "prosze podac hasło!", Toast.LENGTH_SHORT).show();       //wyswietlenie komentarza
        }
        else{
        firebaseAuth.createUserWithEmailAndPassword(mailString, passString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(register_activity.this,"Zarejestrowano pomyślnie!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),login_activity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(register_activity.this,"Upewnij się, że podałeś poprawny email oraz że hasło ma conajmniej 6 znaków",Toast.LENGTH_SHORT).show();
                }
            }
        });
        }



    }
}
