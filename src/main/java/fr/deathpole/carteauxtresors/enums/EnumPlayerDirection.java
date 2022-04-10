package fr.deathpole.carteauxtresors.enums;

public enum EnumPlayerDirection {
    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("O");

    private String value;

    EnumPlayerDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
