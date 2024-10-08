package com.example.gestioncontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView register;

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
        register=findViewById(R.id.signupLink);

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

                if (nom.isEmpty() || mp.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez entrer votre nom et mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserManager userManager = new UserManager(MainActivity.this);
                userManager.ouvrir();

                // Vérifier si l'utilisateur est inscrit
                boolean estInscrit = userManager.verifierUtilisateur(nom, mp);
                if (estInscrit) {
                    // L'utilisateur est inscrit, passer à l'accueil
                    Intent i = new Intent(MainActivity.this, Accueil.class);
                    i.putExtra("USER", nom);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Nom ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,Register.class);
                startActivity(i);
                MainActivity.this.finish();
            }
        });
    }
}