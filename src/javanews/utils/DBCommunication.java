package javanews.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AM
 */
public class DBCommunication {

    // a tiny amount of security :)
    private final String secretKey = "rLdZyTAJeynUh6JDR8Sut8Yj1sLXIPWO";
    
    // i decided to make php scrips for every db action. 
    public String addNewsURL = "http://www.andrejmueller.com/newscit/addnews.php?";
    public String getNewsURL = "http://www.andrejmueller.com/newscit/getnews.php?";
    public String deleteNewsURL = "http://www.andrejmueller.com/newscit/deletenews.php?";
    public String checkNewsURL = "http://www.andrejmueller.com/newscit/checknews.php?";

    private static DBCommunication instance;

    private DBCommunication() {
    }

    public static DBCommunication getInstance() {
        if (DBCommunication.instance == null) {
            DBCommunication.instance = new DBCommunication();
        }
        return DBCommunication.instance;
    }

    public void postNewsEntry(String source, String title, Date date, String url) {
        String hash = sha1(secretKey);
        String postUrl = addNewsURL + "source=" + source + "&hash=" + hash + "&published=" + date + "&url=" + url + "&title=" + makeStringDBFriendly(title);
        handleUrl(postUrl);
    }

    public void deleteNews() {
        String hash = sha1(secretKey);
        String deleteUrl = deleteNewsURL + "hash=" + hash;
        handleUrl(deleteUrl);
    }

    public boolean checkNews(String source, String title) {
        String checkUrl = checkNewsURL + "source=" + source + "&title=" + makeStringDBFriendly(title);
        String str = handleUrl(checkUrl);
        return "ok".equals(str);
    }

    public String getNewsEntryById(int id) {
        String getUrl = getNewsURL + "id=" + id;
        return handleUrl(getUrl);
    }

    // sha1 computation
    private String sha1(String input) {
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DBCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString().toUpperCase();
    }

    // to avoid issues with the url
    private String makeStringDBFriendly(String str) {
        try {
            return java.net.URLEncoder.encode(str.replaceAll("'", "%47"), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DBCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // contact server and handle the server answers
    private String handleUrl(String url) {
        try {
            URL dbUrl = new URL(url);
            URLConnection myURLConnection = dbUrl.openConnection();
            myURLConnection.connect();
            StringBuilder response;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(myURLConnection.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            return response.toString();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return null;
    }
}
