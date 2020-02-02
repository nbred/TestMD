package com.rdeluca118.testmd.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

import com.rdeluca118.testmd.DBManager;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP =
            new HashMap<String, DummyItem>();

    public static void loadData(Cursor c) {
        assert c instanceof Cursor;

        int i = 1;
        c.moveToFirst();
        do{
            addItem(new DummyItem(String.valueOf(c.getInt(0)),c.getString(1), c.getString(7)));
            i++;
        }while( c.moveToNext());
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
