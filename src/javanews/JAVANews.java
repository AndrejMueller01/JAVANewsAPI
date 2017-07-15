package javanews;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javanews.utils.DBCommunication;
import javanews.threads.FetchThread;
import javanews.threads.PollThread;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author AM
 *
 * Fun with newsapi.org: Craft-IT Coding Challenge "JAVA News API" on:
 * https://github.com/tomw1808/cc2017_07_java
 *
 */
public class JAVANews {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*
        
        The following things should be configurable using a config file or command line parameters:
        - API key
        - News sources of the different query-threads
        - (Bonus task: Filter keywords)

         */
        Options options = new Options();

        Option apikey = new Option("a", "apikey", true, "the api key from newsapi.org");
        apikey.setRequired(true);
        options.addOption(apikey);

        Option sources = new Option("s", "sources", true, "the 2 news sources from newsapi.org");
        sources.setRequired(true);
        // currently 2
        sources.setArgs(2);
        options.addOption(sources);

        Option keywords = new Option("k", "keywords", true, "keywords for filtering");
        keywords.setRequired(false);
        keywords.setOptionalArg(true);
        options.addOption(keywords);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        String apiKey = null;//= "ba8a6a936e74432db3fab910729c515a";
        String[] newsSources = null;
        String[] keywordsString = null;

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("a")) {
                apiKey = cmd.getOptionValue("a");
            }
            if (cmd.hasOption("s")) {
                newsSources = cmd.getOptionValues("s");
            }
            if (cmd.hasOption("k")) {
                keywordsString = cmd.getOptionValues("k");
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
        }

        DBCommunication dbc = DBCommunication.getInstance();

        // clear my database
        dbc.deleteNews();

        // two fetch and one poll thread
        FetchThread ft1 = new FetchThread(newsSources[0], "top", apiKey);
        FetchThread ft2 = new FetchThread(newsSources[1], "top", apiKey);
        PollThread pt1 = new PollThread(keywordsString);

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        // there are updates every 5 seconds
        exec.scheduleAtFixedRate(ft1, 0, 5, TimeUnit.SECONDS);
        exec.scheduleAtFixedRate(ft2, 0, 5, TimeUnit.SECONDS);
        exec.scheduleAtFixedRate(pt1, 0, 5, TimeUnit.SECONDS);
    }
}
