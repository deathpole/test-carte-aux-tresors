package fr.deathpole.carteauxtresors.services.impl;

import fr.deathpole.carteauxtresors.enums.EnumPlayerAction;
import fr.deathpole.carteauxtresors.enums.EnumPlayerDirection;
import fr.deathpole.carteauxtresors.model.InputLine;
import fr.deathpole.carteauxtresors.model.MapCell;
import fr.deathpole.carteauxtresors.model.Player.Movement;
import fr.deathpole.carteauxtresors.model.Player.Player;
import fr.deathpole.carteauxtresors.model.Position;
import fr.deathpole.carteauxtresors.services.interfaces.IInputLineService;
import fr.deathpole.carteauxtresors.services.interfaces.IMapService;
import fr.deathpole.carteauxtresors.services.interfaces.IPlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerService implements IPlayerService {

    private IMapService mapService = MapService.getInstance();
    private IInputLineService inputLineService = InputLineService.getInstance();

    private static PlayerService instance = null;

    public static PlayerService getInstance(){
        if (instance == null) {
            instance = new PlayerService();
        }

        return instance;
    }

    @Override
    public List<Player> initPlayers(List<InputLine> inputLines) {
        List<Player> players = new ArrayList<>();

        for (InputLine inputLine : inputLines) {
            if (inputLineService.isPlayer(inputLine)) {
                Player player = inputLineService.getPlayerFromInputLine(inputLine);
                players.add(player);
            }
        }

        if (players.isEmpty()) {
            System.out.println("Aucun joueur défini dans le fichier en entrée !");
            return null;
        } else {
            return players;
        }
    }

    @Override
    public void initPlayersPositions(MapCell[][] map, List<Player> players){

        for (Player player : players) {
            MapCell mapCell = map[player.getPosition().getX()][player.getPosition().getY()];
            if (mapService.isMapCellAccessible(mapCell)) {
                mapCell.setPlayer(player);
            } else {
                System.out.println("Impossible de placer le joueur " + player.getName() + ".");
            }
        }
    }

    @Override
    public void processPlayersMovements(MapCell[][] map, List<Player> players, int counter) {

        List<Movement> allMovements = extractPlayersMovements(players);

        if (isAnyMovementStillLeft(allMovements)) {
            players.stream().filter(player -> counter < player.getMovements().size() && !player.getMovements().get(counter).isExecuted()).forEach(player -> {
                Movement movement = player.getMovements().get(counter);
                executePlayerMovement(map, player, movement);
                movement.setExecuted(true);
            });
            processPlayersMovements(map, players, counter + 1);
        }
    }


    private void executePlayerMovement(MapCell[][] map, Player player, Movement movement) {
        if (movement.getAction().equals(EnumPlayerAction.MOVE_FORWARD)) {
            movePlayerForward(map, player);
        } else if (movement.getAction().equals(EnumPlayerAction.TURN_LEFT)) {
            turnPlayerToTheLeft(player);
        } else if (movement.getAction().equals(EnumPlayerAction.TURN_RIGHT)) {
            turnPlayerToTheRight(player);
        }
    }

    private void movePlayerForward(MapCell[][] map, Player player) {
        Position adventurerNextPosition = getNextPlayerPosition(player);

        if (mapService.isExistingPosition(mapService.getMapDimensions(map), adventurerNextPosition)) {
            MapCell currentPlayerMapCell = map[player.getPosition().getX()][player.getPosition().getY()];
            MapCell nextPlayerMapCell = map[adventurerNextPosition.getX()][adventurerNextPosition.getY()];

            if (mapService.isMapCellAccessible(nextPlayerMapCell)) {
                currentPlayerMapCell.setPlayer(null);
                player.setPosition(adventurerNextPosition);
                nextPlayerMapCell.setPlayer(player);
                System.out.println(player.getName() + " avance d'une case.");
                if (nextPlayerMapCell.getTreasures() > 0) {
                    player.setFoundTreasures(player.getFoundTreasures() + 1);
                    nextPlayerMapCell.setTreasures(nextPlayerMapCell.getTreasures() - 1);
                    System.out.println(player.getName() + " a trouvé un nouveau trésor. Nombre de trésor(s) trouvé(s) : " + player.getFoundTreasures());
                }
            } else {
                System.out.println(player.getName() + " ne peut pas avancer dans cette direction.");
            }
        } else {
            System.out.println(player.getName() + " ne peut pas avancer dans cette direction. Le bord de la carte a été ateint.");
        }
    }

    private void turnPlayerToTheLeft(Player player) {
        switch (player.getEnumPlayerDirection()) {
            case NORTH:
                System.out.println(player.getName() + " tourne à gauche et regarde vers l'ouest.");
                player.setEnumPlayerDirection(EnumPlayerDirection.WEST);
                break;
            case SOUTH:
                System.out.println(player.getName() + " tourne à gauche et regarde vers l'est.");
                player.setEnumPlayerDirection(EnumPlayerDirection.EAST);
                break;
            case EAST:
                System.out.println(player.getName() + " tourne à gauche et regarde vers le nord.");
                player.setEnumPlayerDirection(EnumPlayerDirection.NORTH);
                break;
            case WEST:
                System.out.println(player.getName() + " tourne à gauche et regarde vers le sud.");
                player.setEnumPlayerDirection(EnumPlayerDirection.SOUTH);
                break;
        }
    }

    private void turnPlayerToTheRight(Player player) {
        switch (player.getEnumPlayerDirection()) {
            case NORTH:
                System.out.println(player.getName() + " tourne à droite et regarde vers l'est");
                player.setEnumPlayerDirection(EnumPlayerDirection.EAST);
                break;
            case SOUTH:
                System.out.println(player.getName() + " tourne à droite et regarde vers l'oeust");
                player.setEnumPlayerDirection(EnumPlayerDirection.WEST);
                break;
            case EAST:
                System.out.println(player.getName() + " tourne à droite et regarde vers le sud");
                player.setEnumPlayerDirection(EnumPlayerDirection.SOUTH);
                break;
            case WEST:
                System.out.println(player.getName() + " tourne à droite et regarde vers le nord");
                player.setEnumPlayerDirection(EnumPlayerDirection.NORTH);
                break;
        }
    }

    private Position getNextPlayerPosition(Player player) {
        Position nextPosition = new Position();
        Position adventurerPosition = player.getPosition();
        switch (player.getEnumPlayerDirection()) {
            case NORTH:
                nextPosition.setX(adventurerPosition.getX() - 1);
                nextPosition.setY(adventurerPosition.getY());
                break;
            case SOUTH:
                nextPosition.setX(adventurerPosition.getX() + 1);
                nextPosition.setY(adventurerPosition.getY());
                break;
            case EAST:
                nextPosition.setX(adventurerPosition.getX());
                nextPosition.setY(adventurerPosition.getY() + 1);
                break;
            case WEST:
                nextPosition.setX(adventurerPosition.getX());
                nextPosition.setY(adventurerPosition.getY() - 1);
                break;
        }
        return nextPosition;
    }

    private List<Movement> extractPlayersMovements(List<Player> players) {
        return players.stream()
                .flatMap(adventurer -> adventurer.getMovements().stream())
                .collect(Collectors.toList());
    }

    private boolean isAnyMovementStillLeft(List<Movement> movements) {
        return movements.stream().anyMatch(m -> !m.isExecuted());
    }
}
