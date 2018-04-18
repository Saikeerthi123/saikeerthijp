package com.example.saikeerthana.saikeerthijp;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;

public class DetailsActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private Button issue;
    private EditText username;
    private EditText phoneno;
    private EditText intime;
    private EditText useraddress;
    String bankname;
    String branchname;
    String countername;
    Spinner bankspinner;
    Spinner branchspinner;
    Spinner counterspinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        firebaseAuth = FirebaseAuth.getInstance();

        username=(EditText)findViewById(R.id.etuser);
        phoneno=(EditText) findViewById(R.id.etphone);
        intime=(EditText)findViewById(R.id.ettime);
        useraddress=(EditText)findViewById(R.id.etaddress);

        bankname=getIntent().getExtras().getString("bankname");
        branchname=getIntent().getExtras().getString("branchname");
        countername=getIntent().getExtras().getString("countername");


        Toast.makeText(DetailsActivity.this,"details", Toast.LENGTH_SHORT).show();

        Toast.makeText(DetailsActivity.this,bankname, Toast.LENGTH_SHORT).show();


        /*bankspinner=findViewById(R.id.bankspinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.bankspinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankspinner.setAdapter(adapter);
        bankspinner.setOnItemSelectedListener(this);

        branchspinner=findViewById(R.id.branchspinner);
        ArrayAdapter<CharSequence> branchadapter=ArrayAdapter.createFromResource(this,R.array.branchspinner,android.R.layout.simple_spinner_item);
        branchadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchspinner.setAdapter(branchadapter);
        branchspinner.setOnItemSelectedListener(this);


        counterspinner=findViewById(R.id.counterspinner);
        ArrayAdapter<CharSequence> counteradapter=ArrayAdapter.createFromResource(this,R.array.counterspinner,android.R.layout.simple_spinner_item);
        counteradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counterspinner.setAdapter(counteradapter);
        counterspinner.setOnItemSelectedListener(this);*/

        issue = (Button)findViewById(R.id.btnissue);

        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logoutMenu:{
                Logout();
            }
        }

        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.bankspinner:
                banktext=parent.getItemAtPosition(position).toString();
                Toast.makeText(DetailsActivity.this,banktext, Toast.LENGTH_SHORT).show();
                break;
            case R.id.branchspinner:
                branchtext=parent.getItemAtPosition(position).toString();
                Toast.makeText(DetailsActivity.this,branchtext, Toast.LENGTH_SHORT).show();
                break;
            case R.id.counterspinner:
                countertext=parent.getItemAtPosition(position).toString();
                Toast.makeText(DetailsActivity.this,countertext, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

    private void sendData(){
        String id;
        String userName=username.getText().toString();
        String Phoneno=phoneno.getText().toString();
        String Location=useraddress.getText().toString();
        String inputTime=intime.getText().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference(bankname);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        String tokenid=mDatabase.push().getKey();
        Token token=new Token(inputTime,tokenid,Location,userName,Phoneno);
        id=user.getUid();
        mDatabase.child(branchname).child(countername).child(id).setValue(token);
        Toast.makeText(DetailsActivity.this, "Token Issued!!!", Toast.LENGTH_SHORT).show();
        Intent inten=new Intent(DetailsActivity.this, MapsActivity.class);
        inten.putExtra("bankname",bankname);
        inten.putExtra("branchname",branchname);
        inten.putExtra("time",inputTime);
        inten.putExtra("userloc",Location);
        startActivity(inten);
    }


    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(DetailsActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}

