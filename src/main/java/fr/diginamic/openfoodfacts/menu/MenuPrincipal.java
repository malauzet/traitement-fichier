package fr.diginamic.openfoodfacts.menu;

public class MenuPrincipal {

    public void afficher() {
        System.out.println("——————————————————————— MENU FOOD FACTS ———————————————————————");
        System.out.println("1. Meilleurs produits pour une marque donnée");
        System.out.println("2. Meilleurs produits pour une catégorie donnée");
        System.out.println("3. Meilleurs produits par marque et catégorie");
        System.out.println("4. Allergènes les plus courants (avec nb de produits)");
        System.out.println("5. Additifs les plus courants (avec nb de produits)");
        System.out.println("6. Sortir");
        System.out.println("————————————————————————————————————————————————————————————————");
        System.out.print("\nVotre choix : ");
    }
}
