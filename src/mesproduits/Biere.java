package mesproduits;

public class Biere extends Produit{

    private int ibu ;
    private double alcool ;

    public Biere(int ref, String nom, double prix, int ibu, double alcool) {
        super(ref, nom, prix);
        this.ibu = ibu ;
        this.alcool = alcool ;
    }

    public int getIbu() {
        return ibu;
    }

    public double getAlcool() {
        return Math.round(alcool*100.0)/100.0;
    }



    @Override
    public String toString() {
        return "Biere n°"+reference()+" : "+nom()+" ("+prix()+" €)" + "\n\t"+
                "IBU "+getIbu()+" à "+getAlcool()+"% d'alcool"
        ;
    }

}
