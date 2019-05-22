package com.example.administrator.icare.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.icare.Beranda;
import com.example.administrator.icare.FriendChat;
import com.example.administrator.icare.Model.KnowMore;
import com.example.administrator.icare.Model.ListBerandaModel;
import com.example.administrator.icare.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BerandaKonselor extends RecyclerView.Adapter<BerandaKonselor.CustomViewHolder>{

    Context context;
    List<ListBerandaModel> list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference sobat = database.getReference("konselor");

    public BerandaKonselor(Context context, List<ListBerandaModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_list, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return new CustomViewHolder(v);
    }

    public void addItem(List<ListBerandaModel> mData) {
        this.list = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        holder.username.setText(list.get(position).getUsername());
        holder.status.setText(list.get(position).getStatus());
        sobat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnap: dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) datasnap.getValue();
                    if(map.get("Username").toString().equalsIgnoreCase(list.get(position).getUsername())){
                        Glide.with(context).load(map.get("Foto").toString()).into(holder.image);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(context, FriendChat.class);
                a.putExtra("username", list.get(position).getUsername());
                context.startActivity(a);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView image;
        private TextView username, status;
        private LinearLayout linear;
        public CustomViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.foto);
            username = itemView.findViewById(R.id.username);
            status = itemView.findViewById(R.id.status);
            linear = itemView.findViewById(R.id.toKlik);

        }
    }
}
