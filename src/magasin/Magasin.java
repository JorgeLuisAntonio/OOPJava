package magasin;

import magasin.exceptions.*;

import java.util.*;

public class Magasin implements iStock, iClientele, iPanier {

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
        commandesFinies.put(nouveauClient, new ArrayList<>()) ;
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

        Commande cmd = paniers.get(client) ;
        if (! clients.contains(client) || cmd == null) throw new ClientInconnuException() ;

        return cmd ;
    }

    @Override
    public void ajouterAuPanier(iClient client, iArticle article, int quantite)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            ArticleHorsStockException, QuantiteEnStockInsuffisanteException {

        Commande cmd = paniers.get(client) ;
        if (! clients.contains(client) || cmd == null) throw new ClientInconnuException() ;

        cmd.ajout(quantite,article);
        retirerDuStock(quantite,article);
    }

    @Override
    public void supprimerDuPanier(iClient client, int quantite, iArticle article)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            QuantiteSuppPanierException, ArticleHorsPanierException,
            ArticleHorsStockException {

        Commande cmd = paniers.get(client) ;
        if (! clients.contains(client) || cmd == null) throw new ClientInconnuException() ;

        cmd.retirer(quantite,article);
        reapprovisionnerStock(article,quantite);
    }

    @Override
    public double consulterMontantPanier(iClient client) throws ClientInconnuException {

        Commande cmd = paniers.get(client) ;
        if (! clients.contains(client) || cmd == null) throw new ClientInconnuException() ;
        return cmd.montant() ;
    }

    @Override
    public void viderPanier(iClient client) throws ClientInconnuException {

        Commande cmd = paniers.get(client) ;
        if (! clients.contains(client) || cmd == null) throw new ClientInconnuException() ;

        List<Map.Entry<iArticle, Integer>> articles = cmd.listerCommande() ;
        for (Map.Entry<iArticle, Integer> coupleArtQuantite: articles) {

            iArticle article = coupleArtQuantite.getKey() ;
            int quantite = coupleArtQuantite.getValue() ;

            try {
                reapprovisionnerStock(article,quantite);
            } catch (ArticleHorsStockException | QuantiteNegativeOuNulleException e) {
                e.printStackTrace();
            }

        }
        paniers.replace(client, new Commande()) ;
    }

    @Override
    public void terminerLaCommande(iClient client) throws ClientInconnuException {

        Commande cmd = paniers.get(client) ;
        if (! clients.contains(client) || cmd == null) throw new ClientInconnuException() ;

        List<Commande> commandesDuClient = commandesFinies.get(client) ;
        commandesDuClient.add(cmd) ;

        paniers.replace(client, new Commande()) ;
    }

    @Override
    public List<Commande> listerCommandesTerminees(iClient client) throws ClientInconnuException {

        Commande cmd = paniers.get(client) ;
        if (! clients.contains(client) || cmd == null) throw new ClientInconnuException() ;

        return commandesFinies.get(client);
    }

    @Override
    public double consulterMontantTotalCommandes(iClient client) throws ClientInconnuException {

        List<Commande> commandesDuClient = listerCommandesTerminees(client) ;
        if (! clients.contains(client) || commandesDuClient == null) throw new ClientInconnuException() ;

        double montantTotal = 0.0 ;
        for (Commande cmd : commandesDuClient) {
            montantTotal += cmd.montant() ;
        }
        return montantTotal;
    }


}