package fr.deathpole.carteauxtresors.services.interfaces;

import fr.deathpole.carteauxtresors.model.InputLine;
import fr.deathpole.carteauxtresors.model.MapCell;
import fr.deathpole.carteauxtresors.model.Player.Player;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.util.List;

public interface IFileService {
    List<String> getFileLines(String filePath);

    String getFileToWritePath(CommandLine commandLine, String fileOutputArgName);

    void writeOutput(CommandLine commandLine, String fileOutputArgName, MapCell[][] map, List<Player> players, List<InputLine> inputLines) throws IOException;
}
