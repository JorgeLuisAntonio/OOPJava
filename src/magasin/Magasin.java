package magasin;

import magasin.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Magasin implements iStock, iClientele, iPanier {

    // TODO
    Map<iArticle, Integer> stocksActuels ;

    public Magasin() {
        stocksActuels = new HashMap<>() ;
    }


    // iStock

    @Override
    public void referencerAuStock(iArticle nouvelArticle, int quantiteNouvelle)
            throws ArticleDejaEnStockException, QuantiteNegativeException {

        if (quantiteNouvelle < 0) throw new QuantiteNegativeException() ;
        if (stocksActuels.containsKey(nouvelArticle)) throw new ArticleDejaEnStockException() ;

        stocksActuels.put(nouvelArticle,quantiteNouvelle) ;
    }


    @Override
    public void reapprovisionnerStock(iArticle articleMaj, int quantiteAjoutee)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException {

        if (quantiteAjoutee <= 0) throw new QuantiteNegativeOuNulleException() ;
        if (! stocksActuels.containsKey(articleMaj)) throw new ArticleHorsStockException() ;

        stocksActuels.put(articleMaj, stocksActuels.get(articleMaj) + quantiteAjoutee );
    }

    @Override
    public int consulterQuantiteEnStock(iArticle articleRecherche) throws ArticleHorsStockException {
         if (! stocksActuels.containsKey(articleRecherche)) throw new ArticleHorsStockException() ;
        return stocksActuels.get(articleRecherche);
    }

    @Override
    public void retirerDuStock(int quantiteRetiree, iArticle articleMaj)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException, QuantiteEnStockInsuffisanteException {

        if (quantiteRetiree < 0) throw new QuantiteNegativeOuNulleException() ;
        if (stocksActuels.containsKey(articleMaj)) throw new ArticleHorsStockException() ;

        if (stocksActuels.get(articleMaj) < quantiteRetiree) throw new QuantiteEnStockInsuffisanteException() ;
        stocksActuels.put(articleMaj, stocksActuels.get(articleMaj) - quantiteRetiree );
    }


    @Override
    public List<iArticle> listerArticlesEnStockParNom() {
        // TODO
        return null;
    }

    @Override
    public List<iArticle> listerArticlesEnStockParReference() {
        // TODO
        return null;
    }

    @Override
    public List<Map.Entry<iArticle, Integer>> listerStock() {
        // TODO
        return null;
    }

    // iClientele


    @Override
    public void enregistrerNouveauClient(iClient nouveauClient) throws ClientDejaEnregistreException {
        // TODO
    }

    @Override
    public List<iClient> listerLesClientsParId() {
        // TODO
        return null;
    }


    // iPanier

    @Override
    public Commande consulterPanier(iClient client) throws ClientInconnuException {
        // TODO
        return null;
    }

    @Override
    public void ajouterAuPanier(iClient client, iArticle article, int quantite)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            ArticleHorsStockException, QuantiteEnStockInsuffisanteException {


        if (quantite <= 0) throw new QuantiteNegativeOuNulleException() ;
    }

    @Override
    public void supprimerDuPanier(iClient client, int quantite, iArticle article)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            QuantiteSuppPanierException, ArticleHorsPanierException,
            ArticleHorsStockException {


        if (quantite <= 0) throw new QuantiteNegativeOuNulleException() ;
    }

    @Override
    public double consulterMontantPanier(iClient client) throws ClientInconnuException {
        // TODO
        return -1.0;
    }

    @Override
    public void viderPanier(iClient client) throws ClientInconnuException {
        // TODO
    }

    @Override
    public void terminerLaCommande(iClient client) throws ClientInconnuException {
        // TODO
    }

    @Override
    public List<Commande> listerCommandesTerminees(iClient client) throws ClientInconnuException {
        // TODO
        return null;
    }

    @Override
    public double consulterMontantTotalCommandes(iClient client) throws ClientInconnuException {
        // TODO
        return -1.0;
    }


}