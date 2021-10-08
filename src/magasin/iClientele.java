package magasin;

import magasin.exceptions.ClientDejaEnregistreException;

import java.util.List;


/**
 * défini les opérations concernant la gestion de clientèle d'un magasin
 */
public interface iClientele {


    /**
     * référence un nouveau client dans le magasin
     *
     * @param nouveauClient le nouveau client à référencer
     * @throws ClientDejaEnregistreException si le client est déjà référencer dans le magasins
     */
    void enregistrerNouveauClient(iClient nouveauClient) throws ClientDejaEnregistreException;

    List<iClient> listerLesClientsParId();

}
