package tasks;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;

/**
 * Created by christianbartram on 2019-08-13.
 * <p>
 * http://github.com/cbartram
 */
public class WalkToCows extends Task {
    private Area COW_AREA = new Area(
            new int[][]{
                    { 3243, 3297 },
                    { 3243, 3283 },
                    { 3254, 3273 },
                    { 3253, 3255 },
                    { 3265, 3256 },
                    { 3265, 3297 }
            }
    );

    public WalkToCows(MethodProvider ctx, String name) {
        super(ctx, name);
    }

    @Override
    public boolean activate() {
        ctx.getInventory().contains("Cowhide");
        return !COW_AREA.contains(ctx.myPlayer())
                && !ctx.myPlayer().isMoving()
                && !ctx.getInventory().contains("Cowhide");
    }

    @Override
    public void execute() {
        setStatus("Walking to Pasture...");
        ctx.getWalking().webWalk(COW_AREA);
    }
}
