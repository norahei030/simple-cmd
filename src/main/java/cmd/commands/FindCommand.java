package cmd.commands;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * "Find" command class
 * <p/>
 * Executing the command finds all files of a given type in the current directory.
 *
 * @see SimpleCmd#getCurrentLocation()
 * @see SimpleCmd#setCurrentLocation(File)
 */
@Command(name = "find", //
         description = "Finds all files of given type in current directory", //
         mixinStandardHelpOptions = true)
public class FindCommand implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(FindCommand.class);
    @Parameters(index = "0", description = "type to match files with")
    private String type;
    @Option(names = {"-s", "--storage"}, description = "whether used storage is to be shown")
    private boolean showStorage;

    @Override
    public void run() {
        listFilesOfTypeInDirectory(SimpleCmd.getCurrentLocation());
    }

    private void listFilesOfTypeInDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (null != files) {
                String finalType = type.startsWith(".") ? type : ".".concat(type);
                LOG.info("Listing all files that end with: {}\n", finalType);
                Stream.of(files).filter(file -> file.getName().endsWith(finalType)).sorted(getFileListComparator()).forEach(this::printLine);
            }
        }
    }

    private Comparator<File> getFileListComparator() {
        return Comparator.comparing(File::getName, Comparator.reverseOrder());
    }

    private void printLine(File f) {
        if (showStorage) {
            try {
                LOG.info("{}, used storage: {} B\n", f.getAbsolutePath(), Files.size(f.toPath()));
            } catch (IOException ioe) {
                LOG.error("Error while reading File size\n");
            }
        } else {
            LOG.info("{}\n", f.getAbsolutePath());
        }
    }
}
