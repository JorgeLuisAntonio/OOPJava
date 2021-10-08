package magasin;

import magasin.exceptions.*;

import java.util.List;
import java.util.Map;

/**
 * défini les opérations nécessaires à la gestion du stock d'un magasin
 */
public interface iStock {

    /**
     * ajoute un nouvel article dans le stock
     *
     * @param nouvelArticle    l'article à référecner
     * @param quantiteNouvelle la quantite de l'article à ajouter. Cette quantite peut être nulle,
     *                         ce qui correspond à  uniquement referencer l'article
     * @throws ArticleDejaEnStockException si l'article est déjà référencé
     * @throws QuantiteNegativeException   si la quantité indiquée est négative
     */
    void referencerAuStock(iArticle nouvelArticle, int quantiteNouvelle)
            throws ArticleDejaEnStockException, QuantiteNegativeException;

    /**
     * reapprovisionner le stock
     *
     * @param articleMaj      l'article à reapprovisionner
     * @param quantiteAjoutee la quantite de article à ajouter
     * @throws ArticleHorsStockException        si l'article à réapprovisionner n'est pas référencé
     * @throws QuantiteNegativeOuNulleException si la quantité à réapprovisionner est négative ou nulle
     */
    void reapprovisionnerStock(iArticle articleMaj, int quantiteAjoutee)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException;

    /**
     * donne la quantité actuellemenent en stock de l'article considéré
     *
     * @param articleRecherche l'article à considérer
     * @return la quantité en stock de l'article recherché
     * @throws ArticleHorsStockException si l'article considéré n'est pas référencé dans le stock
     */
    int consulterQuantiteEnStock(iArticle articleRecherche)
            throws ArticleHorsStockException;

    /**
     * retire du stock une certaine quantité de l'article considéré
     *
     * @param quantiteRetiree la quantité à retirer du stock
     * @param articleMaj      l'article à considérer
     * @throws ArticleHorsStockException            si l'article considéré n'est pas référencé dans le stock
     * @throws QuantiteNegativeOuNulleException     si la quantité à retirer est négative ou nulle
     * @throws QuantiteEnStockInsuffisanteException si la quantité actuellement en stock ne permet pas
     *                                              le retrait de la quantité à retirer
     */
    void retirerDuStock(int quantiteRetiree, iArticle articleMaj)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException, QuantiteEnStockInsuffisanteException;


    /**
     * donne la liste de tous les articles actuellement en stock (triés par nom)
     *
     * @return
     */
    List<iArticle> listerArticlesEnStockParNom();

    /**
     * donne la liste de tous les articles actuellement en stock (triés par référence)
     *
     * @return
     */
    List<iArticle> listerArticlesEnStockParReference();

    /**
     * donne la liste de couples (articles, quantité) correspondant au stock actuel (triés par nom d'articles)
     *
     * @return
     */
    List<Map.Entry<iArticle, Integer>> listerStock();

}
