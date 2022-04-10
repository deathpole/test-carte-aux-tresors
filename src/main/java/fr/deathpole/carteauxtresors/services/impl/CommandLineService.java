package fr.deathpole.carteauxtresors.services.impl;


import fr.deathpole.carteauxtresors.services.interfaces.ICommandLineService;
import org.apache.commons.cli.*;

import static fr.deathpole.carteauxtresors.utils.ConstantsHelper.INPUT_FILE_ARG;
import static fr.deathpole.carteauxtresors.utils.ConstantsHelper.OUTPUT_FILE_ARG;

public class CommandLineService implements ICommandLineService {

    private static CommandLineService instance = null;

    public static CommandLineService getInstance(){
        if (instance == null) {
            instance = new CommandLineService();
        }

        return instance;
    }

    @Override
    public CommandLine getCommandLine(String[] args) throws ParseException {

        Options options = new Options();
        Option inputFileArgument = Option.builder().longOpt(INPUT_FILE_ARG).optionalArg(false).argName(INPUT_FILE_ARG).hasArg(true).required(true).build();
        Option outputFileArgument = Option.builder().longOpt(OUTPUT_FILE_ARG).optionalArg(true).argName(OUTPUT_FILE_ARG).hasArg(true).required(false).build();

        options.addOption(inputFileArgument);
        options.addOption(outputFileArgument);

        return parseArguments(options, args);
    }

    @Override
    public String getArgumentValue(CommandLine commandLine, String argName) {
        return commandLine.getOptionValue(argName);
    }

    private CommandLine parseArguments(Options options, String[] args) throws ParseException {

        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        try {
            return commandLineParser.parse(options, args);
        } catch (ParseException pe) {
            helpFormatter.printHelp("java", options);
            System.out.println("Les arguments saisis sont incorrects.");
            pe.printStackTrace();
            System.exit(0);
            throw new ParseException(pe.getMessage());
        }
    }
}
