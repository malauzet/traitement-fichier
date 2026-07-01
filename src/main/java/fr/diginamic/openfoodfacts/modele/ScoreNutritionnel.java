package fr.diginamic.openfoodfacts.modele;

public enum ScoreNutritionnel {

    A, B, C, D, E, F, INCONNU;

    public static ScoreNutritionnel fromLibelle(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            return INCONNU;
        }
        try {
            return ScoreNutritionnel.valueOf(libelle.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return INCONNU;
        }
    }
}
