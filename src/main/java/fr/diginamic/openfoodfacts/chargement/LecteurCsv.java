package fr.diginamic.openfoodfacts.chargement;

import fr.diginamic.openfoodfacts.modele.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LecteurCsv {

    private static final String SEPARATEUR_COLONNE = "\\|";
    private static final String[] SEPARATEURS_LISTE = {",", ";"};

    public Stock chargerStock(String chemin) {

        Stock stock = new Stock();
        List<String> lignes = lireLignes(chemin);
        Map<String, Integer> indexCol = construireIndexColonnes(lignes.getFirst());
        int nbLignesEnErreur = 0;

        for (int i = 1; i < lignes.size(); i++) {

            String ligne = lignes.get(i);

            if (ligne.isBlank()) {
                continue;
            }
            try {
                Produit produit = construireProduit(ligne, indexCol);
                stock.ajouter(produit);
            } catch (Exception e) {
                nbLignesEnErreur++;
                System.err.println("Ligne " + (i + 1) + " ignorée (" + e.getMessage() + ")");
            }
        }

        if (nbLignesEnErreur > 0) {
            System.out.println(nbLignesEnErreur + " ligne(s) n'ont pas pu être traitées et ont été ignorées.");
        }

        return stock;
    }

    // Prends une entete de CSV en paramètre et renvoie une map d'index des colonnes du CSV
    public Map<String, Integer> construireIndexColonnes(String entete) {

        int i = 0;
        String[] et = entete.split(SEPARATEUR_COLONNE);
        Map<String, Integer> enteteMap = new HashMap<>();

        while (i < et.length) {
            enteteMap.put(et[i].trim().toLowerCase(), i);
            i++;
        }

        return enteteMap;
    }

    // Lit toutes les lignes du CSV
    public List<String> lireLignes(String chemin) {

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {

            List<String> lignes = new ArrayList<>();
            String ligne;

            while ((ligne = br.readLine()) != null) {
                lignes.add(ligne.trim());
            }
            return lignes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Produit construireProduit(String ligne, Map<String, Integer> index) {

        String[] valeurs = ligne.split(SEPARATEUR_COLONNE, -1);

        Produit produit = new Produit();

        produit.setNom(valeur(valeurs, index, "nom"));

        String texteCategorie = valeur(valeurs, index, "categorie");
        produit.setCategorie(new Categorie(texteCategorie));

        // On prend seulement la première marque (Exemple : ligne 6 du CSV à deux marques).
        String texteMarque = valeur(valeurs, index, "marque");
        String premiereMarque = texteMarque.split(",", 2)[0].trim();
        produit.setMarque(new Marque(premiereMarque));

        String texteScore = valeur(valeurs, index, "nutritiongradefr");
        produit.setScoreNutritionnel(ScoreNutritionnel.fromLibelle(texteScore));

        DonneesNutritionnelles donnees = new DonneesNutritionnelles();
        donnees.setEnergie(nombre(valeur(valeurs, index, "energie100g")));
        donnees.setGraisses(nombre(valeur(valeurs, index, "graisse100g")));
        donnees.setSucres(nombre(valeur(valeurs, index, "sucres100g")));
        donnees.setFibres(nombre(valeur(valeurs, index, "fibres100g")));
        donnees.setProteines(nombre(valeur(valeurs, index, "proteines100g")));
        donnees.setSel(nombre(valeur(valeurs, index, "sel100g")));
        donnees.setVitamineA(nombre(valeur(valeurs, index, "vita100g")));
        donnees.setVitamineD(nombre(valeur(valeurs, index, "vitd100g")));
        donnees.setVitamineE(nombre(valeur(valeurs, index, "vite100g")));
        donnees.setVitamineK(nombre(valeur(valeurs, index, "vitk100g")));
        donnees.setVitamineC(nombre(valeur(valeurs, index, "vitc100g")));
        donnees.setVitamineB1(nombre(valeur(valeurs, index, "vitb1100g")));
        donnees.setVitamineB2(nombre(valeur(valeurs, index, "vitb2100g")));
        donnees.setVitaminePP(nombre(valeur(valeurs, index, "vitpp100g")));
        donnees.setVitamineB6(nombre(valeur(valeurs, index, "vitb6100g")));
        donnees.setVitamineB9(nombre(valeur(valeurs, index, "vitb9100g")));
        donnees.setVitamineB12(nombre(valeur(valeurs, index, "vitb12100g")));
        donnees.setCalcium(nombre(valeur(valeurs, index, "calcium100g")));
        donnees.setMagnesium(nombre(valeur(valeurs, index, "magnesium100g")));
        donnees.setFer(nombre(valeur(valeurs, index, "fer100g")));
        donnees.setBetaCarotene(nombre(valeur(valeurs, index, "betacarotene100g")));
        donnees.setHuileDePalme(booleen(valeur(valeurs, index, "presencehuilepalme")));
        produit.setDonneesNutritionnelles(donnees);

        for (String libelle : decouperListe(valeur(valeurs, index, "ingredients"))) {
            produit.ajouterIngredient(new Ingredient(libelle));
        }
        for (String libelle : decouperListe(valeur(valeurs, index, "allergenes"))) {
            produit.ajouterAllergene(new Allergene(libelle));
        }
        for (String libelle : decouperListe(valeur(valeurs, index, "additifs"))) {
            produit.ajouterAdditif(new Additif(libelle));
        }

        return produit;
    }

    // Renvoie la valeur d'une colonne
    private String valeur(String[] valeurs, Map<String, Integer> index, String nomColonne) {
        Integer i = index.get(nomColonne);
        if (i == null || i >= valeurs.length) { // On vérifie que la colonne existe
            return "";
        }
        return valeurs[i].trim();
    }

    private List<String> decouperListe(String valeur) {

        List<String> resultat = new ArrayList<>();

        // Si la valeur est vide, on retourne une liste vide
        if (valeur.isBlank()) {
            return resultat;
        }

        String[] elements = getStrings(valeur);

        // Pour chaque élément du tableau, on enlève ses espaces et on l'ajoute à la liste sinon on ne fait rien.
        for (String element : elements) {
            String libelle = element.trim();
            if (!libelle.isEmpty()) {
                resultat.add(libelle);
            }
        }
        return resultat;
    }

    private static String[] getStrings(String valeur) {
        String separateurTrouve = null;
        // Pour chaque séparateur, on regarde s'il est dans 'valeur' s'il est dedans, on le garde et on sort du for.
        for (String separateur : SEPARATEURS_LISTE) {
            if (valeur.contains(separateur)) {
                separateurTrouve = separateur;
                break;
            }
        }

        // Si le séparateur existe on l'utilise pour split sinon on ajoute la valeur directement
        // Pattern.quote(...) serait utile si ma liste de séparateurs avait '|' par exemple.
        return (separateurTrouve != null) ? valeur.split(separateurTrouve) : new String[]{valeur};
    }

    private double nombre(String valeur) {
        if (valeur.isBlank()) {
            return 0d;
        }
        try {
            return Double.parseDouble(valeur.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0d;
        }
    }

    private boolean booleen(String valeur) {
        if (valeur.isBlank()) {
            return false;
        }
        String v = valeur.trim().toLowerCase();
        return v.equals("1") || v.equals("true") || v.equals("oui") || v.equals("yes");
    }
}
