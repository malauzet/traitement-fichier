package fr.diginamic.openfoodfacts.utils;

import java.util.Comparator;
import java.util.Map;

public class ComparatorNombreOccurrences<T> implements Comparator<Map.Entry<T, Integer>> {
    @Override
    public int compare(Map.Entry<T, Integer> e1, Map.Entry<T, Integer> e2) {
        return e2.getValue().compareTo(e1.getValue());
    }
}
