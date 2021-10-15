package monapplication;

import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import magasin.*;
import magasin.exceptions.* ;
import mesproduits.*;

import java.lang.reflect.Array;
import java.util.*;

public class Application {

    // méthodes de configuration initiale

    /**
     * Initialise le magasin de l'appli avec diverses produits
     */
    private static Magasin initStocks(Magasin magasin) throws ArticleDejaEnStockException, QuantiteNegativeException {

        // Couleurs
        for (int i = 0; i< ((int) (Math.random() * 5)+1); i++) {

            JSONObject resp = Unirest.get("https://random-data-api.com/api/color/random_color")
                    .asJson()
                    .getBody()
                    .getObject() ;
            Couleur c = new Couleur(
                    resp.getInt("id"),
                    resp.getString("color_name"),
                    Integer.parseInt(resp.getString("uid").substring(0,4),16) / 10.0,
                    resp.getString("hex_value"),
                    new Number[] {
                            resp.getJSONArray("hsl_value").getInt(0),
                            resp.getJSONArray("hsl_value").getInt(1),
                            resp.getJSONArray("hsl_value").getInt(2),
                    }
            ) ;
            magasin.referencerAuStock(c, (int) (Math.random() * 25)+1);
        }

        // Cafés
        for (int i = 0; i<((int) (Math.random() * 5)+1); i++) {

            JSONObject resp = Unirest.get("https://random-data-api.com/api/coffee/random_coffee")
                    .asJson()
                    .getBody()
                    .getObject() ;
            Cafe c = new Cafe(
                    resp.getInt("id"),
                    resp.getString("blend_name"),
                    Integer.parseInt(resp.getString("uid").substring(0,3),16) / 100.0,
                    resp.getString("origin"),
                    Arrays.asList( resp.getString("notes").split(", "))
            ) ;
            magasin.referencerAuStock(c, (int) (Math.random() * 25)+1);
        }

        // Bières
        for (int i = 0; i<((int) (Math.random() * 5)+1); i++) {

            JSONObject resp = Unirest.get("https://random-data-api.com/api/beer/random_beer")
                    .asJson()
                    .getBody()
                    .getObject() ;
            Biere b = new Biere(
                    resp.getInt("id"),
                    resp.getString("name"),
                    Integer.parseInt(resp.getString("uid").substring(0,3),16) / 100.0,
                    Integer.parseInt( resp.getString("ibu").split(" ")[0]) ,
                    Double.parseDouble( resp.getString("alcohol").replace("%","") )
            ) ;
            magasin.referencerAuStock(b, (int) (Math.random() * 25)+1);
        }

        return magasin ;
    }

    private static Magasin initClients(Magasin magasin) throws ClientDejaEnregistreException {

        magasin.enregistrerNouveauClient(new Client("_User","_Lambda"));

        for (int i = 0; i<((int) (Math.random() * 5)+1); i++) {

            JSONObject resp = Unirest.get("https://random-data-api.com/api/users/random_user")
                    .asJson()
                    .getBody()
                    .getObject() ;

            magasin.enregistrerNouveauClient( new Client(
                    resp.getString("first_name"),
                    resp.getString("last_name")
            ));
        }

        return magasin ;
    }

    // commande d'aide

    public static String aide(String s) {

        return s ;
    }

    // méthodes d'entrées utilisateur

    private static String stocks(Magasin magasin) {
        String res = "";
        List<Map.Entry<iArticle, Integer>> stocks = magasin.listerStock() ;

        for (Map.Entry<iArticle, Integer> couple : stocks) {
            iArticle article = couple.getKey() ;
            int quantite = couple.getValue() ;

            res += "> (ID_" + article.reference() +") "+ article.getClass().getSimpleName()+" | "+article.nom()+" | "+quantite+" en stock"
                    + "\n";
        }
        return res ;
    }

    private static String details(Magasin magasin, String s) {

        List<iArticle> articles = magasin.listerArticlesEnStockParReference() ;
        for (iArticle art : articles) {
            if (art.nom().equalsIgnoreCase(s)) return "> "+art.toString()+"\n" ;
        }
        return "Article inconnu. Veuillez préciser un nom valide\n" ;
    }

