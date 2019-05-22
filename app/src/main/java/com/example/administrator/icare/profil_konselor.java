package com.example.administrator.icare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class profil_konselor extends AppCompatActivity {

    private TextView mTextMessage;
    private FirebaseAuth auth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_beranda:
                    Intent beranda = new Intent(profil_konselor.this, beranda_konselor.class);
                    startActivity(beranda);
                    return true;
                case R.id.navigation_chat:
                    Intent chat = new Intent(profil_konselor.this, Chat.class);
                    startActivity(chat);
                    return true;
                case R.id.navigation_profil:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_konselor);

        auth = FirebaseAuth.getInstance();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Button keluar = findViewById(R.id.keluar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finishAffinity();
                Intent login = new Intent (profil_konselor.this, Login.class);
                startActivity(login);
            }
        });
    }

}
