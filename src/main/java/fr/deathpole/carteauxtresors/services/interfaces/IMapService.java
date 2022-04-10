package fr.deathpole.carteauxtresors.services.interfaces;

import fr.deathpole.carteauxtresors.model.Position;
import fr.deathpole.carteauxtresors.model.InputLine;
import fr.deathpole.carteauxtresors.model.MapCell;

import java.util.List;

public interface IMapService {
    Position getMapDimensions(MapCell[][] map);

    MapCell getMapCellFromPosition(MapCell[][] map, Position position);

    MapCell[][] initMap(List<InputLine> inputLines);

    boolean isMapCellAccessible(MapCell mapCell);

    boolean isExistingPosition(Position mapLimits, Position adventurerNextPosition);

    Position getPositionFromInputLine(InputLine inputLine);

    Position getMapDimensionsFromInputLine(InputLine inputLine);
}
