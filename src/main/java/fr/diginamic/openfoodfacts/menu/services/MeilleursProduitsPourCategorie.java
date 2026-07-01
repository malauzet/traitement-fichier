package fr.diginamic.openfoodfacts.menu.services;

import fr.diginamic.openfoodfacts.modele.Produit;
import fr.diginamic.openfoodfacts.modele.Stock;

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
        trierEtAfficherParScore(topProduits);
    }
}
