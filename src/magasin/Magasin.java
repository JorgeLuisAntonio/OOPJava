package magasin;

import magasin.exceptions.*;

import java.util.*;

public class Magasin implements iStock, iClientele, iPanier {

    // TODO
    Map<iArticle, Integer> stocksActuels ;
    Set<iClient> clients ;
    Map<iClient, Commande> paniers ;
    Map<iClient, List<Commande>> commandesFinies ;

    public Magasin() {
        stocksActuels = new HashMap<>() ;
        clients = new HashSet<>() ;
        paniers = new HashMap<>() ;
        commandesFinies = new HashMap<>() ;
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

        if (quantiteRetiree <= 0) throw new QuantiteNegativeOuNulleException() ;
        if (!stocksActuels.containsKey(articleMaj)) throw new ArticleHorsStockException() ;

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
        List<Map.Entry<iArticle, Integer>> stocks = new ArrayList<>(stocksActuels.entrySet());
        stocks.sort(Map.Entry.comparingByKey(iArticle.COMPARATEUR_NOM));
        return stocks ;
    }

    // iClientele


    @Override
    public void enregistrerNouveauClient(iClient nouveauClient) throws ClientDejaEnregistreException {
        boolean ajout = clients.add(nouveauClient) ;
        if (!ajout) throw new ClientDejaEnregistreException() ;
        paniers.put(nouveauClient, new Commande()) ;
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
        if (! clients.contains(client) || ! paniers.containsKey(client)) throw new ClientInconnuException() ;
        return paniers.get(client);
    }

    @Override
    public void ajouterAuPanier(iClient client, iArticle article, int quantite)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            ArticleHorsStockException, QuantiteEnStockInsuffisanteException {

        if (! clients.contains(client)) throw new ClientInconnuException() ;
        if (quantite <= 0) throw new QuantiteNegativeOuNulleException() ;

        if (! stocksActuels.containsKey(article)) throw new ArticleHorsStockException() ;
        int quantiteActuelle = stocksActuels.get(article) ;
        if (quantiteActuelle - quantite < 0) throw new QuantiteEnStockInsuffisanteException() ;

        Commande cmdActuelle = paniers.get(client) ;
        cmdActuelle.ajout(quantiteActuelle,article);

        retirerDuStock(quantite,article);
    }

    @Override
    public void supprimerDuPanier(iClient client, int quantite, iArticle article)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            QuantiteSuppPanierException, ArticleHorsPanierException,
            ArticleHorsStockException {


    }

    @Override
    public double consulterMontantPanier(iClient client) throws ClientInconnuException {
        return 0.0 ;
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