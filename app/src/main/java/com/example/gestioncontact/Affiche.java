package com.example.gestioncontact;

import static com.example.gestioncontact.Accueil.data;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Affiche extends AppCompatActivity {

    private ListView lv;
    private ImageButton btnback;
    private SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_affiche);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //list view
        lv=findViewById(R.id.lv);

        ContactManager manager=new ContactManager(Affiche.this);
        manager.ouvrir();

        data=manager.getAllContacts();

        //simple_list_item houwa simple textview
        ArrayAdapter ad=new ArrayAdapter(Affiche.this, android.R.layout.simple_list_item_1,data);
        lv.setAdapter(ad);


        //btn back
        btnback=findViewById(R.id.btn_back_aff);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Affiche.this.finish();
            }
        });

        //barre de recherche
        sv=findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // No action needed here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ad.getFilter().filter(newText);
                return true;
            }
        });
    }
}