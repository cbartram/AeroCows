package tasks;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;
import util.Sleep;

/**
 * tasks.Wait.java
 * Waits a specified amount of time for a condition to be met.
 * Mainly that the player and cow are out of combat
 *
 * Created by cbartram on 2019-08-13.
 * http://github.com/cbartram
 */
public class Wait extends Task {
    public Wait(MethodProvider ctx, String name) {
        super(ctx, name);
        setName(name);
    }

    @Override
    public boolean activate() {
        NPC cow = ctx.getNpcs().singleFilter(ctx.getNpcs().getAll(), (Filter<NPC>) npc -> npc.getName().equals("Cow"));
        if(cow == null) return false;
        return cow.getAnimation() == -1 && !ctx.myPlayer().isUnderAttack();
    }

    @Override
    public void execute() {
        setStatus("Attacking Cow");
		new Sleep(() -> ctx.myPlayer().isUnderAttack(), 5000).sleep(); // TODO or cow doesnt is dead / doesnt exist
    }
}
