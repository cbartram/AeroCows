package tasks;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.MethodProvider;

/**
 * Collects nearby cowhides from the ground
 * when they are visible
 * Created by cbartram on 2019-08-13.
 * http://github.com/cbartram
 */
public class CollectHide extends Task {
    public CollectHide(MethodProvider ctx, String name) {
        super(ctx, name);
    }

    @Override
    public boolean activate() {
        // First we build an array of tiles around the player
        GroundItem cowhide = ctx.getGroundItems().closest("Cowhide");
        if(cowhide == null) return false;
        // Won't try to collect cowhides while the player is currently in combat or running to collect the cowhide
        return cowhide.isOnScreen() && !ctx.myPlayer().isMoving();
    }

    @Override
    public void execute() throws InterruptedException {
        setStatus("Collecting cowhide");
        GroundItem cowhide = ctx.getGroundItems().closest("Cowhide");
        cowhide.interact("Take");
        MethodProvider.sleep(1000);
    }
}
