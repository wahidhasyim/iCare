package com.example.administrator.icare;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.icare.Adapter.FriendMessageAdapter;
import com.example.administrator.icare.Model.FriendMessage;
import com.example.administrator.icare.Model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class FriendChat extends AppCompatActivity {

    private String roomName = "room1", email;
    private EditText editText;
    private FriendMessageAdapter messageAdapter;
    private ListView messagesView;
    private FirebaseAuth auth;

    private TextView day, week, percentage;
    private String username;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_chat);

        Intent i = getIntent();
        username = i.getStringExtra("username");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = mFirebaseDatabase.getReference("message");

        auth = FirebaseAuth.getInstance();
        editText = (EditText) findViewById(R.id.editText);

        messageAdapter = new FriendMessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);
        getChat();

        // initiate rating bar and a button
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button submitButton = (Button) findViewById(R.id.submit);
        LinearLayout layout_rating = findViewById(R.id.layout_rating);

        if (Login.checkStatusUser == "Konselor") {
            layout_rating.setVisibility(View.GONE);
        }

        // perform click event on button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get values and then displayed in a toast
                String totalStars = "Total Stars:: " + ratingBar.getNumStars();
                String rating = "Rating :: " + ratingBar.getRating();
                Toast.makeText(getApplicationContext(), totalStars + "\n" + rating, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        email = currentUser.getEmail();
        messageAdapter.setEmail(email);
    }

    private void getChat(){
        mFirebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    FriendMessage message;
                    ArrayList<FriendMessage> messageArrayList = new ArrayList<>();
                    for  (DataSnapshot datasnap: dataSnapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) datasnap.getValue();
                        message = new FriendMessage();
                        message.setText(map.get("text").toString());
                        message.setSender(map.get("sender").toString());
                        message.setReceiver(map.get("receiver").toString());
                        message.setRead_status(map.get("read_status").toString());
                        message.setTime(map.get("time").toString());
                        messageArrayList.add(message);

                    }
                    messageAdapter.add(messageArrayList);
                    messagesView.setAdapter(messageAdapter);

                    scrollMyListViewToBottom();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void sendMessage(View view) {
        String message = editText.getText().toString();
        String id = UUID.randomUUID().toString();
        Date currentTime = Calendar.getInstance().getTime();
        Log.d("date","Current time => " + currentTime);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String time = df.format(currentTime);
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = df2.format(currentTime);
        Log.d("date","Current time => " + time);
        System.out.println("date : "+date);
        Message m = new Message();
        m.setReceiver("all");
        m.setSender(email);
        m.setText(message);
        m.setTime(String.valueOf(time));
        m.setRead_status("not read yet");
        mFirebaseDatabaseReference.child(date).setValue(m);
        editText.getText().clear();
        getChat();
    }

    private void scrollMyListViewToBottom() {
        messagesView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                messagesView.setSelection(messagesView.getCount() - 1);
            }
        });
    }

}
