package fr.deathpole.carteauxtresors.services.interfaces;

import fr.deathpole.carteauxtresors.enums.EnumPlayerAction;
import fr.deathpole.carteauxtresors.enums.EnumPlayerDirection;
import fr.deathpole.carteauxtresors.model.InputLine;
import fr.deathpole.carteauxtresors.model.Player.Player;
import fr.deathpole.carteauxtresors.model.Position;

import java.util.List;
import java.util.Optional;

public interface IInputLineService {
    List<InputLine> getAllInputLines(List<String> sLines);

    boolean isMap(InputLine inputLine);

    boolean isMountain(InputLine inputLine);

    boolean isTreasure(InputLine inputLine);

    boolean isPlayer(InputLine inputLine);

    Player getPlayerFromInputLine(InputLine inputLine);

    Position getPositionFromRawLine(String line);

    int getTreasureNumberFromInputLine(String line);

    Optional<List<InputLine>> getMountainsFromAllLines(List<InputLine> inputLines);

    Optional<List<InputLine>> getTreasuresFromAllLines(List<InputLine> inputLines);

    EnumPlayerAction getPlayerActionFromChar(char c);

    EnumPlayerDirection getPlayerDirectionFromString(String s);
}
