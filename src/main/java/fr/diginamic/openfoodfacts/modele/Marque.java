package fr.diginamic.openfoodfacts.modele;

public record Marque(String nom) {

    @Override
    public String toString() {
        return nom;
    }
}
