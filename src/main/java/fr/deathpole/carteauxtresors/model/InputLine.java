package fr.deathpole.carteauxtresors.model;

import fr.deathpole.carteauxtresors.enums.EnumInputLineType;

public class InputLine {

    private String rawLine;
    private EnumInputLineType enumInputLineType;

    public InputLine() {
    }

    public InputLine(String rawLine, EnumInputLineType enumInputLineType) {
        this.rawLine = rawLine;
        this.enumInputLineType = enumInputLineType;
    }

    public String getRawLine() {
        return rawLine;
    }

    public void setRawLine(String rawLine) {
        this.rawLine = rawLine;
    }

    public EnumInputLineType getEnumInputLineType() {
        return enumInputLineType;
    }

    public void setEnumInputLineType(EnumInputLineType enumInputLineType) {
        this.enumInputLineType = enumInputLineType;
    }
}