    private static String combien(Magasin magasin, String s) throws ArticleHorsStockException {
        List<iArticle> articles = magasin.listerArticlesEnStockParNom() ;
        for (iArticle art : articles) {
            if (art.nom().equalsIgnoreCase(s))
                return "> "+art.nom() + " : " + magasin.consulterQuantiteEnStock(art)+" en stock\n" ;
        }
        return "Article inconnu. Veuillez préciser un nom valide\n" ;
    }

    private static String prix(Magasin magasin,  String s) {
        List<iArticle> articles = magasin.listerArticlesEnStockParNom() ;
        for (iArticle art : articles) {
            if (art.nom().equalsIgnoreCase(s))
                return "> "+art.nom() + " : " +art.prix()+" €\n" ;
        }
        return "Article inconnu. Veuillez préciser un nom valide\n" ;
    }

    private static String panier(Magasin magasin) throws ClientInconnuException {

        List<iClient> clients = magasin.listerLesClientsParId() ;
        iClient user = clients.get(0);
        List<Map.Entry<iArticle, Integer>> couples = magasin.consulterPanier(user).listerCommande() ;
        double montant = magasin.consulterMontantPanier(user) ;

        String res = "" ;
        for (Map.Entry<iArticle, Integer> couple : couples) {
            iArticle article = couple.getKey() ;
            int quantite = couple.getValue() ;

            res += "> (ID_" + article.reference() +") "+ article.getClass().getSimpleName()+" | "+article.nom()+" | "+quantite+" dans le panier"
                    + "\n";
        }
        return (res.trim().equals("") ? "Votre panier est vide\n" : res+"\t montant total : " + montant + " €\n") ;
    }

    private static String acheter(Magasin magasin, String s)
            throws ClientInconnuException,
                QuantiteEnStockInsuffisanteException,
                QuantiteNegativeOuNulleException,
                ArticleHorsStockException {

        List<iClient> clients = magasin.listerLesClientsParId() ;
        iClient user = clients.get(0);

        String[] args = s.split(",") ;
        if (args.length != 2) return "Veuillez préciser un nom d'article suivi d'une quantité, séparés par une virgule ( , )\n" ;

        int quantite = Integer.parseInt(args[1].trim()) ;

        String nomArtDemande = args[0].trim() ;
        iArticle artDemande = null;
        List<iArticle> stocks = magasin.listerArticlesEnStockParNom() ;
        for (iArticle art : stocks) {
            if (art.nom().equalsIgnoreCase(nomArtDemande)) artDemande = art ;
        }
        if (artDemande == null) return "Article inconnu. Veuillez préciser un nom valide\n";
        magasin.ajouterAuPanier(user,artDemande, quantite);

        return "> "+artDemande.nom()+" ajouté au panier en "+quantite+" exemplaires\n" ;
    }

    private static String remettre(Magasin magasin, String s)
            throws
            ClientInconnuException, QuantiteSuppPanierException, ArticleHorsPanierException,
            QuantiteNegativeOuNulleException, ArticleHorsStockException {

        List<iClient> clients = magasin.listerLesClientsParId() ;
        iClient user = clients.get(0);

        String[] args = s.split(",") ;
        if (args.length != 2) return "Veuillez préciser un nom d'article suivi d'une quantité, séparés par une virgule ( , )\n" ;

        int quantite = Integer.parseInt(args[1].trim()) ;

        String nomArtDemande = args[0].trim() ;
        iArticle artDemande = null;
        List<iArticle> stocks = magasin.listerArticlesEnStockParNom() ;
        for (iArticle art : stocks) {
            if (art.nom().equalsIgnoreCase(nomArtDemande)) artDemande = art ;
        }
        if (artDemande == null) return "Article inconnu. Veuillez préciser un nom valide\n";
        magasin.supprimerDuPanier(user, quantite, artDemande);

        return "> "+quantite+" exemplaire(s) de "+artDemande.nom()+" retiré(s) du panier\n" ;
    }

