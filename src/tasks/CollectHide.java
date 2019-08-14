package tasks;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.MethodProvider;

/**
 * Created by christianbartram on 2019-08-13.
 * <p>
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
    public void execute() {
        setStatus("Collecting cowhide");
        GroundItem cowhide = ctx.getGroundItems().closest("Cowhide");
        cowhide.interact("Take");
    }
}
