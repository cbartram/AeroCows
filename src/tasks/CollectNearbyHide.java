package tasks;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.MethodProvider;

/**
 * CollectNearbyHide.java
 * Sometimes while in combat this task will try to collect a hide
 * that is within 3-4 spaces of the player if it exists
 * Created by cbartram on 2019-08-13.
 * <p>
 * http://github.com/cbartram
 */
public class CollectNearbyHide extends Task {
    public CollectNearbyHide(MethodProvider ctx, String name) {
        super(ctx, name);
    }

    @Override
    public boolean activate() {
        return false;
//        // First we build an array of tiles around the player
//        GroundItem cowhide = ctx.getGroundItems().closest("Cowhide");
//        ctx.log("Cowhide exists: " + cowhide != null);
//        if(cowhide == null) return false;
//
//        ctx.log("Cowhide on screen: " + cowhide.isOnScreen());
//        ctx.log("Player moving: " + ctx.myPlayer().isMoving());
//        // Won't try to collect cowhides while the player is currently in combat or running to collect the cowhide
//        return cowhide.isOnScreen() && !ctx.myPlayer().isMoving();
    }

    @Override
    public void execute() {
        setStatus("Collecting cowhide");
        GroundItem cowhide = ctx.getGroundItems().closest("Cowhide");
        cowhide.interact("Take");
    }
}
