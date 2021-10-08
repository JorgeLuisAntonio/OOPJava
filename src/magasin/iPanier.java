package magasin;

import magasin.exceptions.*;

import java.util.List;

/**
 * défini défini les opérations nécessaires à la gestion des commandes des clients.
 * La notion de "panier" correspond à une commande en cours, pas encore validée/terminée.
 */
public interface iPanier {

    /**
     * ajoute au "panier" du client considéré, l'article considéré dans la quantité indiquée.
     * Le stock doit être modifié en conséquence.
     *
     * @param client   le client à considérer
     * @param article  l'article à considérer
     * @param quantite la quantité à ajouter
     * @throws ClientInconnuException               si le client n'est pas référencer par le magasin
     * @throws ArticleHorsStockException            si l'article n'est pas référencer dans le stock du magasin
     * @throws QuantiteEnStockInsuffisanteException si la quantité en stock en permet pas de satisfaire la demande
     * @throws QuantiteNegativeOuNulleException     si la quantité à ajouter est négative ou nulle
     */
    void ajouterAuPanier(iClient client, iArticle article, int quantite)
            throws ClientInconnuException,
            ArticleHorsStockException, QuantiteEnStockInsuffisanteException,
            QuantiteNegativeOuNulleException;

    /**
     * supprime du panier du client considéré, la quantité indiquée de l'article considéré.
     * Le stock doit être modifié en conséquence.
     * NB :si la quantité dans le panier tombe à 0, alors l'article doit être automatiquement supprimé du panier
     *
     * @param client   le client à considérer
     * @param quantite la quantité à supprimer
     * @param article  l'article à considérer
     * @throws ClientInconnuException           si le client n'est pas référencer par le magasin
     * @throws QuantiteNegativeOuNulleException si la quantité à supprimer est négative ou nulle
     * @throws QuantiteSuppPanierException      si la quantité à supprimer du panier est supérieure
     *                                          à celle actuellement dans le panier
     * @throws ArticleHorsPanierException       si l'article considéré n'est pas actuellement dans le panier
     */
    void supprimerDuPanier(iClient client, int quantite, iArticle article)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException, QuantiteSuppPanierException, ArticleHorsPanierException, ArticleHorsStockException;

    /**
     * donne le panier actuel (=la commande en cours) du client considéré
     *
     * @param client le client à considérer
     * @return la commande en cours du client considéré
     * @throws ClientInconnuException si le client n'est pas référencer par le magasin
     */
    Commande consulterPanier(iClient client) throws ClientInconnuException;

    /**
     * donne le montant total du panier du client
     *
     * @param client le client à considérer
     * @return le montant total du panier du client
     * @throws ClientInconnuException si le client n'est pas référencer par le magasin
     */
    double consulterMontantPanier(iClient client) throws ClientInconnuException;

    /**
     * vide totalement le panier du client considéré.
     * Le stock doit être modifié en conséquence.
     *
     * @param client le client à considérer
     * @throws ClientInconnuException si le client n'est pas référencer par le magasin
     */
    void viderPanier(iClient client) throws ClientInconnuException;

    /**
     * termine la commande en cours. Cela clos le panier.
     * La commande est historisée et un nouveau panier vide est prêt pour le client.
     *
     * @param client le client à considérer
     * @throws ClientInconnuException si le client n'est pas référencer par le magasin
     */
    void terminerLaCommande(iClient client) throws ClientInconnuException;

    /**
     * liste l'ensemble des commandes terminées d'un client
     *
     * @param client le client à considérer
     * @return la liste des commandes du client considéré
     * @throws ClientInconnuException si le client n'est pas référencer par le magasin
     */
    List<Commande> listerCommandesTerminees(iClient client)
            throws ClientInconnuException;

    /**
     * donne le montant total des commandes terminées d'un client
     *
     * @param client le client à considérer
     * @return le montant total des commandes du client
     * @throws ClientInconnuException si le client n'est pas référencer par le magasin
     */
    double consulterMontantTotalCommandes(iClient client) throws ClientInconnuException;
}
