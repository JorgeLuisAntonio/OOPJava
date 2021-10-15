package mesproduits;

import java.util.Arrays;

public class Couleur extends Produit {

    String codeHex ;
    Number[] codeHSL ;

    public Couleur(int ref, String nom, double prix, String codeHex, Number[] codeHSL) {
        super(ref, nom, prix);
        this.codeHex = codeHex ;
        this.codeHSL = codeHSL ;
    }

    public String getCodeHex() {
        return codeHex;
    }

    public String getCodeRGB() {
        return Arrays.toString((new long[]{composanteRouge(), composanteVert(), composanteBleu()}));
    }

    public long composanteRouge() {
        return Long.parseLong(codeHex.replaceFirst("#","").substring(0,2), 16) ;
    }

    public long composanteVert() {
        return Long.parseLong(codeHex.replaceFirst("#","").substring(2,4), 16);
    }

    public long composanteBleu() {
        return Long.parseLong(codeHex.replaceFirst("#","").substring(4), 16);
    }

    public boolean estSature() {
        return (double)codeHSL[1] > 0.80 ;
    }

    public boolean estSombre() {
        return (double)codeHSL[2] < 0.30 ;
    }

    @Override
    public String toString() {
        return "Couleur n°"+reference()+" : "+nom()+" ("+prix()+" €)" +"\n"+
                "\tCodes : RGB " + getCodeRGB() + " | Hex " + getCodeHex();
    }

}


