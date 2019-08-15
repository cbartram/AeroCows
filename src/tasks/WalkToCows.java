package tasks;

import constants.Location;
import org.osbot.rs07.script.MethodProvider;

/**
 * Uses web walking to walk from any area to the cow pasture.
 * This will open doors and gates that are in the way
 * Created by cbartram on 2019-08-13.
 * http://github.com/cbartram
 */
public class WalkToCows extends Task {
    // Holds a reference to the Location Enum for each cow location
    private final Location location;

    public WalkToCows(MethodProvider ctx, String name, Location location) {
        super(ctx, name);
        this.location = location;
    }

    @Override
    public boolean activate() {
        return !location.getArea().contains(ctx.myPlayer())
                && !ctx.myPlayer().isMoving()
                && !ctx.getInventory().contains("Cowhide");
    }

    @Override
    public void execute() {
        setStatus("Walking to Pasture...");
        ctx.getWalking().webWalk(location.getArea());
    }
}
