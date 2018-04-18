package com.example.saikeerthana.saikeerthijp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private EditText Email;
    private Button Register;
    private TextView Alreadyuser;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    //upload to database
                    String user_email=Email.getText().toString().trim();
                    String user_pass=Password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "hurray! successfully registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(RegistrationActivity.this, "Oops! could not register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        Alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });
    }

    private void setupUIViews()
    {
        Name=(EditText)findViewById(R.id.etregname);
        Password=(EditText)findViewById(R.id.etpassword);
        Email=(EditText)findViewById(R.id.etemail);
        Register=(Button)findViewById(R.id.btnregister);
        Alreadyuser=(TextView)findViewById(R.id.tvalreadyuser);
    }

    private Boolean validate()
    {
        Boolean result=false;
        String userName=Name.getText().toString();
        String userPass=Password.getText().toString();
        String userEmail=Email.getText().toString();

        if(userName.isEmpty() || userPass.isEmpty() || userEmail.isEmpty())
        {
            Toast.makeText(this,"please enter all the details",Toast.LENGTH_SHORT).show();
        }
        else
        {
            result=true;
        }
        return result;
    }
}
