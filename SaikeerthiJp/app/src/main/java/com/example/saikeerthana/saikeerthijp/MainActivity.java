package com.example.saikeerthana.saikeerthijp;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView attempts;
    private TextView newreg;
    private int counter=5;
    private TextView UserRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.etname);
        Password = (EditText) findViewById(R.id.etpass);
        Login = (Button) findViewById(R.id.btnlogin);
        attempts=(TextView)findViewById(R.id.tvattempts);
        UserRegister=(TextView)findViewById(R.id.tvreg);

        attempts.setText("No of attempts remaining :5");

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });

        UserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });
    }

    public void validate(String userName,String userPass)
    {
        progressDialog.setMessage("validating the credentials");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(userName,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Hurray login successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,Main2Activity.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Oops! login unsuccessful",Toast.LENGTH_SHORT).show();
                    counter--;
                    attempts.setText("no of attempts remaining : "+counter);
                    progressDialog.dismiss();
                    if(counter==0)
                    {
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }
}

