package cmd.commands.web.mkdir;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * "Web" command class
 * <p/>
 * Executing the command web checks if a given url is reachable and then open it.
 */
@Command(
        name = "web",
        description = "Check and open url",
        mixinStandardHelpOptions = true)
public class WebCommand implements Runnable {

    @Parameters(description = "url")
    private String givenUrl;

    public WebCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        try {
            URL url = new URL(givenUrl);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();

            int responseCode = huc.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("url is reachable");
                Runtime rt = Runtime.getRuntime();
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
                //return;
            }
            System.out.println("url is not reachable. please try a http url!");
        } catch (IOException e) {
            System.out.println("url is not reachable. please try a http url!");
        }
    }
}
