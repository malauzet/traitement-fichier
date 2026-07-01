package fr.diginamic.openfoodfacts.menu.services;

import fr.diginamic.openfoodfacts.modele.Produit;
import fr.diginamic.openfoodfacts.modele.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MeilleursProduitsPourMarque extends MenuService {

    @Override
    public void traiter(Stock stock, Scanner scanner) {

        System.out.print("\nEntrez le nom d'une marque : ");
        String marqueSc = scanner.nextLine();

        List<Produit> topProduits = new ArrayList<>();

        for (Produit produit : stock.getProduits()) {
            if (produit.getMarque().nom().equalsIgnoreCase(marqueSc)) {
                topProduits.add(produit);
            }
        }
        trierEtAfficherParScore(topProduits);
    }
}
