package magasin;

import magasin.exceptions.ArticleDejaEnStockException;
import magasin.exceptions.ArticleHorsStockException;
import magasin.exceptions.QuantiteNegativeException;
import magasin.exceptions.QuantiteNegativeOuNulleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Test1_MagasinVideStock {

    Magasin magasin;


    @BeforeEach
    public void init() {
        magasin = new Magasin();
    }


    // interface iStock

    @Test
    public void listerArticlesNom() {
        assertEquals(Collections.EMPTY_LIST, magasin.listerArticlesEnStockParNom());
    }

    @Test
    public void listerArticlesRef() {
        assertEquals(Collections.EMPTY_LIST, magasin.listerArticlesEnStockParReference());
    }

    @Test
    public void reappro_ex1() {
        assertThrows(ArticleHorsStockException.class,
                () -> magasin.reapprovisionnerStock(
                        new ArticleTest("bier", 2.5), 10)
        );
    }

    @Test
    public void reappro_ex2() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.reapprovisionnerStock(
                        new ArticleTest("bier", 2.5), -10)
        );
    }

    @Test
    public void reappro_ex3() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.reapprovisionnerStock(
                        new ArticleTest("bier", 2.5), 0)
        );
    }

    @Test
    public void consult_ex() {
        assertThrows(ArticleHorsStockException.class,
                () -> magasin.consulterQuantiteEnStock(
                        new ArticleTest("bier", 2.5))
        );
    }

    @Test
    public void retrait_ex1() {
        assertThrows(ArticleHorsStockException.class,
                () -> magasin.retirerDuStock(5,
                        new ArticleTest("bier", "local irish bier", 2.5))
        );
    }

    @Test
    public void retrait_ex2() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.retirerDuStock(-5,
                        new ArticleTest("bier", "local irish bier", 2.5))
        );
    }

    @Test
    public void retrait_ex3() {
        assertThrows(QuantiteNegativeOuNulleException.class,
                () -> magasin.retirerDuStock(0,
                        new ArticleTest("bier", "local irish bier", 2.5))
        );
    }

    @Test
    public void ajout()
            throws QuantiteNegativeException, ArticleDejaEnStockException {
        ArticleTest bier = new ArticleTest("bier", "local irish bier", 2.5);
        magasin.referencerAuStock(bier, 10);
        assertTrue(magasin.listerArticlesEnStockParNom().contains(bier));
    }

    @Test
    public void ajout0()
            throws QuantiteNegativeException, ArticleDejaEnStockException {
        ArticleTest bier = new ArticleTest("bier", "local irish bier", 2.5);
        magasin.referencerAuStock(bier, 0);
        assertTrue(magasin.listerArticlesEnStockParNom().contains(bier));
    }

    @Test
    public void ajout_ex1() {
        assertThrows(QuantiteNegativeException.class,
                () -> magasin.referencerAuStock(
                        new ArticleTest("bier", "local irish bier", 2.5), -10)
        );
    }

    @Test
    public void ajout_ex2()
            throws QuantiteNegativeException, ArticleDejaEnStockException {
        ArticleTest bier = new ArticleTest("bier", "local irish bier", 2.5);
        magasin.referencerAuStock(bier, 10);
        assertThrows(ArticleDejaEnStockException.class,
                () -> magasin.referencerAuStock(bier, 4));
    }

    @Test
    public void ajout_ex3()
            throws QuantiteNegativeException, ArticleDejaEnStockException {
        ArticleTest bier = new ArticleTest("bier", "local irish bier", 2.5);
        magasin.referencerAuStock(bier, 10);
        assertThrows(QuantiteNegativeException.class,
                () -> magasin.referencerAuStock(bier, -4));
    }

}
