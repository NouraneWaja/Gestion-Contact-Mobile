package com.example.gestioncontact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ContactManager {

    SQLiteDatabase db=null;
    Context con;

    public ContactManager(Context con) {
        this.con = con;
    }

    //ouvrir la bdd
    public void ouvrir(){
        ContactHelper helper=new ContactHelper(con,"mabase.db",null,2);
        db=helper.getWritableDatabase();
    }

    //ajouter des elts dans la bdd
    public long ajout(String nom,String pseudo, String phone){
        long a=0;
        ContentValues values=new ContentValues();
        values.put(ContactHelper.col_pseudo,pseudo);
        values.put(ContactHelper.col_nom,nom);
        values.put(ContactHelper.col_phone,phone);
        a=db.insert(ContactHelper.table_contact,null,values);
        return a;
    }

    public ArrayList<Contact> getAllContacts(){
        ArrayList<Contact> l=new ArrayList<Contact>();
        Cursor cr=db.query(ContactHelper.table_contact,
                new String[]{ContactHelper.col_pseudo,ContactHelper.col_nom,ContactHelper.col_phone},
                null,null,null,null,ContactHelper.col_pseudo + " ASC");
        cr.moveToFirst();
        while (!cr.isAfterLast()){
        String i1=cr.getString(0);
        String i2=cr.getString(1);
        String i3=cr.getString(2);
        l.add(new Contact(i2,i1,i3));
        cr.moveToNext();
        }

        return l;
    }

    // Méthode pour mettre à jour un contact
    public int updateContact( String pseudo,String nom, String phone) {
        ContentValues values = new ContentValues();
        values.put(ContactHelper.col_nom, nom);
        values.put(ContactHelper.col_phone, phone);

        // Mise à jour de la base de données
        return db.update(ContactHelper.table_contact, values,
                ContactHelper.col_pseudo + " = ?", new String[]{pseudo});
    }

    //supprimer des elts de la bdd
    public void supprimer(String pseudo){
        db.delete(ContactHelper.table_contact,
                ContactHelper.col_pseudo + " = ?", new String[]{pseudo});
    }

    //fermer la bdd
    public void fermer(){
        if (db != null && db.isOpen()) {
            db.close(); // Ferme la connexion à la base de données
        }
    }
}
