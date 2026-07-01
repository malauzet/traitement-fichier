package fr.diginamic.openfoodfacts.utils;

import fr.diginamic.openfoodfacts.modele.Produit;

import java.util.Comparator;

public class ComparatorScoreNutritionnel implements Comparator<Produit> {

    @Override
    public int compare(Produit p1, Produit p2) {
        return p1.getScoreNutritionnel().compareTo(p2.getScoreNutritionnel());
    }
}
