# traitement-fichier — Open Food Facts

Application Java en ligne de commande qui charge en mémoire un extrait de la base **Open Food Facts** (produits alimentaires fabriqués en France) au format CSV, puis propose un menu de recherche et de statistiques sur ces produits.

Ce projet est un TP d'autonomie (Digitalent Formation) sur le traitement de fichiers en Java, avant intégration ultérieure des données en base via JDBC.

## Contexte

[Open Food Facts](https://world.openfoodfacts.org/) est une base de données ouverte et collaborative sur les produits alimentaires, notamment utilisée par l'application **Yuka** pour fournir un score nutritionnel (de A, excellent, à F, mauvais) sur les produits commercialisés en France.

Le fichier fourni (`src/main/resources/open-food-facts.csv`) contient environ 13 400 produits, avec pour chacun : catégorie, marque, nom, score nutritionnel, liste d'ingrédients, valeurs nutritionnelles (énergie, graisses, sucres, sel, vitamines, minéraux...), allergènes, additifs et présence d'huile de palme. Les colonnes sont séparées par `|` et certaines colonnes (ingrédients, allergènes, additifs) contiennent elles-mêmes des listes de valeurs séparées par `,` ou `;`.

## Fonctionnalités

Au lancement, l'application charge l'intégralité du CSV en mémoire, puis affiche un menu interactif :

1. Meilleurs produits pour une marque donnée
2. Meilleurs produits pour une catégorie donnée
3. Meilleurs produits par marque et par catégorie
4. Allergènes les plus courants (avec le nombre de produits concernés)
5. Additifs les plus courants (avec le nombre de produits concernés)
6. Quitter

## Architecture

Le projet suit une approche objet, avec un découpage en packages par responsabilité :

```
fr.diginamic.openfoodfacts
├── ApplicationOpenFoodFacts     # classe exécutable (point d'entrée)
├── chargement
│   └── LecteurCsv               # lecture et parsing du fichier CSV → Stock
├── menu
│   ├── MenuPrincipal            # affichage du menu
│   └── services
│       ├── MenuService                          # interface commune aux services
│       ├── MeilleursProduitsPourMarque
│       ├── MeilleursProduitsPourCategorie
│       ├── MeilleursProduitsPourMarqueEtCategorie
│       ├── AllergenesLesPlusCourants
│       └── AdditifsLesPlusCourants
├── modele
│   ├── Stock                    # conteneur de tous les Produit chargés
│   ├── Produit                  # catégorie, marque, score, données nutritionnelles, ingrédients, additifs, allergènes
│   ├── Categorie
│   ├── Marque
│   ├── Ingredient
│   ├── Allergene
│   ├── Additif
│   ├── DonneesNutritionnelles   # énergie, graisses, sucres, sel, vitamines, minéraux, huile de palme...
│   └── ScoreNutritionnel        # enum A à F, + INCONNU
└── utils
    ├── Normaliseur                     # normalisation des libellés (accents, casse...)
    ├── ComparatorScoreNutritionnel      # tri des produits par score nutritionnel
    └── ComparatorNombreOccurrences      # tri par nombre d'occurrences (allergènes/additifs)
```

### Points clés du modèle

- **Stock** contient la liste de tous les `Produit` chargés depuis le fichier.
- **Produit** référence un `Categorie`, une `Marque`, un `ScoreNutritionnel`, un objet `DonneesNutritionnelles`, ainsi que des listes de `Ingredient`, `Additif` et `Allergene`.
- Chaque concept métier (catégorie, marque, ingrédient, allergène, additif) est représenté par sa propre classe plutôt que par une simple `String`, afin de préparer les futurs TP (persistance JDBC/JPA).
- Lors du chargement, `LecteurCsv` met en cache les instances d'`Ingredient`, `Allergene` et `Additif` déjà rencontrées (via des `Map`) afin qu'un même ingrédient (ex : "Lait") ne soit instancié qu'une seule fois et partagé entre tous les produits qui le contiennent, ce qui limite l'empreinte mémoire.

## Format du fichier CSV

- 1ère ligne : entête, colonnes séparées par `|`.
- Lignes suivantes : un produit par ligne, colonnes séparées par `|`.
- Certaines colonnes (ingrédients, allergènes, additifs) contiennent plusieurs valeurs séparées par `,` ou `;` selon les lignes.
- Le parsing gère les lignes mal formées : une ligne en erreur est ignorée et un message est affiché en console, sans interrompre le chargement du reste du fichier.

## Prérequis

- Java 25 (JDK)
- Maven

Le projet ne dépend d'aucune librairie externe (pas de `commons-lang`/`commons-io` : le parsing du CSV est fait avec l'API standard Java `java.io` / `java.util`).

## Lancer l'application

```bash
git clone https://github.com/malauzet/traitement-fichier.git
cd traitement-fichier
mvn compile exec:java -Dexec.mainClass="fr.diginamic.openfoodfacts.ApplicationOpenFoodFacts"
```

Ou directement depuis un IDE (IntelliJ / STS) en exécutant `ApplicationOpenFoodFacts`.

Au démarrage, le nombre de produits chargés est affiché, suivi du menu interactif décrit ci-dessus.

## Pistes d'évolution

- Persistance des données en base via JDBC (TP ultérieur).
- Ajout de tests unitaires sur le parsing CSV et les comparateurs.

---
© Tous droits réservés à Richard BONNAMY (sujet du TP — Digitalent Formation)