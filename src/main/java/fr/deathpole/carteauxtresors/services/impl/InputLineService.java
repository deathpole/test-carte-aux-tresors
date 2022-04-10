package fr.deathpole.carteauxtresors.services.impl;

import fr.deathpole.carteauxtresors.enums.EnumInputLineType;
import fr.deathpole.carteauxtresors.enums.EnumPlayerAction;
import fr.deathpole.carteauxtresors.enums.EnumPlayerDirection;
import fr.deathpole.carteauxtresors.model.InputLine;
import fr.deathpole.carteauxtresors.model.Player.Movement;
import fr.deathpole.carteauxtresors.model.Player.Player;
import fr.deathpole.carteauxtresors.model.Position;
import fr.deathpole.carteauxtresors.services.interfaces.IInputLineService;
import fr.deathpole.carteauxtresors.services.interfaces.IMapService;
import fr.deathpole.carteauxtresors.utils.ConstantsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class InputLineService implements IInputLineService {


    private static InputLineService instance = null;

    public InputLineService() {
    }

    public static InputLineService getInstance() {
        if (instance == null) {
            instance = new InputLineService();
        }

        return instance;
    }

    @Override
    public List<InputLine> getAllInputLines(List<String> rawLines) {

        List<InputLine> inputLines = new ArrayList<>();
        for (String rawLine : rawLines) {
            inputLines.add(getInputLineFromRaw(rawLine));
        }

        inputLines.removeIf(Objects::isNull);
        return inputLines;
    }

    @Override
    public boolean isMap(InputLine inputLine) {
        EnumInputLineType enumInputLineType = inputLine.getEnumInputLineType();
        if (enumInputLineType == null) {
            return false;
        } else {
            return enumInputLineType.equals(EnumInputLineType.MAP);
        }
    }

    @Override
    public boolean isMountain(InputLine inputLine) {
        EnumInputLineType enumInputLineType = inputLine.getEnumInputLineType();
        if (enumInputLineType == null) {
            return false;
        } else {
            return inputLine.getEnumInputLineType().equals(EnumInputLineType.MOUNTAIN);
        }
    }

    @Override
    public boolean isTreasure(InputLine inputLine) {
        EnumInputLineType enumInputLineType = inputLine.getEnumInputLineType();
        if (enumInputLineType == null) {
            return false;
        } else {
            return inputLine.getEnumInputLineType().equals(EnumInputLineType.TREASURE);
        }
    }

    @Override
    public boolean isPlayer(InputLine inputLine) {
        EnumInputLineType enumInputLineType = inputLine.getEnumInputLineType();
        if (enumInputLineType == null) {
            return false;
        } else {
            return inputLine.getEnumInputLineType().equals(EnumInputLineType.PLAYER);
        }
    }

    @Override
    public Player getPlayerFromInputLine(InputLine inputLine) {
        String[] split = inputLine.getRawLine().split(ConstantsHelper.SPLIT_ON);

        List<Movement> movements = new ArrayList<>();

        for (char c : split[5].toCharArray()) {
            movements.add(new Movement(getPlayerActionFromChar(c), false));
        }

        Position adventurerPosition = getPlayerPositionFromRawLine(inputLine.getRawLine());

        return new Player(
                split[1],
                adventurerPosition,
                getPlayerDirectionFromString(split[4]),
                movements,
                0
        );
    }

    @Override
    public Position getPositionFromRawLine(String line) {
        String[] split = line.split(ConstantsHelper.SPLIT_ON);
        return new Position(Integer.parseInt(split[2]), Integer.parseInt(split[1]));
    }

    @Override
    public int getTreasureNumberFromInputLine(String line) {
        String[] split = line.split(ConstantsHelper.SPLIT_ON);
        return Integer.parseInt(split[3]);
    }

    @Override
    public Optional<List<InputLine>> getMountainsFromAllLines(List<InputLine> inputLines) {
        List<InputLine> mountains = inputLines.stream()
                .filter(this::isMountain)
                .collect(Collectors.toList());

        return (mountains.isEmpty()) ? Optional.empty() : Optional.of(mountains);
    }

    @Override
    public Optional<List<InputLine>> getTreasuresFromAllLines(List<InputLine> inputLines) {
        List<InputLine> treasures = inputLines.stream()
                .filter(this::isTreasure)
                .collect(Collectors.toList());

        return (treasures.isEmpty()) ? Optional.empty() : Optional.of(treasures);
    }

    @Override
    public EnumPlayerAction getPlayerActionFromChar(char c) {
        switch (c) {
            case 'A':
                return EnumPlayerAction.MOVE_FORWARD;
            case 'G':
                return EnumPlayerAction.TURN_LEFT;
            case 'D':
                return EnumPlayerAction.TURN_RIGHT;
            default:
                return null;
        }
    }

    @Override
    public EnumPlayerDirection getPlayerDirectionFromString(String s) {
        switch (s) {
            case "N":
                return EnumPlayerDirection.NORTH;
            case "S":
                return EnumPlayerDirection.SOUTH;
            case "E":
                return EnumPlayerDirection.EAST;
            case "O":
                return EnumPlayerDirection.WEST;
            default:
                return null;
        }
    }

    private InputLine getInputLineFromRaw(String line) {
        InputLine inputLineType = new InputLine();
        inputLineType.setRawLine(line);

        switch (line.substring(0, 4)) {
            case ConstantsHelper.MAP_LINE_START_WITH:
                inputLineType.setEnumInputLineType(EnumInputLineType.MAP);
                break;
            case ConstantsHelper.MOUNTAIN_LINE_START_WITH:
                inputLineType.setEnumInputLineType(EnumInputLineType.MOUNTAIN);
                break;
            case ConstantsHelper.TREASURE_LINE_START_WITH:
                inputLineType.setEnumInputLineType(EnumInputLineType.TREASURE);
                break;
            case ConstantsHelper.PLAYER_LINE_START_WITH:
                inputLineType.setEnumInputLineType(EnumInputLineType.PLAYER);
                break;
            case ConstantsHelper.COMMENT_LINE_START_WITH:
                return null;
            default:
                System.out.println("Type de ligne inconnu : " + line);
        }

        return inputLineType;
    }

    private Position getPlayerPositionFromRawLine(String line) {
        String[] split = line.split(ConstantsHelper.SPLIT_ON);
        return new Position(Integer.parseInt(split[3]), Integer.parseInt(split[2]));
    }
}
