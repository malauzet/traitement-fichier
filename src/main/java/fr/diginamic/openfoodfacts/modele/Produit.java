package fr.diginamic.openfoodfacts.modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Produit {

    private String nom;
    private Categorie categorie;
    private Marque marque;
    private ScoreNutritionnel scoreNutritionnel = ScoreNutritionnel.INCONNU;
    private DonneesNutritionnelles donneesNutritionnelles = new DonneesNutritionnelles();

    private final List<Ingredient> ingredients = new ArrayList<>();
    private final List<Additif> additifs = new ArrayList<>();
    private final List<Allergene> allergenes = new ArrayList<>();

    // Getters & Setters ————————————————————————————————————————————————————————————————————————————————
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public ScoreNutritionnel getScoreNutritionnel() {
        return scoreNutritionnel;
    }

    public void setScoreNutritionnel(ScoreNutritionnel scoreNutritionnel) {
        this.scoreNutritionnel = scoreNutritionnel;
    }

    public DonneesNutritionnelles getDonneesNutritionnelles() {
        return donneesNutritionnelles;
    }

    public void setDonneesNutritionnelles(DonneesNutritionnelles donneesNutritionnelles) {
        this.donneesNutritionnelles = donneesNutritionnelles;
    }

    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public void ajouterIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public List<Additif> getAdditifs() {
        return Collections.unmodifiableList(additifs);
    }

    public void ajouterAdditif(Additif additif) {
        this.additifs.add(additif);
    }

    public List<Allergene> getAllergenes() {
        return Collections.unmodifiableList(allergenes);
    }

    public void ajouterAllergene(Allergene allergene) {
        this.allergenes.add(allergene);
    }

    @Override
    public String toString() {
        return nom + " (" + marque + ") — " + categorie + " — Score : " + scoreNutritionnel;
    }
}
