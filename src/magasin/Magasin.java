package magasin;

import magasin.exceptions.*;

import java.util.*;

public class Magasin implements iStock, iClientele, iPanier {

    // TODO
    Map<iArticle, Integer> stocksActuels ;
    Set<iClient> clients ;

    public Magasin() {
        stocksActuels = new HashMap<>() ;
        clients = new HashSet<>() ;
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
        List<iArticle> articles = new ArrayList<>(stocksActuels.keySet()) ;
        Collections.sort(articles, iArticle.COMPARATEUR_NOM);
        return articles;
    }

    @Override
    public List<iArticle> listerArticlesEnStockParReference() {
        List<iArticle> articles = new ArrayList<>(stocksActuels.keySet()) ;
        Collections.sort(articles, iArticle.COMPARATEUR_REFERENCE);
        return articles;
    }

    @Override
    public List<Map.Entry<iArticle, Integer>> listerStock() {
        return new ArrayList<>(stocksActuels.entrySet());
    }

    // iClientele


    @Override
    public void enregistrerNouveauClient(iClient nouveauClient) throws ClientDejaEnregistreException {
        boolean ajout = clients.add(nouveauClient) ;
        if (!ajout) throw new ClientDejaEnregistreException() ;
    }

    @Override
    public List<iClient> listerLesClientsParId() {
        List<iClient> listeClients = new ArrayList<>(clients) ;
        Collections.sort(listeClients, iClient.COMPARATEUR_ID);
        return listeClients;
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