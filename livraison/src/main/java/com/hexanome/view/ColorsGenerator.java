package com.hexanome.view;

import com.hexanome.model.TimeSlot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javafx.scene.paint.Color;

public class ColorsGenerator {

    private final ArrayList<Color> colors;
    private static HashMap<TimeSlot, Color> timeSlotColors;
    private static ColorsGenerator colorgenerator;

    public static ColorsGenerator getInstance(Collection<TimeSlot> timeSlots) {
        if (colorgenerator == null) {
            colorgenerator = new ColorsGenerator(timeSlots);
        }
        return colorgenerator;
    }

    /**
     * Allow to generate colors for timeSlots
     *
     * @param timeSlots
     */
    private ColorsGenerator(Collection<TimeSlot> timeSlots) {
        colors = new ArrayList<>();
        timeSlotColors = new HashMap<>();

        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.web("#89CFF0"));
        colors.add(Color.ORANGE);
        colors.add(Color.BROWN);
        colors.add(Color.GREEN);
        colors.add(Color.VIOLET);
        colors.add(Color.PINK);
        colors.add(Color.LIGHTGOLDENRODYELLOW);
        colors.add(Color.AQUAMARINE);
        colors.add(Color.CHOCOLATE);
        colors.add(Color.CHARTREUSE);

        int i = 0;
        for (TimeSlot ts : timeSlots) {
            timeSlotColors.put(ts, colors.get((i % colors.size())));
            i++;
        }
    }

    /**
     * Return the timeSlot color
     *
     * @param ts
     * @return the timeSlot color
     */
    public static Color getTimeSlotColor(TimeSlot ts) {
        return timeSlotColors.get(ts);
    }

}
