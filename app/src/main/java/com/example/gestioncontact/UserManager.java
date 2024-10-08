package com.example.gestioncontact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserManager {
    SQLiteDatabase db=null;
    Context con;

    public UserManager(Context con) {
        this.con = con;
    }

    public void ouvrir(){
        ContactHelper helper=new ContactHelper(con,"mabase.db",null,2);
        db=helper.getWritableDatabase();
    }

    public long ajout(String nom, String mdp) {
        long a = 0;
        ContentValues values = new ContentValues();
        values.put(ContactHelper.col_nom, nom);
        values.put(ContactHelper.col_password, mdp);
        a = db.insert(ContactHelper.table_user, null, values);
        return a;
    }

    // Vérifier si l'utilisateur existe dans la base de données
    public boolean verifierUtilisateur(String nom, String password) {
        String[] columns = {ContactHelper.col_id};
        String selection = ContactHelper.col_nom_user + " = ? AND " + ContactHelper.col_password + " = ?";
        String[] selectionArgs = {nom, password};
        Cursor cursor = db.query(ContactHelper.table_user, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public void fermer(){
        if (db != null && db.isOpen()) {
            db.close(); // Ferme la connexion à la base de données
        }
    }


}
