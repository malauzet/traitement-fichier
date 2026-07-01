package fr.diginamic.openfoodfacts.menu.services;

import fr.diginamic.openfoodfacts.modele.Produit;
import fr.diginamic.openfoodfacts.modele.Stock;
import fr.diginamic.openfoodfacts.utils.ComparatorScoreNutritionnel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MeilleursProduitsPourCategorie extends MenuService {

    @Override
    public void traiter(Stock stock, Scanner scanner) {

        System.out.print("\nEntrez le nom d'une catégorie : ");
        String categorieSc = scanner.nextLine();

        List<Produit> topProduits = new ArrayList<>();

        for (Produit produit : stock.getProduits()) {
            if (produit.getCategorie().libelle().equalsIgnoreCase(categorieSc)) {
                topProduits.add(produit);
            }
        }
        topProduits.sort(new ComparatorScoreNutritionnel());

        if (topProduits.isEmpty()) {
            System.out.println("Aucun produit trouvé.");
            return;
        }
        for  (Produit produit : topProduits) {
            System.out.println(produit);
        }
        System.out.println(topProduits.size() + " produit(s) trouvé(s).");
    }
}
