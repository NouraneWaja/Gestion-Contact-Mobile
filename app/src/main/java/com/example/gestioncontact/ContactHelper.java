package com.example.gestioncontact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactHelper extends SQLiteOpenHelper {

    //pour les users
    public static final String table_user="Users";
    public static final String col_id = "ID";
    public static final String col_nom_user="Nom";
    public static final String col_password="Password";

    String req_create_user = "CREATE TABLE " + table_user + " (" +
            col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            col_nom_user + " TEXT NOT NULL, " +
            col_password + " TEXT NOT NULL)";

    //pour les contacts
    public static final String table_contact="Contacts";
    public static final String col_nom="Nom";
    public static final String col_pseudo="Pseudo";
    public static final String col_phone="Num_tel";

    String req = "CREATE TABLE " + table_contact + " ("
            + col_pseudo + " TEXT PRIMARY KEY, "
            + col_nom + " TEXT NOT NULL, "
            + col_phone + " TEXT NOT NULL UNIQUE)";


    public ContactHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //lors de l'ouverture de la base pour la premiere fois
        sqLiteDatabase.execSQL(req_create_user);
        sqLiteDatabase.execSQL(req);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //modif de la version
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_user);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_contact);
        onCreate(sqLiteDatabase);
    }
}
