package com.example.saikeerthana.saikeerthijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private Button continuebutton;
    String banktext;
    String branchtext;
    String countertext;
    Spinner bankspinner;
    Spinner branchspinner;
    Spinner counterspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseAuth = FirebaseAuth.getInstance();

        continuebutton = (Button)findViewById(R.id.btncontinue);

        bankspinner=findViewById(R.id.bankspinner);
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
        counterspinner.setOnItemSelectedListener(this);



        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }






    private void sendData(){
        /*String id;
        mDatabase = FirebaseDatabase.getInstance().getReference(banktext);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        String tokenid=mDatabase.push().getKey();
        Token token=new Token(tokenid);
        id=user.getUid();
        mDatabase.child(branchtext).child(countertext).child(id).setValue(token);
        Toast.makeText(Main2Activity.this, "Token Issued!!!", Toast.LENGTH_SHORT).show();1*/
        Intent intent=new Intent(Main2Activity.this,DetailsActivity.class);
        intent.putExtra("bankname",banktext);
        intent.putExtra("branchname",branchtext);
        intent.putExtra("countername",countertext);
        startActivity(intent);
    }


    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.bankspinner:
                banktext=parent.getItemAtPosition(position).toString();
                Toast.makeText(Main2Activity.this,banktext, Toast.LENGTH_SHORT).show();
                break;
            case R.id.branchspinner:
                branchtext=parent.getItemAtPosition(position).toString();
                Toast.makeText(Main2Activity.this,branchtext, Toast.LENGTH_SHORT).show();
                break;
            case R.id.counterspinner:
                countertext=parent.getItemAtPosition(position).toString();
                Toast.makeText(Main2Activity.this,countertext, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
