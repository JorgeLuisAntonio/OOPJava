package mesproduits;

import magasin.iArticle ;

public class Produit implements iArticle{

    private int reference ;
    private String nom;
    private double prix ;

    public Produit(int ref, String nom, double prix) {
        this.reference = ref;
        this.nom = nom;
        this.prix = prix;
    }

    @Override
    public int reference() {
        return reference;
    }

    @Override
    public String nom() {
        return nom;
    }

    @Override
    public double prix() {
        return prix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass() != o.getClass()) return false ;
        Produit article = (Produit) o;
        return this.reference() == article.reference();
    }
}
