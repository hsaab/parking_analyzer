package edu.upenn.cit594.utils;

import edu.upenn.cit594.data.Area;

import java.util.Comparator;

// Compares Area's by the market value per capita, with larger ones coming first (reverse order).
public class MarketValueComparator implements Comparator<Area> {
    @Override
    public int compare(Area o1, Area o2) {
        if (Double.compare(o2.getMarketValuePerCapita(), o1.getMarketValuePerCapita()) >= 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
