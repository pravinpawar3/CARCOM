package com.project.carcom.startup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.carcom.R;
import com.project.carcom.home.MainActivity;

public class Login extends AppCompatActivity {
    private EditText emailId, password_;
    private Button auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailId = (EditText) findViewById(R.id.email);
        password_ = (EditText) findViewById(R.id.password);
        auth = (Button) findViewById(R.id.auth);
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_Id = emailId.getText().toString().trim();
                String password = password_.getText().toString().trim();
                if (!TextUtils.isEmpty(email_Id) && !TextUtils.isEmpty(password)) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email_Id, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("email", email_Id);
                                startActivity(intent);
                                Login.this.finish();
                            } else {
                                Toast.makeText(Login.this, "Email or Password incorrect.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Email or Password is empty.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}