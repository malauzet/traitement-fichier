package fr.diginamic.openfoodfacts.menu.services;

import fr.diginamic.openfoodfacts.modele.Produit;
import fr.diginamic.openfoodfacts.modele.Stock;
import fr.diginamic.openfoodfacts.utils.ComparatorScoreNutritionnel;

import java.util.List;
import java.util.Scanner;

public abstract class MenuService {
    public abstract void traiter(Stock stock, Scanner scanner);

    protected static void trierEtAfficherParScore(List<Produit> topProduits) {
        topProduits.sort(new ComparatorScoreNutritionnel());

        System.out.println("\n————————————————————————————————————————————————————————————————");
        if (topProduits.isEmpty()) {
            System.out.println("                      Aucun produit trouvé.                     ");
        } else {
            for (Produit produit : topProduits) {
                System.out.println(produit);
            }
            System.out.println("————————————————————————————————————————————————————————————————");
            System.out.println(topProduits.size() + " produit(s) trouvé(s).");
        }
        System.out.println("————————————————————————————————————————————————————————————————");
    }
}
