package fr.diginamic.openfoodfacts.menu.services;

import fr.diginamic.openfoodfacts.modele.Stock;

import java.util.Scanner;

public abstract class MenuService {
    public abstract void traiter(Stock stock, Scanner scanner);
}
