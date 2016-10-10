package com.gleides.teachingfourpattern.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PatternContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<PatternItem> ITEMS = new ArrayList<PatternItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, PatternItem> ITEM_MAP = new HashMap<String, PatternItem>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPatternItem(i));
        }
    }

    private static void addItem(PatternItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static PatternItem createPatternItem(int position) {
        switch (position){
            case 1:
                return new PatternItem(String.valueOf(position), "-Singleton");
            case 2:
                return new PatternItem(String.valueOf(position), "-Factory");
            case 3:
                return new PatternItem(String.valueOf(position), "-Abstract Factory");
            case 4:
                return new PatternItem(String.valueOf(position), "-Builder");
            default:
                return new PatternItem(String.valueOf(position), "-Prototype");
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class PatternItem {
        public final String id;
        public final String content;

        public PatternItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
