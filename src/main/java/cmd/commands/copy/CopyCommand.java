package cmd.commands.copy;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * "Copy File" command class
 * <p/>
 * Executing the command copies a file from one point to another.
 * If the file already exists in the target destination, the existing file is overwritten.
 */
@Command(
        name = "copy",
        description = "Copy a file",
        mixinStandardHelpOptions = true)
public class CopyCommand implements Runnable {

    @Parameters(index = "0", description = "path of the file to copy")
    private File source;

    @Parameters(index = "1", description = "path to copy file to")
    private File target;

    public CopyCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        boolean fileExists = Files.exists(target.toPath());
        StandardCopyOption copyOption = StandardCopyOption.ATOMIC_MOVE;

        if (fileExists) {
            boolean shouldOverwrite = askUserToOverwrite();

            if (!shouldOverwrite) {
                System.out.println("The file was not copied to the new path, so that an existing file is not overwritten.");
                return;
            }
        }

        try {
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("The file was successfully copied to the new path.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean askUserToOverwrite() {
        Scanner input = new Scanner(System.in);
        String answer;

        do {
            System.out.print("The file already exists. Do you wish to overwrite the existing file? (Y/N): ");
            answer = input.next().trim().toUpperCase();
        } while (!(answer.equals("Y") || answer.matches("N")));

        return answer.equals(("Y"));
    }

}
