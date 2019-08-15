package tasks;

import constants.Cow;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;
import util.Util;

/**
 * AttackCow.java
 * Finds and fights cows in the nearby vicinity. It will also
 * collect their
 * Created by cbartram on 2019-08-09.
 * http://github.com/cbartram
 */
public class AttackCow extends Task {
    public AttackCow(MethodProvider ctx, String name) {
        super(ctx, name);
    }

    @Override
    public boolean activate() {
        NPC cow = ctx.getNpcs().singleFilter(ctx.getNpcs().getAll(), (Filter<NPC>) npc -> npc.getName().equals("Cow"));
        GroundItem cowhide = ctx.getGroundItems().closest("Cowhide");

        if(cow == null) return false;

        if(cowhide == null) {
            return !ctx.myPlayer().isUnderAttack() &&
                    !ctx.myPlayer().isMoving() &&
                    // Prevents us from attacking a cow someone else is already in combat with
                    cow.getAnimation() != Cow.DYING.getAnimation() &&
                    cow.getAnimation() != Cow.ATTACKING.getAnimation();
        }

        return  !ctx.myPlayer().isUnderAttack() &&
                !ctx.myPlayer().isMoving() &&
                !cowhide.isOnScreen() &&
                cow.getAnimation() != Cow.DYING.getAnimation() &&
                cow.getAnimation() != Cow.ATTACKING.getAnimation();
    }

    @Override
    public void execute() throws InterruptedException {
        MethodProvider.sleep(Util.rand(3000, 4500));
        Entity cow = ctx.getNpcs().singleFilter(ctx.getNpcs().getAll(), (Filter<NPC>) npc -> npc.getName().equals("Cow"));
        setStatus("Finding new cow...");
        if(cow != null)  {
            cow.interact("Attack");
            // Sleep for a few seconds while we wait for our player to get to the cow and start combat
            MethodProvider.sleep(Util.rand(4000, 4500));
        }
    }
}
