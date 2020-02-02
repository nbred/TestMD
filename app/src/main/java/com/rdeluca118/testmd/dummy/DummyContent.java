package com.rdeluca118.testmd.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rdeluca118.testmd.DBManager;
/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {
    private DBManager dbm;
    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP =
            new HashMap<String, DummyItem>();
//    SELECT date,name
//    FROM game AS a
//    INNER JOIN player AS b
//    ON a.winner = b._id
//    ORDER BY date;
    static {
        // Add 3 sample items.
        addItem(new DummyItem("1", "May 12 2019",
                "Rich"));
        addItem(new DummyItem("2", "June 22 2019",
                "Rich"));
        addItem(new DummyItem("3", "july 24 2019",
                "Babe"));
    }


    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String gameDate;
        public final String winner;

        public DummyItem(String id, String date, String winner) {
            this.id = id;
            this.gameDate = date;
            this.winner = winner;
        }

        @Override
        public String toString() {
            return gameDate;
        }
    }
}
