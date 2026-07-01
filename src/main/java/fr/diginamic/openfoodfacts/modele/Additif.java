package fr.diginamic.openfoodfacts.modele;

public record Additif(String libelle) {

    @Override
    public String toString() {
        return libelle;
    }
}
