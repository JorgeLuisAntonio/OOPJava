package magasin;

import java.util.Comparator;

/**
 * defini une notion abstraite d'Article pour un magasin
 */

public interface iArticle {

    /**
     * Tri les articles par référence
     */
    Comparator<iArticle> COMPARATEUR_REFERENCE = new Comparator<iArticle>() {
        @Override
        public int compare(iArticle o1, iArticle o2) {
            return o1.reference() - o2.reference();
        }
    };
    /**
     * Tri les articles par nom
     */
    Comparator<iArticle> COMPARATEUR_NOM = new Comparator<iArticle>() {
        @Override
        public int compare(iArticle o1, iArticle o2) {
            return String.CASE_INSENSITIVE_ORDER.compare(o1.nom(), o2.nom());
        }
    };

    /**
     * donne la référence de l'article en magasin = la référence d'un article doit être unique
     *
     * @return la référence de l'article
     */
    int reference();

    /**
     * donne le nom de l'article en magasin
     *
     * @return le nom de l'article
     */
    String nom();

    /**
     * donne le prix unitaire de l'article en magasin
     *
     * @return le prix de l'article
     */
    double prix();

}
