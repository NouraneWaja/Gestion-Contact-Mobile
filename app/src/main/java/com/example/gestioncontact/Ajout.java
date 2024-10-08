package com.example.gestioncontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Ajout extends AppCompatActivity {

    private Button btnadd,btnretour;
    private TextView ednom,edpseudo,edphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnadd=findViewById(R.id.btnsave);
        btnretour=findViewById(R.id.btnretour);
        ednom=findViewById(R.id.ednom_ajout);
        edpseudo=findViewById(R.id.edpseudo_ajout);
        edphone=findViewById(R.id.edphone_ajout);

        btnretour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ajout.this.finish();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom=ednom.getText().toString();
                String pseudo=edpseudo.getText().toString();
                String phone=edphone.getText().toString();

                //Accueil.data.add(new Contact(nom, pseudo, phone));

                ContactManager manager = new ContactManager(Ajout.this);
                manager.ouvrir();

                long result = manager.ajout(nom, pseudo, phone);

                if (result != -1) {
                    Toast.makeText(Ajout.this, "Contact ajouté avec succès", Toast.LENGTH_LONG).show();
                    ednom.setText("");
                    edpseudo.setText("");
                    edphone.setText("");
                } else {
                    Toast.makeText(Ajout.this, "Échec de l'ajout", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}