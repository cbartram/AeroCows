package tasks;

import constants.Cow;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;
import util.Sleep;

/**
 * tasks.AttackCow
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
        if(cow == null) {
            ctx.log("Cow is null");
            return false;
        }
        // Before we click on a new cow lets wait for the previous cow to die and we can collect the hide
        setStatus("Waiting for previous cow to die...");
        new Sleep(() -> true, 3000);

        ctx.log("Cow animation: " + cow.getAnimation());
        return  !ctx.myPlayer().isUnderAttack() &&
                !ctx.myPlayer().isMoving() &&
                cow.getAnimation() != Cow.DYING.getAnimation() &&
                cow.getAnimation() != Cow.ATTACKING.getAnimation(); // Prevents us from attacking a cow someone else is already in combat with
    }

    @Override
    public void execute() {
        Entity cow = ctx.getNpcs().singleFilter(ctx.getNpcs().getAll(), (Filter<NPC>) npc -> npc.getName().equals("Cow"));
        setStatus("Finding new cow to attack...");
        if(cow != null)  {
            cow.interact("Attack");
            // Sleep for a few seconds while we wait for our player to get to the cow and start combat
            new Sleep(() -> true, 3000);
        }
    }
}
