package magasin;

import magasin.exceptions.*;

import java.util.List;
import java.util.Map;

public class Magasin implements iStock, iClientele, iPanier {

    // TODO

    public Magasin() {
        // TODO
    }


    // iStock

    @Override
    public void referencerAuStock(iArticle nouvelArticle, int quantiteNouvelle)
            throws ArticleDejaEnStockException, QuantiteNegativeException {
        // TODO
    }


    @Override
    public void reapprovisionnerStock(iArticle articleMaj, int quantiteAjoutee)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException {
        // TODO
    }

    @Override
    public int consulterQuantiteEnStock(iArticle articleRecherche) throws ArticleHorsStockException {
        // TODO
        return -1;
    }

    @Override
    public void retirerDuStock(int quantiteRetiree, iArticle articleMaj)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException, QuantiteEnStockInsuffisanteException {
        // TODO
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
        // TODO
    }

    @Override
    public void supprimerDuPanier(iClient client, int quantite, iArticle article)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            QuantiteSuppPanierException, ArticleHorsPanierException,
            ArticleHorsStockException {
        // TODO
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