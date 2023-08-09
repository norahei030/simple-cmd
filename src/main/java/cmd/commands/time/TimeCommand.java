package cmd.commands.time;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

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
        name = "time",
        description = "Print current time",
        mixinStandardHelpOptions = true)
public class TimeCommand implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(TimeCommand.class);


    public TimeCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        LOG.info(timeStamp + "\n");
    }
}
