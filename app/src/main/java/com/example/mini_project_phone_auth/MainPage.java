package com.example.mini_project_phone_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private String phone_number;

    private Button mLogoutBtn;
    private Button enter_details;
    private Button buy;
    private Button sell;

    int flag=0;

    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        phone_number=getIntent().getStringExtra("phone_number");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mLogoutBtn = findViewById(R.id.logout);
        buy=(Button)findViewById(R.id.buy);
        sell=(Button)findViewById(R.id.sell);
        enter_details=(Button)findViewById(R.id.enter_details);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user=snapshot.getValue(User.class);
                    if(user.getPhone().equals(phone_number)){
                        Toast.makeText(MainPage.this, "User Already exists", Toast.LENGTH_SHORT).show();
                     flag=1;
                    }else{
                        continue;
                    }
                }

                if(flag == 1){
                    buy.setVisibility(View.VISIBLE);
                    sell.setVisibility(View.VISIBLE);
                    enter_details.setVisibility(View.INVISIBLE);
                }else{
                    buy.setVisibility(View.INVISIBLE);
                    sell.setVisibility(View.INVISIBLE);
                    enter_details.setVisibility(View.VISIBLE);
                }

                sell.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(MainPage.this, Sell.class);
                                startActivity(intent);
                            }
                        }
                );



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                sendUserToLogin();

            }
        });

        enter_details.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(MainPage.this, Details.class);
                        intent.putExtra("phone_number", phone_number);
                        startActivity(intent);

                    }
                }
        );


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser == null){
            sendUserToLogin();
        }
    }

    private void sendUserToLogin() {
        Intent loginIntent = new Intent(MainPage.this, Login.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }


}