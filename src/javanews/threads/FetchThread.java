package javanews.threads;

import java.io.IOException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javanews.utils.DBCommunication;
import javanews.utils.JsonReader;
import javanews.utils.StringParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AM
 */
public class FetchThread extends Thread {

    private final String fetchUrl;
    private final String source;

    private final DBCommunication dbc;

    public FetchThread(String source, String sortMode, String apiKey) {
        // construct the fetch URL
        fetchUrl = "https://newsapi.org/v1/articles?source=" + source + "&sortBy=" + sortMode + "&apiKey=" + apiKey;
        this.source = source;
        dbc = DBCommunication.getInstance();
    }

    @Override
    public void run() {
        try {
            JSONObject wholeJsonObject = JsonReader.readJsonFromUrl(fetchUrl);
            JSONArray articlesJsonObject = wholeJsonObject.getJSONArray("articles");
            for (int i = 0; i < articlesJsonObject.length(); i++) {
                JSONObject singleArticleJsonOblect = articlesJsonObject.getJSONObject(i);
                String title = singleArticleJsonOblect.getString("title");
                
                // redundancy check
                if (dbc.checkNews(source, title)) {   
                    String publishedAt = null;
                    try {
                        publishedAt = singleArticleJsonOblect.getString("publishedAt");
                    } catch (JSONException ex) {
                    }
                    Date publishedAtDate = StringParser.convertNewsApiDateStringToDate(publishedAt);
                    String url = singleArticleJsonOblect.getString("url");
                    dbc.postNewsEntry(source, title, publishedAtDate, url );
                }

            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(FetchThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
