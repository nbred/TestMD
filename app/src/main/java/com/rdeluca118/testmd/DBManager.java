package com.rdeluca118.testmd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase getBatabase() {
        return database;
    }

    public void close() {
        dbHelper.close();
    }

    // =============================================================================================
    // player table operations
    // =============================================================================================
    public void insert_player(String name) {
        long result;

        // if name in database skip ?
        Cursor c = database.rawQuery("select * from player where name = '" + name + "'", null);
        if (c.moveToFirst()) {
            do {
                String aname = c.getString(c.getColumnIndex("name"));
                if (name.equals(aname)) {
                    c.close();
                    return;
                }
            } while (c.moveToNext());
            c.close();
        }
        // else insert new
        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.player_name, name);
        contentValue.put(dbHelper.player_wins, 0);
        contentValue.put(dbHelper.player_losses, 0);
        result = database.insert(dbHelper.TABLE_PLAYER, null, contentValue);

    }

    public Cursor fetch_players() {
        String[] columns = new String[]{dbHelper.PLAYER_ID, dbHelper.player_name};
        Cursor cursor = database.query(dbHelper.TABLE_PLAYER, columns, null, null, null, null, "name ASC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int getPlayerId(String name) {
        String[] columns = new String[]{dbHelper.PLAYER_ID, dbHelper.player_name};
        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.player_name, name);
        Cursor cursor = database.query(dbHelper.TABLE_PLAYER, columns, "name = '" + name + "'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow("_id");
            return cursor.getInt(index);
        }
        return -1;
    }

    public Cursor getOnePLayer(int id) {
        Cursor c = null;
        String sql = String.format("select * from player where _id = %1d", id);
        c = database.rawQuery(sql, null);
        if (c.moveToFirst()) {
            return (c);
        }
        return null;
    }

    public void postWins(int _id, @NotNull Boolean w) {
        if (w) {
            database.execSQL("UPDATE player SET wins=wins + 1 WHERE _id=" + _id);
        } else {
            database.execSQL("UPDATE player SET losses=losses + 1 WHERE _id=" + _id);
        }
    }

    // =============================================================================================
    // game table operations
    // =============================================================================================
    public void insert_game(@NotNull Game theGame) {

        int[] p = theGame.getPlayersIds();
        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.game_date, theGame.getDate());
        contentValue.put(dbHelper.game_player1, p[0]);
        contentValue.put(dbHelper.game_player2, p[1]);
        contentValue.put(dbHelper.game_num_legs, theGame.getNumLegs());
        theGame.setId((int) database.insert(dbHelper.TABLE_GAME, null, contentValue));
    }

    public void updateGameWinner(int gameid, int winnerid) {
        String sql = String.format("UPDATE game SET winner=%1d  WHERE _id=%2d", winnerid, gameid);
        Log.i("DartDB", sql);
        database.execSQL(sql);
    }

    public Cursor getGameList() {
        Cursor c = null;
        String sql = "SELECT * FROM game AS a INNER JOIN player AS b ON a.winner = b._id ORDER BY date;";
        c = database.rawQuery(sql, null);
        if (c.moveToFirst()) {
            return (c);
        }
        return null;
    }

    public Cursor getOneGame(int gameID) {
        Cursor c = null;
        String sql = String.format("select * from game where _id = %1d", gameID);
        c = database.rawQuery(sql, null);
        if (c.moveToFirst()) {
            return (c);
        }
        return null;
    }

    // =============================================================================================
    // leg table operations
    // =============================================================================================

    public void insert_leg(@NotNull Leg theLeg) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.game_id, theLeg.getGameId());
        contentValue.put(dbHelper.winner_id, 0);
        theLeg.setId((int) database.insert(dbHelper.TABLE_LEG, null, contentValue));
    }

    public void update_leg(int id, int winner) {
        String sql = String.format("UPDATE leg SET winner=%1d WHERE _id=%2d", winner, id);
        Log.i("DartDB", sql);
        database.execSQL(sql);
    }

    // select * from leg where game_id = 1
    public Cursor getLegsForGame(int game) {
        Cursor c = null;
        String sql = String.format("select * from leg where game_id = %1d", game);
        c = database.rawQuery(sql, null);
        if (c.moveToFirst()) {
            return (c);
        }
        return null;
    }
    // =============================================================================================
    // turn table operations
    // =============================================================================================

    public void insert_turn(@NotNull Turn theTurn) {
        int[] darts = theTurn.getDarts();

        ContentValues contentValue = new ContentValues();
        contentValue.put(dbHelper.player_id, theTurn.getPlayerId());
        contentValue.put(dbHelper.leg_id, theTurn.getLegId());
        contentValue.put(dbHelper.dart_one, darts[0]);
        contentValue.put(dbHelper.dart_two, darts[1]);
        contentValue.put(dbHelper.dart_three, darts[2]);
        database.insert(dbHelper.TABLE_TURN, null, contentValue);
    }

    public Cursor getTurnsForLeg(int leg){
        Cursor c = null;
        String sql = String.format("select * from turn where leg_id = %1d", leg);
        c = database.rawQuery(sql, null);
        if (c.moveToFirst()) {
            return (c);
        }
        return null;
    }
    // =============================================================================================
    // =============================================================================================

    public int update(long _id, String name, String desc) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.SUBJECT, name);
//        contentValues.put(DatabaseHelper.DESC, desc);
//        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return 1; //i
    }

    public void delete(long _id) {
//        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }


}
