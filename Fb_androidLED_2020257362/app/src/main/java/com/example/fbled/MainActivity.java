package com.example.fbled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button onButton;
    Button offButton;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onButton = findViewById(R.id.btn01);
        offButton = findViewById(R.id.btn02);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        setTitle(("LED Remote Control"));

        // Write a message to the database
        // 파이어베이스 연동을 위한 코드
        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LED_STATUS");

        myRef.setValue("Hello, World!");
        textView.setText(myRef.getKey());

        // read from the Database, addValue Event Listenning
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ledState = (String) dataSnapshot.getValue();
                //String value = dataSnapshot.getValue(String.class);
                textView.setText("LED STATUS : " + ledState);
                // 버튼을 클릭했을때 기본적으로뜸
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // LED ON 버튼을 클릭했을때 성공적으로 연동이 되었다면 LED가 켜짐 그리고 안드로이드 화면에 있는 이미지와 텍스트가 바뀜
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.YELLOW);
                // write to the Database
                myRef.setValue("ON");
                imageView.setImageResource(R.drawable.on);
            }
        });
        // LED OFF 버튼을 클릭했을때 성공적으로 연동이 되었다면 LED가 꺼짐짐 그리고 안드로이드 화면에 있 이미지와 텍스트가 바뀜
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.GREEN);
                // write to the Database
                myRef.setValue("OFF");
                imageView.setImageResource(R.drawable.off);
            }
        });
    }
}