package cmd.commands.cd;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;

/**
 * "Change Directory" command class
 * <p/>
 * Executing the command lists all the files and folders in the current working directory.
 *
 * @see SimpleCmd#getCurrentLocation() 
 * @see SimpleCmd#setCurrentLocation(File) 
 */
@Command(
        name = "cd",
        description = "Change current working directory",
        mixinStandardHelpOptions = true)
public class CdCommand implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(CdCommand.class);
    @Parameters(index = "0", description = "change working path to", arity = "0..1")
    private File target;


    public CdCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
    	if(target != null && target.isDirectory()) {
        	SimpleCmd.setCurrentLocation(target);
        	LOG.info("working path is moved to {}.\n", target);
    	} else {
        	LOG.info("cd target path is not valid.\n");
    	}
    }
}
