package fr.diginamic.openfoodfacts.menu.services;

import fr.diginamic.openfoodfacts.modele.Additif;
import fr.diginamic.openfoodfacts.modele.Produit;
import fr.diginamic.openfoodfacts.modele.Stock;
import fr.diginamic.openfoodfacts.utils.ComparatorNombreOccurrences;

import java.util.*;

public class AdditifsLesPlusCourants extends MenuService {

    @Override
    public void traiter(Stock stock, Scanner scanner) {

        Map<Additif, Integer> topAdditifs = new HashMap<>();

        for (Produit produit : stock.getProduits()) {
            for (Additif additif : produit.getAdditifs()) {
                topAdditifs.put(additif, topAdditifs.getOrDefault(additif, 0) + 1);
            }
        }

        List<Map.Entry<Additif, Integer>> listeTriee = new ArrayList<>(topAdditifs.entrySet());

        listeTriee.sort(new ComparatorNombreOccurrences<>());

        System.out.println("\n————————————————————————————————————————————————————————————————");
        for (Map.Entry<Additif, Integer> entry : listeTriee) {
            System.out.println(entry.getKey() + " :  " + entry.getValue());
        }
        System.out.println("————————————————————————————————————————————————————————————————");
        System.out.println(listeTriee.size() + " produit(s) trouvé(s).");
        System.out.println("————————————————————————————————————————————————————————————————");
    }
}
