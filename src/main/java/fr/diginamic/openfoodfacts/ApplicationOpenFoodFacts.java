package fr.diginamic.openfoodfacts;

import fr.diginamic.openfoodfacts.chargement.LecteurCsv;
import fr.diginamic.openfoodfacts.modele.Stock;

import java.io.IOException;

public class ApplicationOpenFoodFacts {
    static void main() throws IOException {

        LecteurCsv lecteur = new LecteurCsv();
        Stock stock = lecteur.chargerStock("src/main/resources/open-food-facts.csv");
        System.out.println(stock.getProduits().size() + " produits chargés.");
    }
}
