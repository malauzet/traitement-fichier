package fr.diginamic.openfoodfacts;

import fr.diginamic.openfoodfacts.chargement.LecteurCsv;
import fr.diginamic.openfoodfacts.menu.MenuPrincipal;
import fr.diginamic.openfoodfacts.menu.services.*;
import fr.diginamic.openfoodfacts.modele.Stock;

import java.util.Scanner;

public class ApplicationOpenFoodFacts {
    static void main() {

        LecteurCsv lecteur = new LecteurCsv();
        Stock stock = lecteur.chargerStock("src/main/resources/open-food-facts.csv");
        System.out.println(stock.getProduits().size() + " produits chargés.");

        Scanner scanner = new Scanner(System.in);
        MenuPrincipal menu = new MenuPrincipal();
        boolean continuer = true;

        while (continuer) {

            menu.afficher();

            try {
                int choix = Integer.parseInt(scanner.nextLine());

                switch (choix) {
                    case 1 -> {
                        MenuService meilleursPPM = new MeilleursProduitsPourMarque();
                        meilleursPPM.traiter(stock, scanner);
                    }
                    case 2 -> {
                        MenuService meilleursPPC = new MeilleursProduitsPourCategorie();
                        meilleursPPC.traiter(stock, scanner);
                    }
                    case 3 -> {
                        MenuService meilleursPPMeC = new MeilleursProduitsPourMarqueEtCategorie();
                        meilleursPPMeC.traiter(stock, scanner);
                    }
                    case 4 -> {
                        MenuService allergenesLPC = new AllergenesLesPlusCourants();
                        allergenesLPC.traiter(stock, scanner);
                    }
                    case 5 -> {
                        MenuService additifsLPC = new AdditifsLesPlusCourants();
                        additifsLPC.traiter(stock, scanner);
                    }
                    case 6 -> {
                        continuer = false;
                        System.out.println("\n————————————————————————————————————————————————————————————————");
                        System.out.println("                          Au revoir !                           ");
                        System.out.println("————————————————————————————————————————————————————————————————");
                    }
                    default -> System.out.println("Choix invalide, veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
            System.out.println();
        }
        scanner.close();
    }
}
