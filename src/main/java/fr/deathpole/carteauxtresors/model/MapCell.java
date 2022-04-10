package fr.deathpole.carteauxtresors.model;


import fr.deathpole.carteauxtresors.model.Player.Player;


public class MapCell {

    private int posX;
    private int posY;
    private Boolean isMountain;
    private int treasures;
    private Player player;

    public MapCell() {
    }

    public MapCell(int x, int y) {
        this.posX = x;
        this.posY = y;
        this.isMountain = false;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Boolean isMountain() {
        return isMountain;
    }

    public void setMountain(Boolean mountain) {
        isMountain = mountain;
    }

    public int getTreasures() {
        return treasures;
    }

    public void setTreasures(int treasures) {
        this.treasures = treasures;
    }

    public Boolean getMountain() {
        return isMountain;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
