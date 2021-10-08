package magasin;

import magasin.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class Test5_MagasinRempliPanier {


    Magasin magasin;
    ClientTest darkVador;
    ArticleTest playmobil, lego, fusee, capsule;

    @BeforeEach
    public void init() throws ClientDejaEnregistreException,
            QuantiteNegativeException, ArticleDejaEnStockException {
        magasin = new Magasin();
        //
        darkVador = new ClientTest("Dark", "Vador");
        magasin.enregistrerNouveauClient(new ClientTest("Iron", "Man"));
        magasin.enregistrerNouveauClient(new ClientTest("Bat", "Man"));
        magasin.enregistrerNouveauClient(darkVador);
        magasin.enregistrerNouveauClient(new ClientTest("Aqua", "Man"));
        magasin.enregistrerNouveauClient(new ClientTest("", "Albator"));
        magasin.enregistrerNouveauClient(new ClientTest("M.", "Spock"));
        //
        //
        playmobil = new ArticleTest("Playmobil", 41.5);
        lego = new ArticleTest("Lego", 23.45);
        fusee = new ArticleTest("Reacteur Fusée", 100000);
        capsule = new ArticleTest("Capsule", 0.10);
        magasin.referencerAuStock(lego, 25);
        magasin.referencerAuStock(playmobil, 100);
        magasin.referencerAuStock(fusee, 0);
        magasin.referencerAuStock(capsule, 100);
    }

    @Test
    public void panierVide() throws ClientInconnuException {
        Commande cmd = magasin.consulterPanier(darkVador);
        assertTrue(cmd.listerCommande().isEmpty());
        assertTrue(cmd.estVide());
    }

    @Test
    public void panierVide_ex() {
        assertThrows(ClientInconnuException.class,
                () -> magasin.consulterPanier(new ClientTest("Totoro", "")));
    }


    @Test
    public void Ajout_ex1() {
        assertThrows(ClientInconnuException.class,
                () -> magasin.ajouterAuPanier(new ClientTest("Totoro", ""), playmobil, 10));
    }

    @Test
    public void Ajout_ex2() {
        assertThrows(ArticleHorsStockException.class,
                () -> magasin.ajouterAuPanier(darkVador, new ArticleTest("Totoro"), 10));
    }

    @Test
    public void Ajout_ex3() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.ajouterAuPanier(darkVador, playmobil, -10));
    }

    @Test
    public void Ajout_ex4() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.ajouterAuPanier(darkVador, playmobil, -10));
    }

    @Test
    public void Ajout_ex5() {
        assertThrows(QuantiteEnStockInsuffisanteException.class,
                () -> magasin.ajouterAuPanier(darkVador, playmobil, 200));
    }

    @Test
    public void ajout_ok()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        //
        Commande cmd = magasin.consulterPanier(darkVador);
        assertFalse(cmd.listerCommande().isEmpty());
        assertFalse(cmd.estVide());
        assertEquals(51, cmd.quantite(playmobil));
        //
        Map.Entry[] attenduCmd = {
                new AbstractMap.SimpleEntry<>(playmobil, 51),
        };
        assertIterableEquals(Arrays.asList(attenduCmd), cmd.listerCommande());
        //
        assertEquals(49, magasin.consulterQuantiteEnStock(playmobil));
        Map.Entry[] attenduStock = {
                new AbstractMap.SimpleEntry<>(capsule, 100),
                new AbstractMap.SimpleEntry<>(lego, 25),
                new AbstractMap.SimpleEntry<>(playmobil, 49),
                new AbstractMap.SimpleEntry<>(fusee, 0)
        };
        assertIterableEquals(Arrays.asList(attenduStock),
                magasin.listerStock());
    }

    @Test
    public void ajout_ok2()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        //
        Map.Entry[] attenduCmd = {
                new AbstractMap.SimpleEntry<>(lego, 10),
                new AbstractMap.SimpleEntry<>(playmobil, 51),
        };
        assertIterableEquals(Arrays.asList(attenduCmd), magasin.consulterPanier(darkVador).listerCommande());
        //
        Map.Entry[] attenduStock = {
                new AbstractMap.SimpleEntry<>(capsule, 100),
                new AbstractMap.SimpleEntry<>(lego, 15),
                new AbstractMap.SimpleEntry<>(playmobil, 49),
                new AbstractMap.SimpleEntry<>(fusee, 0)
        };
        assertIterableEquals(Arrays.asList(attenduStock),
                magasin.listerStock());
    }

    @Test
    public void ajout_ok3()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        Map.Entry[] attenduCmd = {
                new AbstractMap.SimpleEntry<>(lego, 10),
                new AbstractMap.SimpleEntry<>(playmobil, 61),
        };
        assertIterableEquals(Arrays.asList(attenduCmd),
                magasin.consulterPanier(darkVador).listerCommande());
        //
        Map.Entry[] attenduStock = {
                new AbstractMap.SimpleEntry<>(capsule, 100),
                new AbstractMap.SimpleEntry<>(lego, 15),
                new AbstractMap.SimpleEntry<>(playmobil, 39),
                new AbstractMap.SimpleEntry<>(fusee, 0)
        };
        assertIterableEquals(Arrays.asList(attenduStock),
                magasin.listerStock());
    }

    @Test
    public void supprimer_ex1() {
        assertThrows(ClientInconnuException.class,
                () -> magasin.supprimerDuPanier(new ClientTest("Totoro", ""), 10, playmobil));
    }

    @Test
    public void supprimer_ex2() {
        assertThrows(ArticleHorsPanierException.class,
                () -> magasin.supprimerDuPanier(darkVador, 10, playmobil));
    }

    @Test
    public void supprimer_ex3()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            QuantiteNegativeOuNulleException, ArticleHorsStockException {
        magasin.ajouterAuPanier(darkVador, lego, 10);
        assertThrows(ArticleHorsPanierException.class,
                () -> magasin.supprimerDuPanier(darkVador, 10, playmobil));
    }

    @Test
    public void supprimer_ex4()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            QuantiteNegativeOuNulleException, ArticleHorsStockException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.supprimerDuPanier(darkVador, -10, playmobil));
    }

    @Test
    public void supprimer_ex5()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            QuantiteNegativeOuNulleException, ArticleHorsStockException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        assertThrows(QuantiteSuppPanierException.class,
                () -> magasin.supprimerDuPanier(darkVador, 15, lego));
    }

    @Test
    public void supprimer_ok()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            QuantiteNegativeOuNulleException, ArticleHorsStockException,
            QuantiteSuppPanierException, ArticleHorsPanierException {
        assertEquals(25, magasin.consulterQuantiteEnStock(lego));
        //
        magasin.ajouterAuPanier(darkVador, lego, 10);
        assertEquals(15, magasin.consulterQuantiteEnStock(lego));
        //
        magasin.supprimerDuPanier(darkVador, 5, lego);
        assertEquals(20, magasin.consulterQuantiteEnStock(lego));
        //
        Map.Entry[] attenduCmd = {
                new AbstractMap.SimpleEntry<>(lego, 5)
        };
        assertIterableEquals(Arrays.asList(attenduCmd),
                magasin.consulterPanier(darkVador).listerCommande());
    }

    @Test
    public void supprimer_ok2()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            QuantiteNegativeOuNulleException, ArticleHorsStockException,
            QuantiteSuppPanierException, ArticleHorsPanierException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        magasin.supprimerDuPanier(darkVador, 1, playmobil);
        magasin.supprimerDuPanier(darkVador, 5, lego);
        magasin.supprimerDuPanier(darkVador, 10, playmobil);
        //
        Map.Entry[] attenduCmd = {
                new AbstractMap.SimpleEntry<>(lego, 5),
                new AbstractMap.SimpleEntry<>(playmobil, 50),
        };
        assertIterableEquals(Arrays.asList(attenduCmd),
                magasin.consulterPanier(darkVador).listerCommande());
        //
        Map.Entry[] attenduStock = {
                new AbstractMap.SimpleEntry<>(capsule, 100),
                new AbstractMap.SimpleEntry<>(lego, 20),
                new AbstractMap.SimpleEntry<>(playmobil, 50),
                new AbstractMap.SimpleEntry<>(fusee, 0)
        };
        assertIterableEquals(Arrays.asList(attenduStock),
                magasin.listerStock());
    }


    @Test
    public void montant_ex1() {
        assertThrows(ClientInconnuException.class,
                () -> magasin.consulterMontantPanier(new ClientTest("Totoro", "")));
    }

    @Test
    public void montant_ok()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        //
        assertEquals((41.5 * 51), magasin.consulterMontantPanier(darkVador));
    }

    @Test
    public void montant_ok2()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        assertEquals(((41.5 * (51 + 10)) + (23.45 * 10)), magasin.consulterMontantPanier(darkVador));
    }


    @Test
    public void vider_ex1() {
        assertThrows(ClientInconnuException.class,
                () -> magasin.viderPanier(new ClientTest("Totoro", "")));
    }

    @Test
    public void vider_ok()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        magasin.viderPanier(darkVador);
        //
        Commande cmd = magasin.consulterPanier(darkVador);
        assertTrue(cmd.listerCommande().isEmpty());
        assertTrue(cmd.estVide());
        //
        assertEquals(100, magasin.consulterQuantiteEnStock(playmobil));
        assertEquals(25, magasin.consulterQuantiteEnStock(lego));
    }

    @Test
    public void terminer_ex() {
        assertThrows(ClientInconnuException.class,
                () -> magasin.terminerLaCommande(new ClientTest("Totoro", "")));
    }

    @Test
    public void listerCmd_ex() {
        assertThrows(ClientInconnuException.class,
                () -> magasin.listerCommandesTerminees(new ClientTest("Totoro", "")));
    }

    @Test
    public void consultCmd_ex() {
        assertThrows(ClientInconnuException.class,
                () -> magasin.consulterMontantTotalCommandes(new ClientTest("Totoro", "")));
    }

    @Test
    public void terminer_ok()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        magasin.terminerLaCommande(darkVador);
        //
        Commande cmd = magasin.consulterPanier(darkVador);
        assertTrue(cmd.listerCommande().isEmpty());
        assertTrue(cmd.estVide());
        //
        assertEquals(39, magasin.consulterQuantiteEnStock(playmobil));
        assertEquals(15, magasin.consulterQuantiteEnStock(lego));
    }

    @Test
    public void nonterminer_lister()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        List<Commande> liste = magasin.listerCommandesTerminees(darkVador);
        assertTrue(liste.isEmpty());
    }

    @Test
    public void terminer_lister()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        magasin.terminerLaCommande(darkVador);
        //
        List<Commande> liste = magasin.listerCommandesTerminees(darkVador);
        assertEquals(1, liste.size());
        //
        Map.Entry[] attenduCmd = {
                new AbstractMap.SimpleEntry<>(lego, 10),
                new AbstractMap.SimpleEntry<>(playmobil, 61),
        };
        assertIterableEquals(Arrays.asList(attenduCmd), liste.get(0).listerCommande());
    }

    @Test
    public void terminer_lister2()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        //
        magasin.terminerLaCommande(darkVador);
        //
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        magasin.terminerLaCommande(darkVador);
        //
        List<Commande> liste = magasin.listerCommandesTerminees(darkVador);
        assertEquals(2, liste.size());
        //
        Map.Entry[] attenduCmd1 = {
                new AbstractMap.SimpleEntry<>(playmobil, 51),
        };
        assertIterableEquals(Arrays.asList(attenduCmd1), liste.get(0).listerCommande());
        //
        Map.Entry[] attenduCmd2 = {
                new AbstractMap.SimpleEntry<>(lego, 10),
                new AbstractMap.SimpleEntry<>(playmobil, 10),
        };
        assertIterableEquals(Arrays.asList(attenduCmd2), liste.get(1).listerCommande());
    }

    @Test
    public void terminer_consultCmd()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        magasin.terminerLaCommande(darkVador);
        //
        assertEquals(((41.5 * (51 + 10)) + (23.45 * 10)), magasin.consulterMontantTotalCommandes(darkVador));
    }

    @Test
    public void terminer_consultCmd1()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        //
        magasin.terminerLaCommande(darkVador);
        //
        magasin.ajouterAuPanier(darkVador, lego, 10);
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        magasin.terminerLaCommande(darkVador);
        //
        assertEquals(((41.5 * (51 + 10)) + (23.45 * 10)), magasin.consulterMontantTotalCommandes(darkVador));
    }

    @Test
    public void terminer_consultCmd2()
            throws ClientInconnuException, QuantiteEnStockInsuffisanteException,
            ArticleHorsStockException, QuantiteNegativeOuNulleException {
        magasin.ajouterAuPanier(darkVador, playmobil, 51);
        //
        magasin.terminerLaCommande(darkVador);
        //
        magasin.ajouterAuPanier(darkVador, lego, 10);
        //
        magasin.terminerLaCommande(darkVador);
        //
        magasin.ajouterAuPanier(darkVador, playmobil, 10);
        //
        magasin.terminerLaCommande(darkVador);
        //
        assertEquals(((41.5 * (51 + 10)) + (23.45 * 10)), magasin.consulterMontantTotalCommandes(darkVador));
    }
}
