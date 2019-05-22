package com.example.administrator.icare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Login extends AppCompatActivity {

    private Button login;
    private EditText email, password;

    private static String TAG = "LoginActivity";
    private FirebaseAuth auth;
    private ProgressDialog progress;
    private String emailAuth;
    private TextView belumPunyaAkun;
    public static String checkStatusUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refUser = database.getReference("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        belumPunyaAkun = findViewById(R.id.belumPunyaAkun);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equalsIgnoreCase(null) || email.getText().toString().equalsIgnoreCase("")) {
                    if (password.getText().toString().equalsIgnoreCase(null) || password.getText().toString().equalsIgnoreCase("")) {
                        email.setError("Email is required");
                        password.setError("Password is required");
                    } else {
                        email.setError("Email is required");
                    }
                } else {
                    if (password.getText().toString().equalsIgnoreCase(null) || password.getText().toString().equalsIgnoreCase("")) {
                        password.setError("Password is required");
                    } else {
                        login();
                    }
                }
            }
        });

        belumPunyaAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Pilih.class));
            }
        });

    }

    private void login() {
        final String strEmail = email.getText().toString();
        final String strPassword = password.getText().toString();
        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait...");
        progress.show();
        auth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    if (strPassword.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password must not less than 6", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Account not registered ", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
                } else {
                    progress.dismiss();
                    checkUser(strEmail);
                    finish();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            checkUser(currentUser.getEmail());
        }
    }

    private void checkUser(final String email) {
        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean check = false;
                for (DataSnapshot datasnap: dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) datasnap.getValue();
                    if(map.get("email").toString().equalsIgnoreCase(email)){
                        if((map.get("status").toString().equalsIgnoreCase("sobat"))){
                            checkStatusUser = "Sobat";
                            startActivity(new Intent(Login.this, Beranda.class));
                        }
                        else {
                            checkStatusUser = "Konselor";
                            startActivity(new Intent(Login.this, beranda_konselor.class));
                        }
                        check = true;
                        finish();
                    }
                }
                if(!check){
                    Toast.makeText(Login.this, "Email tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}




