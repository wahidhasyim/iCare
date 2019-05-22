package com.example.administrator.icare;

import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;


public class Pilih extends AppCompatActivity {

    private PopupWindow popUpPilih;
    private CardView sobat, konselor;
    private Button back;
    private ImageView informasi;
    private Context mContext;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih);

//        informasi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Initialize a new instance of LayoutInflater service
//                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//
//                View customView = inflater.inflate(R.layout.activity_popup_pilih, null);
//
//                popUpPilih = new PopupWindow(
//                        customView,
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.WRAP_CONTENT
//                );
//                if(Build.VERSION.SDK_INT>=21){
//                    popUpPilih.setElevation(5.0f);
//                }
//
//                // Get a reference for the custom view close button
//                Button close = (Button) customView.findViewById(R.id.close);
//
//                close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // Dismiss the popup window
//                        popUpPilih.dismiss();
//
//                    }
//                });
//
//                popUpPilih.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
//            }
//        });

        Tombol();
    }

            public void Tombol() {
                sobat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sobat = new Intent(Pilih.this, Daftar.class);
                        startActivity(sobat);
                    }
                });

                sobat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent konselor = new Intent(Pilih.this, Daftar.class);
                        startActivity(konselor);
                    }
                });
            }
        }



