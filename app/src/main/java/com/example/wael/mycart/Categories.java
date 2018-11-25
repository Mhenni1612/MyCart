package com.example.wael.mycart;

import java.util.ArrayList;
import java.util.List;

public class Categories {

    private String id;
    private String nom;
    private List<String> sousCategorie;

    public Categories(){
    }

    public String getNom(){
        return this.nom;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSousCategorie(ArrayList<String> sousCategorie) {
        this.sousCategorie = sousCategorie;
    }

    public List<String> getSousCategorie() {
        return this.sousCategorie;
    }

    public String getId() {
        return this.id;
    }

    //@androidx.annotation.NonNull
    @Override
    public String toString() {
        return this.id+" / "+this.nom+" / "+this.sousCategorie.get(2);
    }
}
