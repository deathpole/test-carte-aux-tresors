package fr.deathpole.carteauxtresors.model.Player;

import fr.deathpole.carteauxtresors.enums.EnumPlayerDirection;
import fr.deathpole.carteauxtresors.model.Position;

import java.util.List;

public class Player {

    private String name;
    private Position position;
    private EnumPlayerDirection enumPlayerDirection;
    private List<Movement> movements;
    private int foundTreasures;

    public Player() {
    }

    public Player(String name, Position position, EnumPlayerDirection enumPlayerDirection, List<Movement> movements, int foundTreasures) {
        this.name = name;
        this.position = position;
        this.enumPlayerDirection = enumPlayerDirection;
        this.movements = movements;
        this.foundTreasures = foundTreasures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public EnumPlayerDirection getEnumPlayerDirection() {
        return enumPlayerDirection;
    }

    public void setEnumPlayerDirection(EnumPlayerDirection enumPlayerDirection) {
        this.enumPlayerDirection = enumPlayerDirection;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public int getFoundTreasures() {
        return foundTreasures;
    }

    public void setFoundTreasures(int foundTreasures) {
        this.foundTreasures = foundTreasures;
    }
}
