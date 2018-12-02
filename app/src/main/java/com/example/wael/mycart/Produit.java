package com.example.wael.mycart;


import android.net.Uri;

public class Produit {
    private String id;
    private String marque;
    private String nom;
    private String taille;
    private String gout;
    private String prix;
    private String image;

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getSousCategorie() {
        return sousCategorie;
    }

    public void setSousCategorie(String sousCategorie) {
        this.sousCategorie = sousCategorie;
    }

    private String categorie;
    private String sousCategorie;

    public Produit(String marque, String nom, String taille, String gout, String prix, String image) {
        this.marque = marque;
        this.nom = nom;
        this.taille = taille;
        this.gout = gout;
        this.prix = prix;
        this.image = image;
    }

    public Produit() {}

    public String getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getGout() {
        return gout;
    }

    public void setGout(String gout) {
        this.gout = gout;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri convertImage(){
        return Uri.parse(this.image);
    }
}
