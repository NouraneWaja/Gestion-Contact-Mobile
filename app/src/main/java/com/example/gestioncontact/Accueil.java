package com.example.gestioncontact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Accueil extends AppCompatActivity {

    static ArrayList<Contact> data=new ArrayList<Contact>();

    private TextView tvusername;
    private Button btnajout,btnaff;
    private TextView tvdeconnecter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accueil);

        tvusername=findViewById(R.id.tvuser_acc);
        btnajout=findViewById(R.id.btnajout_acc);
        btnaff=findViewById(R.id.btnaffiche_acc);
        tvdeconnecter=findViewById(R.id.tvdeconnecte_acc);

        Intent x=this.getIntent();
        Bundle b=x.getExtras();
        String u=b.getString("USER");

        tvusername.setText("Accueil de M. "+u);

        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Accueil.this,Ajout.class);
                startActivity(i);
            }
        });

        btnaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Accueil.this,Affiche.class);
                startActivity(i);
            }
        });

        tvdeconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.editor != null) {
                    MainActivity.editor.clear();  // Vider toutes les données de SharedPreferences
                    MainActivity.editor.apply();
                }

                Intent i = new Intent(Accueil.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


}