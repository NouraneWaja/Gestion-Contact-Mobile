package com.example.gestioncontact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SplashScreen extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        // Récupération des vues
        ImageView logo = findViewById(R.id.logo);
        TextView textView = findViewById(R.id.textView2);

        // Initialisation du texte avec alpha à 0 (invisible)
        textView.setAlpha(0f);

        // Animation du logo (scale up)
        logo.setScaleX(0f);
        logo.setScaleY(0f);
        logo.animate().scaleX(1f).scaleY(1f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                // Animation du texte (fade-in) après l'animation du logo
                textView.animate().alpha(1f).setDuration(1000).start();
            }
        }).start();

        // Délai pour passer à MainActivity après l'animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // Appliquer une transition d'animation entre les activités
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 2500); // 3 secondes de délai avant de passer à MainActivity

    }
}
