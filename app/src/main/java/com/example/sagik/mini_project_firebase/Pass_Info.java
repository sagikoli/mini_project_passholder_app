package com.example.sagik.mini_project_firebase;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pass_Info extends AppCompatActivity {
    LinearLayout routes_column;
    LinearLayout avail_passes_column;
    TextView name,gender,age,address;
    ImageView img;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pass_info);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        name=findViewById(R.id.name);
        gender=findViewById(R.id.gender);
        age=findViewById(R.id.age);
        address=findViewById(R.id.address);
        routes_column=findViewById(R.id.route_column);
        avail_passes_column=findViewById(R.id.avail_pass_column);
        Bundle bundle=getIntent().getExtras();
        name.setText(bundle.get("name").toString());
        gender.setText(bundle.get("gender").toString());
        address.setText(bundle.get("address").toString());
        age.setText(bundle.get("DOB").toString());
        HashMap<Object,Object> routes= (HashMap<Object, Object>) bundle.get("routes");
        for (Map.Entry<Object,Object> entry : routes.entrySet())
        {
            TextView temp=new TextView(this);
            temp.setText(entry.getKey().toString());
            temp.setTextColor(Color.BLACK);
            temp.setGravity(Gravity.CENTER);
            routes_column.addView(temp);

            TextView temp2=new TextView(this);
            temp2.setText(entry.getValue().toString());
            temp2.setTextColor(Color.BLACK);
            temp2.setGravity(Gravity.CENTER);
            avail_passes_column.addView(temp2);
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        img = findViewById(R.id.imageView);
        mStorageRef.child("users/"+MainActivity.unm+".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                InputStream is = null;
                try {
                    URL u=new URL(task.getResult().toString());
                    is=u.openStream();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i("data_base",task.getResult().getPath());
            Log.i("data_base",task.getResult().toString());
                img.setImageBitmap(BitmapFactory.decodeStream(is));
            Toast.makeText(getApplicationContext(),"Image download complete",Toast.LENGTH_LONG);
            }
        });
        super.onPostCreate(savedInstanceState);
    }

        public void getcode (View view)
        {
            Intent go_to_code = new Intent(this, Code_Gen.class);
            startActivity(go_to_code);
            return;
        }
    }
