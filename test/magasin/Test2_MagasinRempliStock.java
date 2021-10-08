package magasin;

import magasin.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class Test2_MagasinRempliStock {

    Magasin magasin;
    ArticleTest playmobil, lego, fusee, capsule;

    @BeforeEach
    public void init() throws
            QuantiteNegativeException, ArticleDejaEnStockException {
        magasin = new Magasin();
        //
        playmobil = new ArticleTest("Playmobil", 41.5);
        lego = new ArticleTest("Lego", 23.45);
        fusee = new ArticleTest("Reacteur Fusée", 100000);
        capsule = new ArticleTest("Capsule", 0.10);
        //
        magasin.referencerAuStock(lego, 25);
        magasin.referencerAuStock(playmobil, 100);
        magasin.referencerAuStock(fusee, 0);
        magasin.referencerAuStock(capsule, 100);
    }

    // iStock
    @Test
    public void consulterQuantite_1() throws ArticleHorsStockException {
        assertEquals(0, magasin.consulterQuantiteEnStock(fusee));
    }

    @Test
    public void consulterQuantite_2() throws ArticleHorsStockException {
        assertEquals(100, magasin.consulterQuantiteEnStock(playmobil));
    }

    @Test
    public void reappro() throws ArticleHorsStockException,
            QuantiteNegativeOuNulleException {
        magasin.reapprovisionnerStock(playmobil, 50);
        assertEquals(150, magasin.consulterQuantiteEnStock(playmobil));
    }

    @Test
    public void reappro_ex() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.reapprovisionnerStock(playmobil, -10));
    }

    @Test
    public void reappro_ex2() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.reapprovisionnerStock(playmobil, 0));
    }


    @Test
    public void reappro_ex3() {
        assertThrows(ArticleHorsStockException.class,
                () -> magasin.reapprovisionnerStock(
                        new ArticleTest("Biere artisanale"), 10));
    }

    @Test
    public void reappro_ex4() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.reapprovisionnerStock(
                        new ArticleTest("Biere artisanale"), -10));
    }

    @Test
    public void retrait() throws ArticleHorsStockException,
            QuantiteNegativeOuNulleException, QuantiteEnStockInsuffisanteException {
        magasin.retirerDuStock(10, playmobil);
        assertEquals(90, magasin.consulterQuantiteEnStock(playmobil));
    }

    @Test
    public void retrait_ex() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.retirerDuStock(-10, playmobil));
    }

    @Test
    public void retrait_ex1() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.retirerDuStock(0, playmobil));
    }

    @Test
    public void retrait_ex2() {
        assertThrows(QuantiteEnStockInsuffisanteException.class,
                () -> magasin.retirerDuStock(110, playmobil));
    }

    @Test
    public void consulterQuantite_ex() {
        assertThrows(ArticleHorsStockException.class,
                () -> magasin.consulterQuantiteEnStock(
                        new ArticleTest("Biere artisanale")));
    }

    @Test
    public void retrait_ex3() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.retirerDuStock(-10,
                        new ArticleTest("Biere artisanale")));
    }

    @Test
    public void retrait_ex4() {
        assertThrows(ArticleHorsStockException.class,
                () -> magasin.retirerDuStock(110,
                        new ArticleTest("Biere artisanale")));
    }

    @Test
    public void retrait_ex5() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.retirerDuStock(0,
                        new ArticleTest("Biere artisanale")));
    }

    @Test
    public void listerArticlesStockNom() {
        ArticleTest[] attendu = {capsule, lego, playmobil, fusee};
        assertIterableEquals(Arrays.asList(attendu),
                magasin.listerArticlesEnStockParNom());
    }

    @Test
    public void listerArticlesStockReference() {
        ArticleTest[] attendu = {playmobil, lego, fusee, capsule};
        List<iArticle> resultat = magasin.listerArticlesEnStockParReference();
        assertEquals(4, resultat.size());
        for (ArticleTest article : Arrays.asList(attendu)) {
            assertTrue(resultat.contains(article));
        }
    }

    @Test
    public void listerStock() {
        Map.Entry[] attendu = {
                new AbstractMap.SimpleEntry<>(capsule, 100),
                new AbstractMap.SimpleEntry<>(lego, 25),
                new AbstractMap.SimpleEntry<>(playmobil, 100),
                new AbstractMap.SimpleEntry<>(fusee, 0)
        };
        assertIterableEquals(Arrays.asList(attendu),
                magasin.listerStock());
    }

}
