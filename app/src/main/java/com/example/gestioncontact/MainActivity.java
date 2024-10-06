package com.example.gestioncontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //declaration
    EditText ednom,edmp;
    private Button btnval;
    private Button btnqte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);//mettre un fichir xml dans notre ecran
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //recuperation des composantes
        edmp=findViewById(R.id.edmdp_auth);//hethi el zone de saisie
        ednom=findViewById(R.id.ednom_auth);
        btnval=findViewById(R.id.btnval_auth);
        btnqte=findViewById(R.id.btnqte_auth);

        //ecouteur d'action
        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });

        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom=ednom.getText().toString();//eli fi wosst el zone de saisie
                String mp=edmp.getText().toString();
                if(nom.equalsIgnoreCase("")&&mp.equals("")){
                    //context:activitecourant = qui occupe l'ecran
                    Intent i= new Intent(MainActivity.this,Accueil.class);
                    i.putExtra("USER",nom);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this, "valeur nn valide", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}