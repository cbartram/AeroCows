package util;

import org.osbot.rs07.script.MethodProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.Random;

/**
 * Util.java
 * Basic Utility class which holds static methods
 * for things like time formatting, money formatting and
 * retrieving GE prices for specific items
 * Created by cbartram on 2019-08-13.
 * http://github.com/cbartram
 */
public class Util {

    /**
     * Retrieves the current price of a Grand Exchange tradeable item given its ID.
     * @param id Integer the id of the item to lookup
     * @return Optional representing the id and null if it could notbe found
     */
    public static Optional<Integer> getPrice(int id){
        Optional<Integer> price = Optional.empty();

        try {
            URL url = new URL("http://services.runescape.com/m=itemdb_oldschool/Cowhide/viewitem?obj=" + id);
            URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
            con.setUseCaches(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data;
            while ((data = br.readLine()) != null) {
                if(data.contains("Current Guide Price")) {
                    int firstIndex = data.indexOf("title=\'");
                    int lastIndex = data.lastIndexOf("\'");
                    price = Optional.of(Integer.parseInt(data.substring(firstIndex + 7, lastIndex)));
                }
            }
            br.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return price;
    }

    /**
     * Returns a random integer between the specified low and high integers
     * @param low Integer lowest possible value returned from the random number generator
     * @param high Integer highest possible value returned from the random number generator
     * @return Integer between the specified range
     */
    public static int rand(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    /**
     * Computes the total amount of gold gained from the script each
     * hour that is has been run. Note: this also has the added convenience of
     * formatting the value returned to be a string like 1k, 19k, 20m etc...
     * @param totalGold Integer
     * @param ms Long the time in milliseconds the script has been running
     * @return String the amount of gold gained each hour pre-formatted
     */
    public static String goldPerHour(int totalGold, long ms) {
        return formatValue(totalGold / ((ms / 1000) / 60) / 60);
    }

    /**
     * Formats time in a human readable way hh:mm:ss
     * @param ms long Time im milliseconds to format
     * @return String the correctly formatted time in hh:mm:ss
     */
    public static String formatTime(final long ms){
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    /**
     * Formats a monetary value from its raw long to a string like 1m 10m, 1k, 20k etc...
     * @param l Long the input value given
     * @return String the value returned after it has been formatted
     */
    public static String formatValue(final long l) {
        return (l > 1_000_000) ? String.format("%.2fm", ((double) l / 1_000_000))
                : (l > 1000) ? String.format("%.1fk", ((double) l / 1000))
                : l + "";
    }
}
