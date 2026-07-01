package fr.diginamic.openfoodfacts.modele;

public record Ingredient(String libelle) {

    @Override
    public String toString() {
        return libelle;
    }
}
