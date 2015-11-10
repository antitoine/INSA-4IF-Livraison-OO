package com.hexanome.view;

import com.hexanome.model.TimeSlot;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class generates colors to draw arcs associating them to
 * their timeslot
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class ColorsGenerator {

    private static HashMap<Integer, Color> timeSlotColors;
    private static ColorsGenerator colorgenerator;
    private final ArrayList<Color> colors;

    /**
     * Builds a new instance of ColorsGenerator
     */
    private ColorsGenerator() {
        colors = new ArrayList<>();
        timeSlotColors = new HashMap<>();

        colors.add(Color.BLUE);
        colors.add(Color.SPRINGGREEN);
        colors.add(Color.ORANGE);
        colors.add(Color.BROWN);
        colors.add(Color.PINK);
        colors.add(Color.DARKGREEN);
        colors.add(Color.LIGHTGOLDENRODYELLOW);
        colors.add(Color.web("#89CFF0"));
        colors.add(Color.YELLOW);
        colors.add(Color.ORANGE);
        colors.add(Color.VIOLET);
        colors.add(Color.AQUAMARINE);
        colors.add(Color.CHOCOLATE);
        colors.add(Color.CHARTREUSE);
    }

    /**
     * Returns a single instance of colors generator (Singleton)
     *
     * @return a single instance of colors generator (Singleton)
     */
    public static ColorsGenerator getInstance() {
        if (colorgenerator == null) {
            colorgenerator = new ColorsGenerator();
        }
        return colorgenerator;
    }

    /**
     * Return the timeSlot associated color
     *
     * @param ts timeSlot
     * @return the timeSlot's color
     */
    public static Color getTimeSlotColor(TimeSlot ts) {
        return timeSlotColors.get(ts.getStartTime());
    }


    /**
     * Creates a color for each timeslot in the collection
     *
     * @param timeSlots Collection of timeSlots
     */
    public void createColors(Collection<TimeSlot> timeSlots) {
        timeSlotColors.clear();

        int i = 0;
        for (TimeSlot ts : timeSlots) {
            timeSlotColors.put(ts.getStartTime(), colors.get((i % colors.size())));
            i++;
        }
    }
}
