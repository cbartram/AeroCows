package tasks;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;

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
        setName(name);
    }

    @Override
    public boolean activate() {
        Entity cow = ctx.getNpcs().singleFilter(ctx.getNpcs().getAll(), (Filter<NPC>) npc -> npc.getName().equals("Cow"));
        if(cow == null) {
            ctx.log("Cow is null");
            return false;
        }
        ctx.log("Player under attack: " + ctx.myPlayer().isUnderAttack());
        return !ctx.myPlayer().isUnderAttack() && !ctx.myPlayer().isMoving(); // && cow animation is not "Dying"
    }

    @Override
    public void execute() {
        Entity cow = ctx.getNpcs().singleFilter(ctx.getNpcs().getAll(), (Filter<NPC>) npc -> npc.getName().equals("Cow"));
        setStatus("Finding new cow to attack...");
        if(cow != null)  {
            ctx.log("Found cow to attack");
            cow.interact("Attack");
        } else {
            ctx.log("Couldn\'t find any cows");
        }
    }
}
