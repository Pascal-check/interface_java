package objet;

// Assurez-vous que le package est correct

public class Vestimentaire extends Produit {
    private String taille; // Taille du vêtement

    public Vestimentaire(int id, String nom, double prix, int quantite, String type) {
        super(id, nom, prix, quantite, type);
        this.taille = taille;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    @Override
    public String toString() {
        return "Vestimentaire{" +
                "taille='" + taille + '\'' +
                "} " + super.toString();
    }
}
