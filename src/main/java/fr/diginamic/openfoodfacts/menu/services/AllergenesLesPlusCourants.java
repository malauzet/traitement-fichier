package fr.diginamic.openfoodfacts.menu.services;

import fr.diginamic.openfoodfacts.modele.Allergene;
import fr.diginamic.openfoodfacts.modele.Produit;
import fr.diginamic.openfoodfacts.modele.Stock;
import fr.diginamic.openfoodfacts.utils.ComparatorNombreOccurrences;

import java.util.*;

public class AllergenesLesPlusCourants extends MenuService {

    @Override
    public void traiter(Stock stock, Scanner scanner) {

        Map<Allergene, Integer> topAllergenes = new HashMap<>();

        for (Produit produit : stock.getProduits()) {
            for (Allergene allergene : produit.getAllergenes()) {
                topAllergenes.put(allergene, topAllergenes.getOrDefault(allergene, 0) + 1);
            }
        }

        List<Map.Entry<Allergene, Integer>> listeTriee = new ArrayList<>(topAllergenes.entrySet());

        listeTriee.sort(new ComparatorNombreOccurrences<>());

        System.out.println("\n————————————————————————————————————————————————————————————————");
        for (Map.Entry<Allergene, Integer> entry : listeTriee) {
            System.out.println(entry.getKey() + " :  " + entry.getValue());
        }
        System.out.println("————————————————————————————————————————————————————————————————");
        System.out.println(listeTriee.size() + " produit(s) trouvé(s).");
        System.out.println("————————————————————————————————————————————————————————————————");
    }
}
