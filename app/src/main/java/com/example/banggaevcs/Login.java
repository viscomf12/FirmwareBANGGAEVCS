package com.example.banggaevcs;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    ImageView unair, bangga;
    TextView bluetoothevcs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        unair = findViewById(R.id.unair);
        bluetoothevcs = findViewById(R.id.bluetoothevcs);
        bangga = findViewById(R.id.bangga);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        unair.startAnimation(fadeInAnimation);
        bluetoothevcs.startAnimation(fadeInAnimation);
        bangga.startAnimation(fadeInAnimation);

        bangga.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        unair.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        bluetoothevcs.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}