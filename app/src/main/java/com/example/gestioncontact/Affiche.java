package com.example.gestioncontact;

import static com.example.gestioncontact.Accueil.data;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Affiche extends AppCompatActivity {

    private RecyclerView rv;
    private ImageButton btnback;
    private SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_affiche);

        //list view
        rv=findViewById(R.id.rv);

        ContactManager manager=new ContactManager(Affiche.this);
        manager.ouvrir();

        data=manager.getAllContacts();

        //MyContactAdapter ad=new MyContactAdapter(Affiche.this,data);
        MyContactRecyclerAdapter ad=new MyContactRecyclerAdapter(Affiche.this, data);
        rv.setAdapter(ad);

        LinearLayoutManager manager1=new LinearLayoutManager(Affiche.this,LinearLayoutManager.VERTICAL,true);

        GridLayoutManager manager2=new GridLayoutManager(Affiche.this,2,GridLayoutManager.VERTICAL,true);
        rv.setLayoutManager(manager2);

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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
    }
}