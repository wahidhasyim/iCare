package com.example.administrator.icare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    private TextView mTextMessage;
    private ListView listChat;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_beranda:
                    Intent beranda;
                    if (Login.checkStatusUser == "Sobat") {
                        beranda = new Intent (Chat.this, Beranda.class);
                    } else {
                        beranda = new Intent (Chat.this, beranda_konselor.class);
                    }
                    startActivity(beranda);
                    return true;
                case R.id.navigation_chat:
                    return true;
                case R.id.navigation_profil:
                    Intent profil = new Intent (Chat.this, profil_konselor.class);
                    if (Login.checkStatusUser == "Sobat") {
                        profil = new Intent (Chat.this, profile_user.class);
                    } else {
                        profil = new Intent (Chat.this, profil_konselor.class);
                    }
                    startActivity(profil);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mTextMessage = (TextView) findViewById(R.id.message);
        TextView tvUsername = findViewById(R.id.username);
        CircleImageView img = findViewById(R.id.foto);
        final TextView lastMsg = findViewById(R.id.lastMessage);
        final TextView waktu = findViewById(R.id.waktu);
//        listChat = findViewById(R.id.list_chat);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (Login.checkStatusUser == "Sobat") {
            img.setImageResource(R.drawable.doctor_1);
            tvUsername.setText("Andi");
        } else {
            img.setImageResource(R.drawable.boy);
            tvUsername.setText("Wahid");
        }

        Query lastQuery = database.getReference().child("message").orderByKey().limitToLast(1);
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String lastText = "";
                String lastTime = "";
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    lastText = child.child("text").getValue().toString();
                    lastTime = child.child("time").getValue().toString();
                }
                lastMsg.setText(lastText);
                waktu.setText(lastTime);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Handle possible errors.
            }
        });

        tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendChat = new Intent (Chat.this, FriendChat.class);
                startActivity(friendChat);
            }
        });


    }

}
