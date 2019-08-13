package tasks;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;

/**
 * Created by christianbartram on 2019-08-13.
 * <p>
 * http://github.com/cbartram
 */
public class CollectHide extends Task {
    public CollectHide(MethodProvider ctx, String name) {
        super(ctx, name);
        setName(name);
    }

    @Override
    public boolean activate() {
       GroundItem cowhide = ctx.getGroundItems().singleFilter(ctx.getGroundItems().getAll(),
               (Filter<GroundItem>) item -> item.getName().equalsIgnoreCase("Cowhide"));
       if(cowhide == null) return false;
       // Won't try to collect cowhides while the player is currently in combat or running to collect the cowhide
       return cowhide.isOnScreen() && !ctx.myPlayer().isUnderAttack() && !ctx.myPlayer().isMoving();
    }

    @Override
    public void execute() {
        setStatus("Collecting cowhide");
        GroundItem cowhide = ctx.getGroundItems().closest("Cowhide");
        cowhide.interact("Take");
    }
}
