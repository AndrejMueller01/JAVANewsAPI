package javanews.threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import javanews.utils.DBCommunication;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author AM
 */
public class PollThread extends Thread {

    // id starts at 1 in DB
    private int lastID = 1;
    private final DBCommunication dbc;
    private String[] keywords = null;

    public PollThread(String[] keywords) {
        dbc = DBCommunication.getInstance();
        this.keywords = keywords;
    }

    @Override
    public void run() {

        String entry;

        while (true) {
            entry = dbc.getNewsEntryById(lastID);
            if (entry.equals("false")) {
                break;
            }
            try {
                JSONTokener tokener = new JSONTokener(entry);
                JSONObject dbJsonEntry = new JSONObject(tokener);
                String published = dbJsonEntry.getString("published");
                // dirty fx for "'" replacement
                String title = dbJsonEntry.getString("title").replaceAll("%47", "'");
                // the bonus task
                if (keywords != null) {
                    boolean containKW = false;
                    for (String keyword : keywords) {
                        if (title.contains(keyword)) {
                            containKW = true;
                        }
                    }
                    if (containKW) {
                        System.out.println(published + " " + title);
                    }
                } else {
                    System.out.println(published + " " + title);
                }
            } catch (JSONException ex) {
                Logger.getLogger(PollThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            lastID++;
        }
    }
}
