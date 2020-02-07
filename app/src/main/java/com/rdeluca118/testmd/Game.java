package com.rdeluca118.testmd;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Game {

    private int game_id;
    private String GameDate;
    private Player Player1;
    private Player Player2;
    private int legs;
    private int winnerId;

    public Game(Player p1, Player p2, int maxlegs) {
        Date Game_date;
        Player1 = p1;
        Player2 = p2;

        legs = maxlegs;

        Game_date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        GameDate = sdf.format(Game_date);
        winnerId = 0;
    }

    public String getDate() {
        return GameDate;
    }

    public void setDate(String d) {
        this.GameDate = d;
    }

    public int[] getPlayersIds() {
        int[] p = {Player1.getId(), Player2.getId()};
        return p;
    }

    public int getNumLegs() {
        return legs;
    }

    public void setMaxLegs(int m) {
        this.legs = m;
    }

    public void setId(int num) {
        game_id = num;
    }

    public int getId() {
        return game_id;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public Player getPlayer1() {
        return this.Player1;
    }

    public void setPlayer1(Player p) {
        this.Player1 = p;
    }

    public Player getPlayer2() {
        return this.Player2;
    }

    public void setPlayer2(Player p) {
        this.Player2 = p;
    }
}
