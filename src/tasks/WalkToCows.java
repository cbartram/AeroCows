package tasks;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.MethodProvider;

/**
 * Created by christianbartram on 2019-08-13.
 * <p>
 * http://github.com/cbartram
 */
public class WalkToCows extends Task {
    private Area COW_AREA = new Area(
            new int[][]{
                    { 3240, 3297 },
                    { 3240, 3294 },
                    { 3242, 3291 },
                    { 3239, 3285 },
                    { 3246, 3277 },
                    { 3249, 3277 },
                    { 3251, 3276 },
                    { 3251, 3273 },
                    { 3253, 3271 },
                    { 3253, 3254 },
                    { 3266, 3254 },
                    { 3266, 3297 },
                    { 3263, 3299 },
                    { 3261, 3299 },
                    { 3261, 3300 },
                    { 3256, 3300 },
                    { 3241, 3299 }
            }
    );

    public WalkToCows(MethodProvider ctx, String name) {
        super(ctx, name);
    }

    @Override
    public boolean activate() {
        return !COW_AREA.contains(ctx.myPlayer()) && !ctx.myPlayer().isMoving();
    }

    @Override
    public void execute() {
        setStatus("Walking to Pasture...");
        ctx.getWalking().webWalk(COW_AREA);
    }
}
