package com.example.gestioncontact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyContactRecyclerAdapter extends RecyclerView.Adapter<MyContactRecyclerAdapter.MyViewHolder> {

    Context con;
    ArrayList<Contact> data;

    public MyContactRecyclerAdapter(Context con, ArrayList<Contact> data) {
        this.con = con;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf=LayoutInflater.from(con);
        View v=inf.inflate(R.layout.viewcontact,null);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contact c=data.get(position);
        holder.tvnom.setText(c.nom);
        holder.tvpseudo.setText(c.pseudo);
        holder.tvnum.setText(c.phone);
    }

    //nb total des elts a afficher
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvnom,tvpseudo,tvnum;
        ImageView imaccount,imcall,imedit,imdelete;

        public MyViewHolder(@NonNull View v) {
            super(v);
            //recuperation des holders
            tvnom=v.findViewById(R.id.tv_nom_contact);
            tvpseudo=v.findViewById(R.id.tv_pseudo_contact);
            tvnum=v.findViewById(R.id.tv_phone_contact);
            imaccount=v.findViewById(R.id.account_photo);
            imcall=v.findViewById(R.id.call);
            imedit=v.findViewById(R.id.edit);
            imdelete=v.findViewById(R.id.delete);

            //events
            imdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //indice de l'elt selectionner
                    int position=getAdapterPosition();
                    //supprimer les donner depuis la base testi ken mawjouda se3a
                    data.remove(position);
                    notifyDataSetChanged();//refresh
                }
            });

            //chouf video el permission w badlou
            imcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Intent i=new Intent();
                    i.setAction(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:"+data.get(position).phone));
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


        }
    }
}
