package mesproduits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cafe extends Produit {

    private String origine ;
    private List<String> blend_notes ;

    public Cafe(int ref, String nom, double prix, String origin, List<String> notes) {
        super(ref, nom, prix);
        origine = origin ;
        blend_notes = notes ;
    }

    public String origine() {
        return origine;
    }

    public List<String> notesBlend() {
        return blend_notes;
    }

    public List<String> attributsEnCommun(Cafe c) {
        List<String> commun = new ArrayList<>(c.blend_notes) ;
        commun.retainAll(blend_notes) ;
        return commun ;
    }

    @Override
    public String toString() {
        return "Café Blend n°"+reference()+" : "+nom()+" ("+prix()+" €)" +"\n\t"+
                "Origine : "+origine() +"\n\t"+
                "Tags : " + notesBlend();
    }

}
