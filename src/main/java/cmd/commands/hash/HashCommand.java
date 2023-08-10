package cmd.commands.hash;

import cmd.SimpleCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;


/**
 * "Hash" command class
 * <p/>
 * Executing the command hash the file given in parameter.
 *
 * @see SimpleCmd#getCurrentLocation() 
 * @see SimpleCmd#setCurrentLocation(File) 
 */
@Command(
        name = "hash",
        description = "hash given file",
        mixinStandardHelpOptions = true)
public class HashCommand implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(HashCommand.class);

    @CommandLine.Parameters(index = "0", description = "path of file to hash")
    private File file;
    
    public HashCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
    	try {
    		MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(Files.readAllBytes(file.toPath()));
		
	        byte[] digest = md.digest();
	        String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
	
	        LOG.info("Hash for " + file.getAbsolutePath() + " -> is "+ myHash + "\n");
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
