package magasin;

import magasin.exceptions.ClientDejaEnregistreException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Test4_MagasinRempliClientele {

    Magasin magasin;
    ClientTest darkVador;

    @BeforeEach
    public void init() throws ClientDejaEnregistreException {
        magasin = new Magasin();
        //
        darkVador = new ClientTest("Dark", "Vador");
        magasin.enregistrerNouveauClient(new ClientTest("Iron", "Man"));
        magasin.enregistrerNouveauClient(new ClientTest("Bat", "Man"));
        magasin.enregistrerNouveauClient(darkVador);
        magasin.enregistrerNouveauClient(new ClientTest("Aqua", "Man"));
        magasin.enregistrerNouveauClient(new ClientTest("", "Albator"));
        magasin.enregistrerNouveauClient(new ClientTest("M.", "Spock"));
    }


    // iClientele

    @Test
    public void ListerClients() {
        ClientTest[] attendu = {
                new ClientTest("", "Albator"),
                new ClientTest("Aqua", "Man"),
                new ClientTest("Bat", "Man"),
                new ClientTest("Iron", "Man"),
                new ClientTest("M.", "Spock"),
                darkVador
        };
        List<iClient> resultat = magasin.listerLesClientsParId();
        assertEquals(6, resultat.size());
        for (ClientTest ci : Arrays.asList(attendu)) {
            assertTrue(resultat.contains(ci));
        }
    }


    @Test
    public void nouveauClient_ex() {
        assertThrows(ClientDejaEnregistreException.class,
                () -> magasin.enregistrerNouveauClient(darkVador));
    }

}
