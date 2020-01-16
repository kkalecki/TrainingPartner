package com.example.engineerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_activity extends AppCompatActivity {

    private Button login;
    private Button register;
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        login = findViewById(R.id.button_login);
        register = findViewById(R.id.button_register);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!= null)
        {
            Intent intent = new Intent(this,profile_activity.class);
            startActivity(intent);
            //startActivity(new Intent(getApplicationContext(),profile_activity.class));
        }


    }

    public void login(View view) {      //funkcja odpowiadajaca za logowanie
        String stringEmail = email.getText().toString().trim();     //sprawdzenie czy mail nie jest pusty
        String stringPassword = password.getText().toString().trim();

        if (stringEmail.isEmpty())
        {
            Toast.makeText(this, "prosze podac email!", Toast.LENGTH_SHORT).show();       //wyswietlenie komentarza
        }
        else if (stringPassword.isEmpty())
        {
            Toast.makeText(this, "prosze podac hasło!", Toast.LENGTH_SHORT).show();       //wyswietlenie komentarza
        }



        else
        {

           firebaseAuth.signInWithEmailAndPassword(stringEmail,stringPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful())  //sprawdzenie poprawnosci logowania i logowanie
                   {
                       finish();
                       startActivity(new Intent(getApplicationContext(),profile_activity.class));
                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(),"Niepoprawne dane logowania!",Toast.LENGTH_SHORT).show();
                   }
               }
           });}

    }

    public void register(View view) {
        Intent register = new Intent(this, register_activity.class);
        startActivity(register);
        //finish();
    }
}
