package fr.deathpole.carteauxtresors;

import fr.deathpole.carteauxtresors.model.InputLine;
import fr.deathpole.carteauxtresors.model.MapCell;
import fr.deathpole.carteauxtresors.model.Player.Player;
import fr.deathpole.carteauxtresors.services.impl.*;
import fr.deathpole.carteauxtresors.services.interfaces.*;
import fr.deathpole.carteauxtresors.utils.ConstantsHelper;
import org.apache.commons.cli.CommandLine;

import java.util.List;

public class Main {


    private static ICommandLineService commandLineService = CommandLineService.getInstance();
    private static IPlayerService playerService = PlayerService.getInstance();
    private static IInputLineService inputLineService = InputLineService.getInstance();
    private static IMapService mapService = MapService.getInstance();
    private static IFileService fileService = FileService.getInstance();

    public static void main(String[] args) throws Exception {

        CommandLine commandLine = commandLineService.getCommandLine(args);

        String fileToReadPath = commandLineService.getArgumentValue(commandLine, ConstantsHelper.INPUT_FILE_ARG);

        List<InputLine> linesTypes = inputLineService.getAllInputLines(fileService.getFileLines(fileToReadPath));

        MapCell[][] map = mapService.initMap(linesTypes);

        List<Player> players = playerService.initPlayers(linesTypes);

        playerService.initPlayersPositions(map, players);

        playerService.processPlayersMovements(map, players, 0);

        fileService.writeOutput(
                commandLine,
                ConstantsHelper.OUTPUT_FILE_ARG,
                map,
                players,
                linesTypes);

    }


}