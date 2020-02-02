package com.rdeluca118.testmd;
public class Player {

    private String name;
    private int id;
    private int wins;
    private int losses;
    private boolean hasHammer;

    public Player(String pName) {
        setName(pName);
        this.wins = 0;
        this.losses = 0;
        hasHammer = false;
    }

    // method to set the name
    public void setName(String name) {
        this.name = name;
    }

    // method to retrieve the name
    public String getName() {
        return name;
    }

    public void setId(int aid) {
        this.id = aid;
    }

    public int getId() {
        return this.id;
    }

    public boolean getHammer(){
        return this.hasHammer;
    }
    public void setHammer(boolean has){
        this.hasHammer = has;
    }
}


