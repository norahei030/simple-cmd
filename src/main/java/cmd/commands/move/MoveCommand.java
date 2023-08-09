package cmd.commands.move;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * "Move" command class
 * <p/>
 * Executing the command moves a file or directory to a different location (and/or renames it)..
 *
 * @see SimpleCmd#getCurrentLocation()
 * @see SimpleCmd#setCurrentLocation(File)
 */
@Command(name = "move", //
         description = "Moves files and directories to different locations (and/or renames them)", //
         mixinStandardHelpOptions = true)
public class MoveCommand implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(MoveCommand.class);
    @Parameters(index = "0", description = "path of the file to copy")
    private Path source;
    @Parameters(index = "1", description = "path to move file to")
    private Path target;

    @Override
    public void run() {
        File sourceFile = source.toFile();
        File targetFile = target.toFile();
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            LOG.info("{} has been moved to {} \n", sourceFile, targetFile);
        } catch (DirectoryNotEmptyException dnee) {
            final String sourceFileName = sourceFile.getName();
            File targetFinalFile;
            final String targetWithSourceFilename = target.toAbsolutePath().toString().concat(FileSystems.getDefault().getSeparator() + sourceFileName);
            targetFinalFile = new File(targetWithSourceFilename);
            LOG.error("Please only move and rename into non existent or empty directories. Otherwise adjust the given command to: move {} {}\n",
                      source,
                      targetFinalFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
