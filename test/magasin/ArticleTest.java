package magasin;

public class ArticleTest implements iArticle, Comparable<ArticleTest> {

    private static int nbReference = 1;
    private int reference;
    private String nom;
    private double prix;

    public ArticleTest(String nom, double prix) {
        this.nom = nom;
        this.reference = nbReference++;
        this.prix = prix;
    }


    public ArticleTest(String nom, String desc, double prix) {
        this(nom, prix);
    }

    public ArticleTest(String nom) {
        this(nom, 0.0);
    }

    @Override
    public int hashCode() {
        return reference();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleTest)) return false;
        ArticleTest article = (ArticleTest) o;
        return this.reference() == article.reference();
    }

    @Override
    public int compareTo(ArticleTest autre) {
        return COMPARATEUR_NOM.compare(this, autre);
    }

    @Override
    public int reference() {
        return reference;
    }

    @Override
    public String nom() {
        return nom;
    }

    @Override
    public double prix() {
        return prix;
    }

}
