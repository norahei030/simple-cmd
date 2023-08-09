package cmd.commands.date;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * "Change Directory" command class
 * <p/>
 * Executing the command lists all the files and folders in the current working directory.
 *
 * @see SimpleCmd#getCurrentLocation() 
 * @see SimpleCmd#setCurrentLocation(File) 
 */
@Command(
        name = "date",
        description = "Print current date",
        mixinStandardHelpOptions = true)
public class DateCommand implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(DateCommand.class);


    public DateCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        String timeStamp = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        LOG.info(timeStamp + "\n");
    }
}
