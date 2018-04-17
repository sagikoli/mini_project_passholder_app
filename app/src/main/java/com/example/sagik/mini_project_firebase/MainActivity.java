package com.example.sagik.mini_project_firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static String unm;
    Intent i;
    DatabaseReference id;
    DatabaseReference myRef;
    DatabaseReference map;
    FirebaseDatabase database;
boolean haschild=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database

    }
    public void Login(View v)
    {
        final TextView username=findViewById(R.id.username);
        final TextView pass=findViewById(R.id.passwd);
        unm=username.getText().toString();
         database= FirebaseDatabase.getInstance();
         myRef= database.getReference("users");
         map=database.getReference("mapping");
        map.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(unm))
                {
                    unm=dataSnapshot.child(unm).getValue().toString();
                  //  Log.i("zxc", unm);
                    id = myRef.child(unm);
                    id.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot Snapshot) {
                            Log.i("zxc", Snapshot.getValue().toString());
                            if(pass.getText().toString().equals( Snapshot.child("password").getValue().toString()))
                            {
                                Toast.makeText(getApplicationContext(), "Login sucessesful", Toast.LENGTH_LONG).show();
                                HashMap<Object, Object> route_map= (HashMap<Object, Object>) Snapshot.child("routes").getValue();
                                HashMap<Object, Object> expiry_map= (HashMap<Object, Object>) Snapshot.child("expiry").getValue();
                                i = new Intent(MainActivity.this, Pass_Info.class);
                                i.putExtra("address",Snapshot.child("address").getValue().toString());
                                i.putExtra("expiry",expiry_map);
                                i.putExtra("name",Snapshot.child("name").getValue().toString());
                                i.putExtra("address",Snapshot.child("address").getValue().toString());
                                i.putExtra("gender",Snapshot.child("gender").getValue().toString());
                                i.putExtra("mob_no",Snapshot.child("mobile no").getValue().toString());
                                i.putExtra("DOB",Snapshot.child("date of birth").getValue().toString());
                                Log.i("data_base", String.valueOf(Snapshot.child("routes").getValue().getClass()));
                                i.putExtra("routes",route_map);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "incorrect password", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
                else {
                    Toast.makeText(getApplicationContext(),"User not register",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
