package fr.diginamic.openfoodfacts.modele;

public record Categorie(String libelle) {

    @Override
    public String toString() {
        return libelle;
    }
}
