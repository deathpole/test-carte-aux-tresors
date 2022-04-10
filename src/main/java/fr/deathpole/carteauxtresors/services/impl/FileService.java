package fr.deathpole.carteauxtresors.services.impl;

import fr.deathpole.carteauxtresors.model.InputLine;
import fr.deathpole.carteauxtresors.model.MapCell;
import fr.deathpole.carteauxtresors.model.Player.Player;
import fr.deathpole.carteauxtresors.model.Position;
import fr.deathpole.carteauxtresors.services.interfaces.ICommandLineService;
import fr.deathpole.carteauxtresors.services.interfaces.IFileService;
import fr.deathpole.carteauxtresors.services.interfaces.IInputLineService;
import fr.deathpole.carteauxtresors.services.interfaces.IMapService;
import fr.deathpole.carteauxtresors.utils.ConstantsHelper;
import org.apache.commons.cli.CommandLine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static fr.deathpole.carteauxtresors.utils.ConstantsHelper.DEFAULT_FILE_TO_WRITE_PATH;

public class FileService implements IFileService {


    IMapService mapService = MapService.getInstance();
    IInputLineService inputLineService =  InputLineService.getInstance();
    ICommandLineService commandLineService = CommandLineService.getInstance();

    private static FileService instance = null;

    public static FileService getInstance(){
        if (instance == null) {
            instance = new FileService();
        }

        return instance;
    }


    @Override
    public List<String> getFileLines(String filePath){

        List<String> fileLine = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(fileLine::add);
        } catch (IOException e) {
            System.out.println("Impossible de lire le fichier fourni.");
        }

        return fileLine;
    }

    @Override
    public String getFileToWritePath(CommandLine commandLine, String fileOutputArgName) {
        String path = commandLineService.getArgumentValue(commandLine, fileOutputArgName);

        if (path == null) {
            return DEFAULT_FILE_TO_WRITE_PATH;
        }

        return path;
    }

    @Override
    public void writeOutput(CommandLine commandLine, String fileOutputArgName, MapCell[][] map, List<Player> players, List<InputLine> inputLines) throws IOException {

        FileWriter writer = new FileWriter(getFileToWritePath(commandLine, fileOutputArgName));
        PrintWriter printWriter = new PrintWriter(writer);

        inputLines.forEach(inputLine -> {
            if (inputLineService.isMap(inputLine)) {
                writeMapLine(printWriter, inputLine);
            } else if (inputLineService.isMountain(inputLine)) {
                writeMountainLine(printWriter, inputLine);
            } else if (inputLineService.isTreasure(inputLine)) {
                MapCell treasureMapCell = mapService.getMapCellFromPosition(map, mapService.getPositionFromInputLine(inputLine));
                if (treasureMapCell != null) {
                    writeTreasureLine(printWriter, treasureMapCell);
                }
            }
        });

        players.forEach(player -> writePlayerLine(printWriter, player));

        printWriter.close();
    }

    private void writeMapLine(PrintWriter writer, InputLine inputLine) {
        Position dimensions = mapService.getMapDimensionsFromInputLine(inputLine);
        writer.println(ConstantsHelper.MAP_LINE_START_WITH +
                dimensions.getY() + ConstantsHelper.SPLIT_ON + dimensions.getX()
        );
    }

    private void writeMountainLine(PrintWriter writer, InputLine inputLine) {
        Position mountainPosition = mapService.getPositionFromInputLine(inputLine);
        writer.println(ConstantsHelper.MOUNTAIN_LINE_START_WITH +
                mountainPosition.getY() + ConstantsHelper.SPLIT_ON + mountainPosition.getX()
        );
    }

    private void writeTreasureLine(PrintWriter writer, MapCell mapCell) {
        if (mapCell.getTreasures() > 0) {
            writer.println(ConstantsHelper.TREASURE_LINE_START_WITH +
                    mapCell.getPosY() + ConstantsHelper.SPLIT_ON + mapCell.getPosX() + ConstantsHelper.SPLIT_ON + mapCell.getTreasures()
            );
        }
    }

    private void writePlayerLine(PrintWriter writer, Player player) {
        writer.println(ConstantsHelper.PLAYER_LINE_START_WITH +
                player.getName() + ConstantsHelper.SPLIT_ON + player.getPosition().getY() + ConstantsHelper.SPLIT_ON + player.getPosition().getX() + ConstantsHelper.SPLIT_ON +
                player.getEnumPlayerDirection().getValue() + ConstantsHelper.SPLIT_ON + player.getFoundTreasures()
        );
    }
}
