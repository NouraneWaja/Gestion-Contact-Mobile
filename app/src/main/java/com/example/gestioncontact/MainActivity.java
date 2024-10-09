package com.example.gestioncontact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    private CheckBox btnconnecte;

    // Pour SharedPreferences
    SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);//mettre un fichir xml dans notre ecran


        //recuperation des composantes
        edmp=findViewById(R.id.edmdp_auth);//hethi el zone de saisie
        ednom=findViewById(R.id.ednom_auth);
        btnval=findViewById(R.id.btnval_auth);
        btnqte=findViewById(R.id.btnqte_auth);
        register=findViewById(R.id.signupLink);
        btnconnecte = findViewById(R.id.stay_connected);

        // Initialiser SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Vérifier si l'utilisateur a coché "Rester connecté"
        checkStayConnected();

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
                String nom = ednom.getText().toString();
                String mp = edmp.getText().toString();

                if (nom.isEmpty() || mp.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez entrer votre nom et mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserManager userManager = new UserManager(MainActivity.this);
                userManager.ouvrir();

                // Vérifier si l'utilisateur est inscrit
                boolean estInscrit = userManager.verifierUtilisateur(nom, mp);
                if (estInscrit) {
                    // Si l'utilisateur est inscrit et "Rester connecté" est coché
                    if (btnconnecte.isChecked()) {
                        // Sauvegarder les informations dans SharedPreferences
                        editor.putBoolean("stayConnected", true);
                        editor.putString("username", nom);
                        editor.putString("password", mp);
                        editor.apply();
                    } else {
                        // Effacer les informations de connexion si "Rester connecté" n'est pas coché
                        editor.clear();
                        editor.apply();
                    }

                    // Passer à l'accueil
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

    // Méthode pour vérifier si "Rester connecté" est activé
    private void checkStayConnected() {
        // Récupérer la valeur du booléen "stayConnected"
        boolean stayConnected = sharedPreferences.getBoolean("stayConnected", false);
        if (stayConnected) {
            // Si l'utilisateur a coché "Rester connecté", récupérer les informations sauvegardées
            String savedUsername = sharedPreferences.getString("username", "");
            // Passer automatiquement à l'accueil
            Intent i = new Intent(MainActivity.this, Accueil.class);
            i.putExtra("USER", savedUsername);
            startActivity(i);
            finish();
        }
    }
}