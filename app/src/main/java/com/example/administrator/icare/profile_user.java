package com.example.administrator.icare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class profile_user extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_beranda:
                    Intent beranda = new Intent(profile_user.this, Beranda.class);
                    startActivity(beranda);
                    return true;
                case R.id.navigation_chat:
                    Intent chat = new Intent(profile_user.this, Chat.class);
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
        setContentView(R.layout.activity_profile_user);
        auth = FirebaseAuth.getInstance();
        Button keluar = findViewById(R.id.keluar);
        LinearLayout konAndi = findViewById(R.id.konAndi);
        LinearLayout temAndi = findViewById(R.id.temAndi);
        konAndi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent (profile_user.this, FriendChat.class);
                startActivity(chat);
            }
        });
        temAndi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent (profile_user.this, FriendChat.class);
                startActivity(chat);
            }
        });
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finishAffinity();
                Intent logout = new Intent (profile_user.this, Login.class);
                startActivity(logout);
            }
        });
    }
}
