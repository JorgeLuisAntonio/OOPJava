package magasin;

import java.util.Comparator;

/**
 * défini une notion abstrait de client
 */

public interface iClient extends Comparable<iClient> {

    /**
     * Tri les clients par id
     */
    Comparator<iClient> COMPARATEUR_ID = new Comparator<iClient>() {
        @Override
        public int compare(iClient o1, iClient o2) {
            return String.CASE_INSENSITIVE_ORDER.compare(o1.id(), o2.id());
        }
    };

    /**
     * donne l'id du client = l'id d'un client doit être unique
     *
     * @return l'id du client
     */
    String id();

}

