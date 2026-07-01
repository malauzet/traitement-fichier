package fr.diginamic.openfoodfacts.modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stock {

    private final List<Produit> produits = new ArrayList<>();

    public void ajouter(Produit produit) {
        produits.add(produit);
    }

    public List<Produit> getProduits() {
        return Collections.unmodifiableList(produits);
    }
}
