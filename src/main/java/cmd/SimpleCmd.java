package cmd;

import cmd.commands.BaseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Entry point of the Simple CMD application for training purposes.
 * <p/>
 * This class provides the basic read-evaluate-print (REPL) loop, which enables the Simple CMD application to
 * behave like a very basic command line interface (CLI).
 */
public class SimpleCmd {

  /**
   * register the {@link BaseCommand} in the new {@link CommandLine} Object
   */
  public static final CommandLine commandLine = new CommandLine(new BaseCommand())
          .setErr(new PrintWriter(System.out));

  private static final Logger LOG = LoggerFactory.getLogger(SimpleCmd.class);

  private static File currentLocation = new File(".");

  /**
   * Main starting point of the CLI. Here we parse the arguments from the user.
   *
   * @param args list of arguments the user has passed
   */
  public static void main(String... args) {

    try (Scanner scanner = new Scanner(System.in)) {
      String[] arguments;
      String nextLine = "";
      LOG.info(">>> ");
      do {
        nextLine = scanner.nextLine();
        if (0 < nextLine.length()) {
          arguments = nextLine.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");
          for(int i=0;i<arguments.length;i++) {
        	  arguments[i] = arguments[i].replaceAll("\"", "");
          }
          commandLine.execute(arguments);
        }
        LOG.info(">>> ");
      } while (scanner.hasNextLine());

    } catch (IllegalStateException | NoSuchElementException e) {
      // System.in has been closed
      LOG.error("Error", e);
    }

  }

  /**
   * @return {@link File} pointing to current location, to be used across application
   */
  public static File getCurrentLocation() {

    return currentLocation;
  }

  /**
   * Sets current Location to provided {@link File}, can be used across application
   *
   * @param currentLocation {@link File}
   */
  public static void setCurrentLocation(File currentLocation) {

    SimpleCmd.currentLocation = currentLocation;
  }

}
