package fr.deathpole.carteauxtresors.services.interfaces;

import fr.deathpole.carteauxtresors.model.InputLine;
import fr.deathpole.carteauxtresors.model.MapCell;
import fr.deathpole.carteauxtresors.model.Player.Player;

import java.util.List;

public interface IPlayerService {
    List<Player> initPlayers(List<InputLine> inputLines);

    void initPlayersPositions(MapCell[][] map, List<Player> players);

    void processPlayersMovements(MapCell[][] map, List<Player> players, int counter);
}
