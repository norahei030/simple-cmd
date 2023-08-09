package cmd.commands.mkdir;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * "Make directory" command class
 * <p/>
 * Executing the command mkdir creates a directory and/or a file.
 * If the directory or file already exists, a message is given and nothing is done.
 */
@Command(
        name = "mkdir",
        description = "Create directories and files",
        mixinStandardHelpOptions = true)
public class MkdirCommand implements Runnable {

    @Parameters(description = "directory or file to be created")
    private File toBeCreated;

    @CommandLine.Option(names = {"-f", "--file"}, description = "directory or file to be created")
    private boolean createFile;


    public MkdirCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
    try {
        if(!createFile){
                Files.createDirectory(toBeCreated.toPath());
                System.out.println("directory was created successfully");
        } else {
            toBeCreated.createNewFile();
            System.out.println("file was created successfully");
        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }
}
