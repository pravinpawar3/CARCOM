package com.project.carcom.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ncorti.slidetoact.SlideToActView;
import com.project.carcom.R;
import com.project.carcom.home.MainActivity;

public class Emergency extends AppCompatActivity {
    private RelativeLayout unlockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        unlockView = findViewById(R.id.unlockView);
        TextView textView = findViewById(R.id.textView3);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Carcom/prav/EMG");
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                textView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                textView.setText("0");
                databaseRef.setValue(-1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data successfully updated
                                Toast.makeText(Emergency.this, "Car Locked", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while updating data
                                Toast.makeText(Emergency.this, "Try Again", Toast.LENGTH_SHORT).show();
                            }
                        });
                Toast.makeText(Emergency.this, "SOS message sent!", Toast.LENGTH_SHORT).show();
            }
        }.start();
        SlideToActView slideToActView = findViewById(R.id.slide);
        slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                databaseRef.setValue(0)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data successfully updated
                                Toast.makeText(Emergency.this, "Car Locked", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while updating data
                                Toast.makeText(Emergency.this, "Try Again", Toast.LENGTH_SHORT).show();
                            }
                        });
                Intent intent = new Intent(Emergency.this, MainActivity.class);
                startActivity(intent);
                Emergency.this.finish();
                Toast.makeText(Emergency.this, "Swipe detected", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
