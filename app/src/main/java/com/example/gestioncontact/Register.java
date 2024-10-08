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

public class Register extends AppCompatActivity {

    EditText edname,edpass;
    private Button btncreer;
    private Button btnqte;
    TextView connecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edpass=findViewById(R.id.edmdp_reg);
        edname=findViewById(R.id.ednom_reg);
        btncreer=findViewById(R.id.btnval_reg);
        btnqte=findViewById(R.id.btnqte_reg);
        connecter=findViewById(R.id.signinLink);

        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register.this.finish();
            }
        });

        btncreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom=edname.getText().toString();
                String mp=edpass.getText().toString();

            }
        });

        connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Register.this,MainActivity.class);
                startActivity(i);
                Register.this.finish();
            }
        });

        btncreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = edname.getText().toString();
                String mp = edpass.getText().toString();

                UserManager manager=new UserManager(Register.this);

                if (!nom.isEmpty() && !mp.isEmpty()) {
                    manager.ouvrir();
                    long result = manager.ajout(nom, mp);

                    if (result != -1) {
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Register.this, "Erreur lors de l'ajout de l'utilisateur", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}