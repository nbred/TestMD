package com.rdeluca118.testmd;

public class Turn {
    private int turnID;
    private int playerID;
    private int legID;
    private int dartOne;
    private int dartTwo;
    private int dartThree;

    public Turn() {
        this.turnID = 0;
    }

    public Turn(int pID, int lid) {
        this.playerID = pID;
        this.legID = lid;
    }

    public int getPlayerId() {
        return this.playerID;
    }

    public void setPlayerID(int id) {
        this.playerID = id;
    }

    public int getLegId() {
        return this.legID;
    }

    public void setLegId(int leg) {
        this.legID = leg;
    }

    public int[] getDarts() {
        int[] d = new int[3];
        d[0] = this.dartOne;
        d[1] = this.dartTwo;
        d[2] = this.dartThree;
        return d;
    }

    public void setDart(int theDart, int valu) {
        switch (theDart) {
            case 1:
                dartOne = valu;
                break;
            case 2:
                dartTwo = valu;
                break;
            case 3:
                dartThree = valu;
        }
    }

    public int getTurnID() {
        return turnID;
    }

    public void setTurnID(int turnID) {
        this.turnID = turnID;
    }
}
