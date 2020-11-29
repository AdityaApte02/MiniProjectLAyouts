package com.example.mini_project_phone_auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmUpload extends AppCompatActivity {

    private TextView itemName;
    private EditText quantity;
    private EditText price;
    private Button confirm;

    private String fruitName;
    private String phone_number;
    private String TAG="Error";

     private int flag=0;

    DatabaseReference databaseReferenceUsers= FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference databaseReferenceItems= FirebaseDatabase.getInstance().getReference().child("Items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "In ConfrimUpload");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_upload);



        fruitName=getIntent().getStringExtra("itemName");
        phone_number=getIntent().getStringExtra("phone_number");

        itemName =(TextView)findViewById(R.id.stockName);
        quantity=(EditText)findViewById(R.id.quantity);
        price=(EditText)findViewById(R.id.price);
        confirm=(Button)findViewById(R.id.confirm_button);

        itemName.setText(fruitName);

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

confirm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        databaseReferenceItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Stock stock = snapshot.getValue(Stock.class);
                    if(stock.getUser().getPhone().equals(phone_number)){

                            if (stock.getItemName().equals(fruitName)) {

                            int newQuantity = stock.getQuantity() + Integer.parseInt(quantity.getText().toString());
                            stock.setQuantity(newQuantity);
                            stock.setPrice(Double.parseDouble(price.getText().toString()));
                            break;
                        }
                    } else {

                        databaseReferenceItems.push().setValue(stock, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError != null) {

                                    Toast.makeText(ConfirmUpload.this, "Data could not saved", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(ConfirmUpload.this, "Data saved successfully", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    });

}

    }

