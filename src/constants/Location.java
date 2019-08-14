package constants;

import org.osbot.rs07.api.map.Area;

/**
 * Location.java
 * Holds references to static Area objects where cows
 * exist and can be fought
 * Created by cbartram on 2019-08-14.
 * http://github.com/cbartram
 */
public enum Location {
    EAST(new Area(new int[][]{
            { 3243, 3297 },
            { 3243, 3283 },
            { 3254, 3273 },
            { 3253, 3255 },
            { 3265, 3256 },
            { 3265, 3297 }
    }
    )),
    WEST(new Area(new int[][]{
            { 3193, 3300 },
            { 3193, 3285 },
            { 3198, 3282 },
            { 3212, 3286 },
            { 3210, 3302 },
            { 3194, 3302 }
    }
    ));
    private Area area;

    Location(Area area) {
        this.area = area;
    }
    public Area getArea() {
        return area;
    }
}
