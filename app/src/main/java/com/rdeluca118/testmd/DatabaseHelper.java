package com.rdeluca118.testmd;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Names
    public static final String TABLE_GAME = "game";
    public static final String TABLE_PLAYER = "player";
    public static final String TABLE_LEG = "leg";
    public static final String TABLE_TURN = "turn";

    // com.rdeluca118.testmd.dummy.DBManager.Game Table columns
    public static final String GAME_ID = "_id";
    public static final String game_date = "date";
    public static final String game_player1 = "player1_id";
    public static final String game_player2 = "player2_id";
    public static final String game_num_legs = "max_legs";
    public static final String game_winner = "winner";

    // Creating game query
    private static final String CREATE_GAME_TABLE = "create table " + TABLE_GAME + "(" + GAME_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " + game_date + " DATETIME NOT NULL, " + game_player1 + " INTEGER NOT NULL, "  + game_player2 + " INTEGER NOT NULL, " + game_num_legs + " INTEGER, " + game_winner + " INTEGER);";

    private static final String CREATE_GAME_INDEX ="CREATE INDEX 'date_idx' ON '" + TABLE_GAME +"' ('"+ game_date + "' ASC)";

    // Player Table columns

    public static final String PLAYER_ID = "_id";
    public static final String player_name = "name";
    public static final String player_wins = "wins";
    public static final String player_losses = "losses";

    // creating player query
    private static final String CREATE_PLAYER_TABLE = "create table " + TABLE_PLAYER + "(" + PLAYER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " + player_name + " VARCHAR(20) NOT NULL UNIQUE, " + player_wins + " INTEGER, " + player_losses + " INTEGER);";

    // Leg Table columns
    public static final String LEG_ID = "_id";
    public static final String game_id = "game_id";
    public static final String winner_id = "winner";


    // creating leg query
    private static final String CREATE_LEG_TABLE = "create table " + TABLE_LEG + "(" + LEG_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " + game_id + " INTEGER NOT NULL, " + winner_id + " INTEGER, FOREIGN KEY('game_id') REFERENCES 'game'('_id'));";

    // Turn Table columns
    public static final String TURN_ID = "_id";
    public static final String player_id = "player_id";
    public static final String leg_id = "leg_id";
    public static final String dart_one = "dart_1";
    public static final String dart_two = "dart_2";
    public static final String dart_three = "dart_3";

    // creating leg query
    private static final String CREATE_TURN_TABLE = "create table " + TABLE_TURN + "(" + TURN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " + player_id + " INTEGER NOT NULL, " + leg_id + " INTEGER NOT NULL, " + dart_one + " INTEGER, " + dart_two + " INTEGER, " + dart_three + " INTEGER, FOREIGN KEY('leg_id') REFERENCES 'leg'('_id'), FOREIGN KEY('player_id') REFERENCES 'player'('_id'));";

    // Database Information
    static final String DB_NAME = "darts118.db";
    // database version
    static final int DB_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        // game table
        db.execSQL(CREATE_GAME_TABLE);
        db.execSQL(CREATE_GAME_INDEX);
        // player table
        db.execSQL(CREATE_PLAYER_TABLE);
        // leg table
        db.execSQL(CREATE_LEG_TABLE);
        // turn table
        db.execSQL(CREATE_TURN_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP INDEX IF EXISTS date_idx");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TURN);

        onCreate(db);
    }
}
