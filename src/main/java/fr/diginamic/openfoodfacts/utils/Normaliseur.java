package fr.diginamic.openfoodfacts.utils;

import java.util.Locale;
import java.util.Map;

/**
 * Normalise les libellés bruts issus du CSV (ingrédients, allergènes, additifs)
 * afin de regrouper les variantes qui désignent le même concept :
 * <ul>
 *     <li>variations de casse ("OEUF" / "Oeuf" / "oeuf")</li>
 *     <li>ligature / digramme ("œuf" / "oeuf")</li>
 *     <li>tags de taxonomie OpenFoodFacts ("en:eggs", "en:milk", ...)</li>
 *     <li>pluriel simple ("oeuf" / "oeufs")</li>
 *     <li>espaces et ponctuation résiduels</li>
 * </ul>
 * Deux libellés qui produisent la même clé normalisée sont considérés comme
 * le même concept : ils partageront la même instance (Ingredient / Allergene / Additif)
 * grâce aux caches mis en place dans LecteurCsv.
 * <p>
 * Limite connue : les libellés "collés" issus d'une corruption de la donnée source
 * (ex: "daœuf", "boeufcuit") ne peuvent pas être scindés de façon fiable et restent
 * donc des clés à part entière, non fusionnées avec le concept d'origine.
 */
public final class Normaliseur {

    // Traduction des tags de taxonomie OpenFoodFacts (préfixe "en:") vers leur libellé français usuel.
    private static final Map<String, String> TAGS_EN_VERS_FR = Map.ofEntries(
            Map.entry("en:eggs", "oeuf"),
            Map.entry("en:milk", "lait"),
            Map.entry("en:gluten", "gluten"),
            Map.entry("en:soybeans", "soja"),
            Map.entry("en:mustard", "moutarde"),
            Map.entry("en:fish", "poisson"),
            Map.entry("en:nuts", "fruits a coque"),
            Map.entry("en:sulphur-dioxide-and-sulphites", "sulfites"),
            Map.entry("en:sesame-seeds", "sesame"),
            Map.entry("en:celery", "celeri"),
            Map.entry("en:lupin", "lupin"),
            Map.entry("en:molluscs", "mollusques"),
            Map.entry("en:crustaceans", "crustaces"),
            Map.entry("en:peanuts", "arachide")
    );

    private Normaliseur() {
    }

    /**
     * Calcule la clé de regroupement d'un libellé (à utiliser pour la déduplication,
     * jamais pour l'affichage).
     */
    public static String cle(String libelleBrut) {

        if (libelleBrut == null) {
            return "";
        }

        String v = libelleBrut.trim().toLowerCase(Locale.FRENCH);

        // Tag de taxonomie type "en:eggs" : traduit s'il est connu, sinon nettoyé du préfixe.
        if (v.startsWith("en:")) {
            v = TAGS_EN_VERS_FR.getOrDefault(v, v.substring(3).replace('-', ' '));
        }

        // Ligature / digramme : on uniformise sur "oe" (ex: "œuf" -> "oeuf").
        v = v.replace("œ", "oe");

        // On retire tout ce qui n'est pas une lettre (accentuée) ou un espace.
        v = v.replaceAll("[^a-zéèêëàâäîïôöùûüç ]", "");

        // Espaces multiples -> simple espace.
        v = v.replaceAll("\\s+", " ").trim();

        // Pluriel simple ("oeufs" -> "oeuf") : seulement si le mot reste significatif,
        // pour éviter de trop rogner des mots courts.
        if (v.endsWith("s") && v.length() > 3 && !v.endsWith("ss")) {
            v = v.substring(0, v.length() - 1);
        }

        return v;
    }

    /**
     * Construit le libellé "propre" (première lettre en majuscule) qui sera affiché
     * à l'utilisateur. Utilisé lors de la création de l'instance canonique, la première
     * fois qu'une clé normalisée est rencontrée.
     */
    public static String libellePropre(String libelleBrut) {
        String cle = cle(libelleBrut);
        if (cle.isEmpty()) {
            return libelleBrut == null ? "" : libelleBrut.trim();
        }
        return Character.toUpperCase(cle.charAt(0)) + cle.substring(1);
    }
}
