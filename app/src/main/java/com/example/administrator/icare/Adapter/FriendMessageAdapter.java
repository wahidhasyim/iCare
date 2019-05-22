package com.example.administrator.icare.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.icare.Adapter.FriendMessageAdapter;
import com.example.administrator.icare.Model.FriendMessage;
import com.example.administrator.icare.R;

import java.util.ArrayList;
import java.util.List;

public class FriendMessageAdapter extends BaseAdapter {

    List<FriendMessage> messages = new ArrayList<>();
    Context context;
    String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public FriendMessageAdapter(Context context) {
        this.context = context;
    }

    public void add(ArrayList message) {
        this.messages = message;
        notifyDataSetChanged(); // to render the list we need to notify
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        FriendMessage message = messages.get(position);

        if(message.getSender().equalsIgnoreCase(email)){
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            holder.read = convertView.findViewById(R.id.read);
            holder.time = convertView.findViewById(R.id.time);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getText());
            if(message.getRead_status().equalsIgnoreCase("read")){
                holder.read.setText("Read");
            }else{
                holder.read.setText("");
            }
            holder.time.setText(message.getTime());
        }else{
            Log.d("sender_a", message.getSender());
            Log.d("sender_b", email);
            Log.d("sender_boolean", String.valueOf(message.getSender().equalsIgnoreCase(email)));
            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.avatar = (View) convertView.findViewById(R.id.avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);

            holder.name.setText(message.getSender());
            holder.messageBody.setText(message.getText());
            GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
//            drawable.setColor(Color.parseColor(message.getData().getColor()));
        }
        return convertView;
    }
}