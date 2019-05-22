package com.example.administrator.icare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.icare.Adapter.ListBeranda;
import com.example.administrator.icare.Model.ListBerandaModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class beranda_konselor extends AppCompatActivity {

    TextView username;
    String email, strUsername;
    private FirebaseAuth auth;
    private ListBeranda adapterBeranda;
    private LinkedList<ListBerandaModel> list;
    private RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refStatus = database.getReference("berandaSobat");
    DatabaseReference konselor = database.getReference("konselor");

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_beranda:
//                    Intent beranda = new Intent(Beranda.this, Beranda.class);
                    return true;
                case R.id.navigation_chat:
                    Intent chat = new Intent (beranda_konselor.this, Chat.class);
                    startActivity(chat);
                    return true;
                case R.id.navigation_profil:
                    Intent profil = new Intent (beranda_konselor.this,  profil_konselor.class);
                    startActivity(profil);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_konselor);
        auth = FirebaseAuth.getInstance();
//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        username = findViewById(R.id.username);
        list = new LinkedList<>();
        adapterBeranda = new ListBeranda(this, list);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setAdapter(adapterBeranda);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        ambilUsername();
        ambilStatus();
    }
    private void ambilUsername () {
        konselor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean check = false;
                for (DataSnapshot datasnap: dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) datasnap.getValue();
                    if(map.get("Email").toString().equalsIgnoreCase(email)){
                        strUsername = map.get("Username").toString();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ambilStatus(){
        refStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    ListBerandaModel save;
                    list.removeAll(list);
                    for (DataSnapshot datasnap: dataSnapshot.getChildren()) {
                        save = new ListBerandaModel();
                        Map<String, Object> map = (Map<String, Object>) datasnap.getValue();
                        save.setStatus(map.get("status").toString());
                        save.setUsername(map.get("username").toString());
                        list.addFirst(save);
                    }
                    adapterBeranda.addItem(list);
                    recyclerView.setAdapter(adapterBeranda);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            email = (currentUser.getEmail());
        }
    }

}
