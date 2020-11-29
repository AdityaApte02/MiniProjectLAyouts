package com.example.mini_project_phone_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActualPage extends AppCompatActivity {

    private TextView t;
    private EditText quantity;
    private EditText price;
    private Button confirm;

    private String fruitName;
    private String phone_number;
    private String TAG="Error";

    DatabaseReference databaseReferenceUsers= FirebaseDatabase.getInstance().getReference().child("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_page);

        fruitName=getIntent().getStringExtra("itemName");
        phone_number=getIntent().getStringExtra("phone_number");

        t=(TextView)findViewById(R.id.stockName);
        quantity=(EditText)findViewById(R.id.quantity);
        price=(EditText)findViewById(R.id.price);
        confirm=(Button)findViewById(R.id.confirm_button);



        t.setText(fruitName);

        if(price.getText().toString().equals("") ||  quantity.getText().toString().equals("")){
            confirm.setVisibility(View.INVISIBLE);
        }else{
            confirm.setVisibility(View.VISIBLE);
        }

        Stock stock=new Stock();
        stock.setStockName(fruitName);
        stock.setPrice(Double.parseDouble(price.getText().toString()));
        stock.setQuantity(Integer.parseInt(quantity.getText().toString()));


        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(user.getPhone().equals(phone_number)){
                        stock.setUser(user);
                        break;
                    }else{
                        continue;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
}