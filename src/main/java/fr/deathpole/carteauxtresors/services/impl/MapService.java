package fr.deathpole.carteauxtresors.services.impl;

import fr.deathpole.carteauxtresors.model.Position;
import fr.deathpole.carteauxtresors.model.InputLine;
import fr.deathpole.carteauxtresors.model.MapCell;
import fr.deathpole.carteauxtresors.services.interfaces.IInputLineService;
import fr.deathpole.carteauxtresors.services.interfaces.IMapService;
import fr.deathpole.carteauxtresors.utils.ConstantsHelper;

import java.util.List;
import java.util.Optional;

public class MapService implements IMapService {

    private IInputLineService inputLineService = InputLineService.getInstance();

    private static MapService instance = null;

    public static MapService getInstance(){
        if (instance == null) {
            instance = new MapService();
        }

        return instance;
    }

    @Override
    public MapCell[][] initMap(List<InputLine> inputLines) {

        Position mapDimensions = getMapDimensionsFromAllInputLines(inputLines);
        MapCell[][] map = new MapCell[mapDimensions.getX()][mapDimensions.getY()];
        setupMap(map, mapDimensions);
        setupMountains(map, inputLines);
        setupTreasures(map, inputLines);
        return map;
    }

    @Override
    public Position getMapDimensions(MapCell[][] map) {
        Position position = new Position();
        position.setX(map.length);
        position.setY(map[0].length);

        return position;
    }

    @Override
    public MapCell getMapCellFromPosition(MapCell[][] map, Position position)  {
        if (isExistingPosition(getMapDimensions(map), position)) {
            return map[position.getX()][position.getY()];
        }
        return null;
    }

    @Override
    public boolean isMapCellAccessible(MapCell mapCell) {
        return (!mapCell.isMountain() && mapCell.getPlayer() == null);
    }

    @Override
    public boolean isExistingPosition(Position mapLimits, Position adventurerNextPosition) {
        return (
                mapLimits.getX() > adventurerNextPosition.getX() &&
                        mapLimits.getY() > adventurerNextPosition.getY() &&
                        adventurerNextPosition.getX() >= 0 &&
                        adventurerNextPosition.getY() >= 0
        );
    }

    @Override
    public Position getPositionFromInputLine(InputLine inputLine) {
        Position position = new Position();

        String[] data = inputLine.getRawLine().split(ConstantsHelper.SPLIT_ON);
        position.setX(Integer.parseInt(data[2]));
        position.setY(Integer.parseInt(data[1]));

        return position;
    }

    @Override
    public Position getMapDimensionsFromInputLine(InputLine inputLine){
        return getPositionFromInputLine(inputLine);
    }

    private Position getMapDimensionsFromAllInputLines(List<InputLine> inputLines){

        for (InputLine inputLine : inputLines) {
            if (inputLineService.isMap(inputLine)) {
                return getPositionFromInputLine(inputLine);
            }
        }
        System.out.println("La taille de la carte n'est pas définie.");
        return null;
    }

    private void setupMap(MapCell[][] map, Position dimensions) {

        for (int i = 0; i < dimensions.getX(); i++) {
            for (int j = 0; j < dimensions.getY(); j++) {
                map[i][j] = new MapCell(i, j);
            }
        }
    }

    private void setupMountains(MapCell[][] map, List<InputLine> inputLines) {
        Optional<List<InputLine>> mountainsOptional = inputLineService.getMountainsFromAllLines(inputLines);
        mountainsOptional.ifPresent(o -> placeMountains(map, o));
    }

    private void placeMountains(MapCell[][] map, List<InputLine> mountainsInputLines) {
        mountainsInputLines
                .stream()
                .map(this::getPositionFromInputLine)
                .forEach(mountainPosition -> map[mountainPosition.getX()][mountainPosition.getY()].setMountain(true));
    }

    private void setupTreasures(MapCell[][] map, List<InputLine> inputLines) {
        Optional<List<InputLine>> treasuresOptional = inputLineService.getTreasuresFromAllLines(inputLines);
        treasuresOptional.ifPresent(o -> placeTreasures(map, o));
    }

    private void placeTreasures(MapCell[][] map, List<InputLine> treasuresInputLines) {
        treasuresInputLines.forEach(inputLine -> {
            Position treasurePosition = getPositionFromInputLine(inputLine);
            MapCell mapCell = map[treasurePosition.getX()][treasurePosition.getY()];
            if (!mapCell.isMountain()) {
                mapCell.setTreasures(inputLineService.getTreasureNumberFromInputLine(inputLine.getRawLine()));
            }
        });
    }

}
