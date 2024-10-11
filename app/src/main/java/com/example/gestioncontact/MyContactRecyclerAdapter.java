package com.example.gestioncontact;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyContactRecyclerAdapter extends RecyclerView.Adapter<MyContactRecyclerAdapter.MyViewHolder> {

    Context con;
    ArrayList<Contact> data;
    ArrayList<Contact> dataFull; // Liste complète pour filtrage

    public MyContactRecyclerAdapter(Context con, ArrayList<Contact> data) {
        this.con = con;
        this.data = data;
        this.dataFull = new ArrayList<>(data); // Cloner la liste originale
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

    // Méthode pour filtrer la liste des contacts
    public void filter(String text) {
        data.clear();
        if (text.isEmpty()) {
            data.addAll(dataFull);
        } else {
            text = text.toLowerCase();
            for (Contact item : dataFull) {
                if (item.pseudo.toLowerCase().contains(text)|| item.nom.toLowerCase().contains(text) || item.phone.contains(text)) {
                    data.add(item);
                }
            }
        }
        notifyDataSetChanged(); // Rafraîchir la liste
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
                    // Créer un dialogue de confirmation
                    AlertDialog.Builder alert = new AlertDialog.Builder(con);

                    // Charger la vue personnalisée
                    LayoutInflater inflater = LayoutInflater.from(con);
                    View dialogView = inflater.inflate(R.layout.viewdelete, null);
                    alert.setView(dialogView);

                    // Récupérer les boutons
                    Button btnAnnuler = dialogView.findViewById(R.id.button_annuler);
                    Button btnConfirmer = dialogView.findViewById(R.id.button_confirmer);

                    // Créer et afficher le dialogue
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                    int position = getAdapterPosition();

                    Contact contact = data.get(position);

                    btnConfirmer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ContactManager contactManager = new ContactManager(con);
                            contactManager.ouvrir();
                            contactManager.supprimer(contact.pseudo);
                            contactManager.fermer();

                            // Supprimer de la liste des données locales
                            data.remove(position);
                            notifyDataSetChanged(); // Actualiser l'affichage

                            // Fermer le dialogue
                            alertDialog.dismiss();
                        }
                    });

                    // Action pour le bouton "Annuler"
                    btnAnnuler.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Fermer le dialogue sans supprimer
                            alertDialog.dismiss();
                        }
                    });
                }
            });


            imcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Récupérer la position de l'élément cliqué 
                    int position = getAdapterPosition();

                    // Vérifier si la permission d'appeler est accordée
                    if (ContextCompat.checkSelfPermission(con, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        // Si la permission est accordée, lancer l'appel
                        call(data.get(position).phone);
                    } else {
                        // Si la permission n'est pas accordée, la demander
                        ActivityCompat.requestPermissions((Activity) con, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                }
            });


            imedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Création d'une boîte de dialogue
                    AlertDialog.Builder alert = new AlertDialog.Builder(con);

                    // Charger la vue personnalisée
                    LayoutInflater inf = LayoutInflater.from(con);
                    View v = inf.inflate(R.layout.viewdialogue, null);

                    // Récupérer les EditText
                    EditText editNom = v.findViewById(R.id.edit_nom);
                    EditText editTelephone = v.findViewById(R.id.edit_telephone);

                    // Pré-remplir les champs avec les données actuelles du contact
                    int position = getAdapterPosition();
                    Contact contact = data.get(position);
                    editNom.setText(contact.nom);
                    editTelephone.setText(contact.phone);

                    // Ajouter la vue personnalisée à l'alerte
                    alert.setView(v);

                    // Créer et afficher le dialogue
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                    //bouton Enregistrer
                    Button btnSave = v.findViewById(R.id.btn_save);
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Récupérer les nouvelles valeurs entrées par l'utilisateur
                            String nouveauNom = editNom.getText().toString();
                            String nouveauPhone = editTelephone.getText().toString();

                            // Vérifier si le numéro de téléphone existe déjà
                            ContactManager contactManager = new ContactManager(con);
                            contactManager.ouvrir();
                            boolean phoneExists = contactManager.isPhoneNumberExists(nouveauPhone);
                            contactManager.fermer();

                            if (phoneExists && !nouveauPhone.equals(contact.phone)) {
                                // Afficher un message d'erreur
                                Toast.makeText(con, "Ce numéro de téléphone existe déjà.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Mettre à jour les informations dans la base de données
                                contactManager.ouvrir();
                                contactManager.updateContact(contact.pseudo, nouveauNom, nouveauPhone);
                                contactManager.fermer();

                                // Mettre à jour les données locales et rafraîchir l'affichage
                                contact.nom = nouveauNom;
                                contact.phone = nouveauPhone;
                                notifyDataSetChanged();

                                // Fermer la boîte de dialogue
                                alertDialog.dismiss();
                            }
                        }
                    });
                    //bouton Annuler
                    Button btnCancel = v.findViewById(R.id.btn_cancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Fermer le dialogue
                            alertDialog.dismiss();
                        }
                    });
                }
            });


        }

        private void call(String phoneNumber) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));

            // Vérifier une dernière fois si la permission est accordée avant de lancer l'appel
            if (ActivityCompat.checkSelfPermission(con, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                con.startActivity(intent);
            }
        }

        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (requestCode == 1) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int position = getAdapterPosition();
                    call(data.get(position).phone);
                }
            }
        }

    }
}
