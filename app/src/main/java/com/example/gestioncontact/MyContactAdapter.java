package com.example.gestioncontact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyContactAdapter extends BaseAdapter {
    Context con;
    ArrayList<Contact> data;

    public MyContactAdapter(Context con, ArrayList<Contact> data) {
        this.con = con;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //creation d'un view holder
        LayoutInflater inf=LayoutInflater.from(con);
        View v=inf.inflate(R.layout.viewcontact,null);

        //recuperation des holders
        TextView tvnom=v.findViewById(R.id.tv_nom_contact);
        TextView tvpseudo=v.findViewById(R.id.tv_pseudo_contact);
        TextView tvnum=v.findViewById(R.id.tv_phone_contact);
        ImageView imaccount=v.findViewById(R.id.account_photo);
        ImageView imcall=v.findViewById(R.id.call);
        ImageView imedit=v.findViewById(R.id.edit);
        ImageView imdelete=v.findViewById(R.id.delete);

        //affectation des holders
        Contact c=data.get(position);
        tvnom.setText(c.nom);
        tvpseudo.setText(c.pseudo);
        tvnum.setText(c.phone);

        //Evenements
        imdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //supprimer les donner depuis la base testi ken mawjouda se3a
                data.remove(position);
                notifyDataSetChanged();//refresh
            }
        });

        //chouf video el permission w badlou
        imcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+c.phone));
                con.startActivity(i);
            }
        });

        imedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(con);
                alert.setTitle("Edition");
                LayoutInflater inf=LayoutInflater.from(con);
                View v=inf.inflate(R.layout.viewdialogue,null);
                alert.show();
            }
        });


        return v;
    }
}
