package edu.upenn.cit594.utils;

import edu.upenn.cit594.data.Area;

import java.util.Comparator;

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
