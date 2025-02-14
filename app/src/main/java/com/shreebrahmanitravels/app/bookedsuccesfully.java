package com.shreebrahmanitravels.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class bookedsuccesfully extends AppCompatActivity {
Button gohome;

Button tryagain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_bookedsuccesfully);
   gohome=findViewById(R.id.gohome_succesbook);
        AlertDialog dialog=null;
        final AlertDialog.Builder alert = new AlertDialog.Builder(bookedsuccesfully.this);

        LayoutInflater inflaterdialoge = this.getLayoutInflater();
        alert.setView(inflaterdialoge.inflate(R.layout.errorbookingseat, null));
        dialog = alert.create();
final String busroute = getIntent().getStringExtra("bus route");
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        final Handler handler = new Handler(Looper.getMainLooper());
        final AlertDialog finalDialog = dialog;

final String username=new sessionmanager(this).getuserdetail();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                final String currentdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String path = "Booking/" + currentdate + "/"+busroute+"/" + getIntent().getStringExtra("id") + "/username";
                System.out.println(path);
                final DatabaseReference ref = firebaseDatabase.getReference(path);
                final DatabaseReference ref2 = firebaseDatabase.getReference("users");




                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue().toString().equals("null")) {

                            ref2.child(username).child("seat").child(currentdate).child("busroute").setValue("null");
                            ref2.child(username).child("seat").child(currentdate).child("seat ID").setValue("null");

                            finalDialog.show();
                            if (ref != null && this != null)
                                ref.removeEventListener(this);




                        } else if (!snapshot.getValue().toString().equals(username)) {
                            finalDialog.show();

                            ref2.child(username).child("seat").child(currentdate).child("busroute").setValue("null");
                            ref2.child(username).child("seat").child(currentdate).child("seat ID").setValue("null");
                            if (ref != null && this != null)
                                ref.removeEventListener(this);


                        }else

                        {
                            if (ref != null && this != null)
                                ref.removeEventListener(this);

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }, 1000);



        Button tryagain = dialog.findViewById(R.id.tryagainonerrorpagebookingseat);


   gohome.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Intent intent = new Intent(bookedsuccesfully.this,welcomePage.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
       }


   });


    }

    @Override
    public void onBackPressed() {

    }
}