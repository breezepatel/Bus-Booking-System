package com.shreebrahmanitravels.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class seatManagement extends AppCompatActivity {
    Button setvalue, resetvalue;
    String currentdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_management);
        setvalue = findViewById(R.id.setvalues);
        resetvalue = findViewById(R.id.resetvalue);
final String username=new sessionmanager(getBaseContext()).getuserdetail();
        currentdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        setvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ref = firebaseDatabase.getReference("Booking");
                final DatabaseReference ref1 = firebaseDatabase.getReference("users");


                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            ref1.child(Objects.requireNonNull(dsp.getKey())).child("seat").child(currentdate).child("seat ID").setValue("null");
                            if (ref1 != null && this != null) {
                                ref1.removeEventListener(this);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                for (int busno = 1; busno <= 5; busno++) {
                    for (int i = 1; i <= 6; i++) {
                        for (int j = 1; j <= 2; j++) {
                            ref.child(currentdate).child("SB" + busno).child("L" + i + j).child("isbooked").setValue("null");

                            ref.child(currentdate).child("SB" + busno).child("L" + i + j).child("username").setValue("null");

                        }


                    }
                }

                for (int busno = 1; busno <= 5; busno++) {
                    for (int i = 1; i <= 6; i++) {
                        for (int j = 1; j <= 3; j++) {
                            ref.child(currentdate).child("SB" + busno).child("R" + i + j).child("isbooked").setValue("null");

                            ref.child(currentdate).child("SB" + busno).child("R" + i + j).child("username").setValue("null");
                        }


                    }
                }
            }
        });
    }
}