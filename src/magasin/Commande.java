package magasin;

import magasin.exceptions.ArticleHorsPanierException;
import magasin.exceptions.QuantiteNegativeException;
import magasin.exceptions.QuantiteNegativeOuNulleException;
import magasin.exceptions.QuantiteSuppPanierException;

import java.util.*;

/**
 * défini une commande, c'est-à-dire des articles associés à leur quantité commandée
 */

public class Commande implements Comparable<Commande> {

 Map<iArticle,Integer> achats ;
    // TODO

    public Commande() {
        achats=new HashMap<>();
        // TODO
    }

    /**
     * indique si la commande est vide
     *
     * @return
     */
    public boolean estVide() {
        // TODO
        return achats.isEmpty();
    }

    /**
     * ajoute la quantité indiquée de l'article considéré  à la commande
     *
     * @param quantite        quantité à ajouter
     * @param articleCommande article à considérer
     * @throws QuantiteNegativeOuNulleException si la quantité indiquée est négative ou nulle
     */
    public void ajout(int quantite, iArticle articleCommande) throws QuantiteNegativeOuNulleException {
        if(quantite<=0) throw new  QuantiteNegativeOuNulleException();
        achats.put(articleCommande,quantite);
    }

    /**
     * retire de la commande la quantité indiquée de l'article considéré
     *
     * @param quantite        quantité à retirer
     * @param articleCommande article à considérer
     * @throws QuantiteNegativeOuNulleException si la quantité indiquée est négative ou nulle
     * @throws QuantiteSuppPanierException      si la quantité indiquée est supp à celle dans da commande
     * @throws ArticleHorsPanierException       si l'article considéré n'est pas dans la commande
     */
    public void retirer(int quantite, iArticle articleCommande) throws QuantiteNegativeOuNulleException, QuantiteSuppPanierException, ArticleHorsPanierException {

        if(quantite<=0)throw new QuantiteNegativeOuNulleException();
        if(quantite>achats.size()) throw new QuantiteSuppPanierException();
      if(!(achats.containsKey(articleCommande))) throw new ArticleHorsPanierException();
        achats.put(articleCommande, achats.get(articleCommande) - quantite );

    }

    /**
     * donne une liste de tous les articles présent dans la commande
     * (trié par nom d'article)
     *
     * @return
     */
    public List<iArticle> listerArticlesParNom() {

        List<iArticle> articles = new ArrayList<>();
        Collections.sort(articles,iArticle.COMPARATEUR_NOM);
        return articles;
    }

    /**
     * donne une liste de tous les articles présent dans la commande
     * (trié par reference)
     *
     * @return
     */
    public List<iArticle> listerArticlesParReference() {
        List<iArticle> articles = new ArrayList<>();
        Collections.sort(articles,iArticle.COMPARATEUR_REFERENCE);
        return articles;
    }

    /**
     * donne une liste de tous les couples (articleCommande, quantiteCommandee)
     * présent dans la commande
     *
     * @return
     */
    public List<Map.Entry<iArticle, Integer>> listerCommande() {

     return new ArrayList<>(achats.entrySet());
    }


    /**
     * donne la quantité commandée de l'article considéré
     *
     * @param article l'article à considérer
     * @return la quantité commmandée
     */
    public int quantite(iArticle article) {

        return  achats.get(article);
    }

    /**
     * donne le montant actuel de la commande
     *
     * @return
     */
    public double montant() {

        List<Map.Entry<iArticle, Integer>>  list= listerCommande();
        double mont=0.0;
      for (int i=0; i< list.size();i++){
        iArticle article=list.get(i).getKey();
        int quantite= list.get(i).getValue();
        mont+= article.prix()* quantite;
      }
        return mont;
    }

    @Override
    public int compareTo(Commande o) {
       // this>o=1   this<0=-1 this=o=0
        return (int) (this.montant()-o.montant());


    }
}
