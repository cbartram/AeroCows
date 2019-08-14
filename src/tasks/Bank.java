package tasks;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;

/**
 * Bank.java
 * Determines when to walk to the lumbridge back to deposit a
 * full inventory of fresh cow hide.
 * Created by cbartram on 2019-08-13.
 * http://github.com/cbartram
 */
public class Bank extends Task {
    public Bank(MethodProvider ctx, String name) {
        super(ctx, name);
    }

    @Override
    public boolean activate() {
        NPC banker = ctx.getNpcs().singleFilter(ctx.getNpcs().getAll(), (Filter<NPC>) npc -> npc.getName().equalsIgnoreCase("Banker"));

        return ctx.getInventory().contains("Cowhide")
                && ctx.getInventory().isFull()
                && !Banks.LUMBRIDGE_UPPER.contains(ctx.myPlayer())
                && banker == null;
     }

    @Override
    public void execute() {
        setStatus("Walking to bank");
        ctx.getWalking().webWalk(Banks.LUMBRIDGE_UPPER);
    }
}
