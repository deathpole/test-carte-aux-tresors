package fr.deathpole.carteauxtresors.services.interfaces;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public interface ICommandLineService {
    CommandLine getCommandLine(String[] args) throws ParseException;

    String getArgumentValue(CommandLine commandLine, String argName);
}