    private static String encaisser(Magasin magasin) throws ClientInconnuException {
        List<iClient> clients = magasin.listerLesClientsParId() ;
        iClient user = clients.get(0);
        double montant = magasin.consulterMontantPanier(user) ;
        if (montant == 0.0) return "Votre panier est vide, remplissez-le d'abord\n" ;
        magasin.terminerLaCommande(user);
        return "Merci pour votre achat d'un montant de "+montant+" €\n" ;
    }

    private static String historique(Magasin magasin) throws ClientInconnuException {
        List<iClient> clients = magasin.listerLesClientsParId() ;
        iClient user = clients.get(0);
        List<Commande> commandesPassees = magasin.listerCommandesTerminees(user) ;
        String res = "" ;
        int num = 0 ;
        for (Commande cmd : commandesPassees) {
            num++ ;
            res += "> Commande n°"+num+" | "+cmd.listerCommande().size()+" article(s) unique(s) pour un montant de "+cmd.montant()+" €\n" ;
        }

        return (res.trim().equals("") ? "Vous n'avez encore fait aucune commande\n" : res) ;
    }

    /**
     *    ___  ____________ _     _____ _____   ___ _____ _____ _____ _   _
     *   / _ \ | ___ \ ___ \ |   |_   _/  __ \ / _ \_   _|_   _|  _  | \ | |
     *  / /_\ \| |_/ / |_/ / |     | | | /  \// /_\ \| |   | | | | | |  \| |
     *  |  _  ||  __/|  __/| |     | | | |    |  _  || |   | | | | | | . ` |
     *  | | | || |   | |   | |_____| |_| \__/\| | | || |  _| |_\ \_/ / |\  |
     *  \_| |_/\_|   \_|   \_____/\___/ \____/\_| |_/\_/  \___/ \___/\_| \_/
     */

    public static void main(String[] args) throws Exception {

        Magasin magasinAppli = initStocks(new Magasin()) ;
        initClients(magasinAppli) ;

        Scanner scanner = new Scanner(System.in) ;

        System.out.println("Bienvenue dans notre magasin ! Profitez ! Faites un tour !\n");

        System.out.println("Commandes disponibles\n" +
                "stocks\tdetails\tcombien\tprix\tpanier\tacheter\tremettre\tencaisser\tprofils\thistorique\taide\tquitter");
        System.out.println("Taper 'aide <nom de commande>' pour avoir des détails sur une commande précise\n");

        boolean appli = true ;

        while (appli) {

            String input = (scanner.nextLine()).toLowerCase() ;
            if (input.startsWith("quitter")) appli = false ;

            if (input.startsWith("aide"))
                System.out.println( aide( input.replaceFirst("aide ","")) );

            try {
                if (input.startsWith("stocks"))
                    System.out.println(stocks(magasinAppli));

                if (input.startsWith("details"))
                    System.out.println(details(magasinAppli, input.replaceFirst("details ", "")));

                if (input.startsWith("combien"))
                    System.out.println(combien(magasinAppli, input.replaceFirst("combien ", "")));

                if (input.startsWith("prix"))
                    System.out.println(prix(magasinAppli, input.replaceFirst("prix ", "")));

                if (input.startsWith("panier"))
                    System.out.println(panier(magasinAppli));

                if (input.startsWith("acheter"))
                    System.out.println(acheter(magasinAppli, input.replaceFirst("acheter ", "")));

                if (input.startsWith("remettre"))
                    System.out.println(remettre(magasinAppli, input.replaceFirst("remettre ", "")));

                if (input.startsWith("encaisser"))
                    System.out.println(encaisser(magasinAppli));

                if (input.startsWith("historique"))
                    System.out.println(historique(magasinAppli));

            } catch (Exception ex) {

                System.out.println("\nErreur, redémarrage de l'appli nécessaire");
                System.out.println(ex.getClass().getSimpleName()+"\n");
                appli = false ;
            }

        }

        scanner.close();
    }
}
