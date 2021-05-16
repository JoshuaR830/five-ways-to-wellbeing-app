package com.joshuarichardson.fivewaystowellbeing.ui.inspire;

import java.util.Comparator;

public class NameComparator implements Comparator<InspireRecord> {
    @Override
    public int compare(InspireRecord o1, InspireRecord o2) {
        return o1.getActivityName().compareTo(o2.getActivityName());
    }
}
