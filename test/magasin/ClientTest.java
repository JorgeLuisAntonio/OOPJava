package magasin;

public class ClientTest implements iClient {

    private String nom;
    private String prenom;

    public ClientTest(String p, String n) {
        prenom = p;
        nom = n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientTest)) return false;
        ClientTest client = (ClientTest) o;
        return this.id().equals(client.id());
    }

    @Override
    public int hashCode() {
        return (id() != null ? id().hashCode() : 0);
    }

    @Override
    public String id() {
        return nom + prenom;
    }

    @Override
    public int compareTo(iClient o) {
        return iClient.COMPARATEUR_ID.compare(this, o);
    }
}
