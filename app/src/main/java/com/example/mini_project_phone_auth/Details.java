package com.example.mini_project_phone_auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Details extends AppCompatActivity {

    private static EditText Name;
    private static EditText Address;
    private static Button save;
    private String phone_number;
    User user;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Name=findViewById(R.id.name);
        Address=findViewById(R.id.address);
        save=(Button)findViewById(R.id.save);

        phone_number=getIntent().getStringExtra("phone_number");

        user=new User();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");


        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                          String name=Name.getText().toString();
                          String address=Address.getText().toString();

                          if(TextUtils.isEmpty(name) || TextUtils.isEmpty(address)){
                              Toast.makeText(Details.this, "Empty Credentials!!", Toast.LENGTH_SHORT).show();
                          }
                        else {
                              user.setName(name);
                              user.setAddress(address);
                              user.setPhone(phone_number);

                              databaseReference.push().setValue(user, new DatabaseReference.CompletionListener() {
                                  @Override
                                  public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                      if (databaseError != null) {

                                          Toast.makeText(Details.this, "Data could not saved", Toast.LENGTH_SHORT).show();

                                      } else {
                                          Toast.makeText(Details.this, "Account created Successfully", Toast.LENGTH_SHORT).show();
                                          Intent intent=new Intent(Details.this, MainPage.class);
                                          intent.putExtra("phone_number", phone_number);
                                          startActivity(intent);
                                      }
                                  }
                              });



                          }
                    }
                }
        );
    }
}