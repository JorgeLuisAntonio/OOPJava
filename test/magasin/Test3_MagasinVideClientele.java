package magasin;

import magasin.exceptions.ClientDejaEnregistreException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Test3_MagasinVideClientele {

    Magasin magasin;


    @BeforeEach
    public void init() {
        magasin = new Magasin();
    }


    // interface iClientele

    @Test
    public void listerClient() {
        assertEquals(Collections.EMPTY_LIST, magasin.listerLesClientsParId());
    }

    @Test
    public void enregistrerClient0()
            throws ClientDejaEnregistreException {
        ClientTest client = new ClientTest("Iron", "Man");
        magasin.enregistrerNouveauClient(client);
        assertTrue(magasin.listerLesClientsParId().contains(client));
    }

    @Test
    public void enregistrerClient1()
            throws ClientDejaEnregistreException {
        magasin.enregistrerNouveauClient(new ClientTest("Iron", "Man"));
        assertTrue(magasin.listerLesClientsParId().contains(new ClientTest("Iron", "Man")));
    }

    @Test
    public void enregistrerClient2()
            throws ClientDejaEnregistreException {
        magasin.enregistrerNouveauClient(new ClientTest("Iron", "Man"));
        assertFalse(magasin.listerLesClientsParId().contains(new ClientTest("Dober", "Man")));
    }
}
