package magasin;

import magasin.exceptions.ArticleHorsPanierException;
import magasin.exceptions.QuantiteNegativeOuNulleException;
import magasin.exceptions.QuantiteSuppPanierException;

import java.util.List;
import java.util.Map;

/**
 * défini une commande, c'est-à-dire des articles associés à leur quantité commandée
 */

public class Commande implements Comparable<Commande> {

    // TODO

    public Commande() {
        // TODO
    }

    /**
     * indique si la commande est vide
     *
     * @return
     */
    public boolean estVide() {
        // TODO
        return false;
    }

    /**
     * ajoute la quantité indiquée de l'article considéré  à la commande
     *
     * @param quantite        quantité à ajouter
     * @param articleCommande article à considérer
     * @throws QuantiteNegativeOuNulleException si la quantité indiquée est négative ou nulle
     */
    public void ajout(int quantite, iArticle articleCommande)
            throws QuantiteNegativeOuNulleException {
        // TODO
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
    public void retirer(int quantite, iArticle articleCommande)
            throws QuantiteNegativeOuNulleException,
            QuantiteSuppPanierException, ArticleHorsPanierException {
        // TODO
    }

    /**
     * donne une liste de tous les articles présent dans la commande
     * (trié par nom d'article)
     *
     * @return
     */
    public List<iArticle> listerArticlesParNom() {
        // TODO
        return null;
    }

    /**
     * donne une liste de tous les articles présent dans la commande
     * (trié par reference)
     *
     * @return
     */
    public List<iArticle> listerArticlesParReference() {
        // TODO
        return null;
    }

    /**
     * donne une liste de tous les couples (articleCommande, quantiteCommandee)
     * présent dans la commande
     *
     * @return
     */
    public List<Map.Entry<iArticle, Integer>> listerCommande() {
        // TODO
        return null;
    }


    /**
     * donne la quantité commandée de l'article considéré
     *
     * @param article l'article à considérer
     * @return la quantité commmandée
     */
    public int quantite(iArticle article) {
        // TODO
        return -1;
    }

    /**
     * donne le montant actuel de la commande
     *
     * @return
     */
    public double montant() {
        // TODO
        return -1.0;
    }

    @Override
    public int compareTo(Commande o) {
        // TODO
        return 0;
    }
}
