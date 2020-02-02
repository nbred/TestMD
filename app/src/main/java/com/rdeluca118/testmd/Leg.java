package com.rdeluca118.testmd;

public class Leg {
    private int legID;
    private int gameID;
    private int winnerID;
    private int hammer;

    public Leg(int gameid) {
        this.gameID = gameid;
        this.winnerID = -1;
    }

    public int getGameId() {
        return this.gameID;
    }

    public void setId(int aid) {
        this.legID = aid;
    }

    public int getLegId() {
        return this.legID;
    }

    public int getHammer() {
        return hammer;
    }

    public void setHammer(int hammer) {
        this.hammer = hammer;
    }

    public int getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(int winnerID) {
        this.winnerID = winnerID;
    }
}
