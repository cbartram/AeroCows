package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

/**
 * Util.java
 * Basic Utility class which holds static methods
 * for things like time formatting, money formatting and
 * retrieving GE prices for specific items
 * Created by cbartram on 2019-08-13.
 * <p>
 * http://github.com/cbartram
 */
public class Util {
    public static Optional<Integer> getPrice(int id){
        Optional<Integer> price = Optional.empty();

        try {
            URL url = new URL("http://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + id);
            URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
            con.setUseCaches(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String[] data = br.readLine().replace("{", "").replace("}", "").split(",");
            br.close();
            price = Optional.of(Integer.parseInt(data[0].split(":")[1]));
        } catch(Exception e){
            e.printStackTrace();
        }
        return price;
    }

    public static String formatTime(final long ms){
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public static String formatValue(final long l) {
        return (l > 1_000_000) ? String.format("%.2fm", ((double) l / 1_000_000))
                : (l > 1000) ? String.format("%.1fk", ((double) l / 1000))
                : l + "";
    }
}
