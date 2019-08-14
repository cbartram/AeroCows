package tasks;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;
import util.Sleep;

/**
 * DepositHides.java
 * Deposits all the cowhides into the bank
 * Created by cbartram on 2019-08-13.
 * http://github.com/cbartram
 */
public class DepositHides extends Task {
    public DepositHides(MethodProvider ctx, String name) {
        super(ctx, name);
    }

    @Override
    public boolean activate() {
       NPC banker = ctx.getNpcs().singleFilter(ctx.getNpcs().getAll(), (Filter<NPC>) npc -> npc.getName().equalsIgnoreCase("Banker"));
        if(banker != null) {
            ctx.log("Banker is on screen: " + banker.isOnScreen());
            return banker != null && banker.isOnScreen() && Banks.LUMBRIDGE_UPPER.contains(ctx.myPlayer());
        }
        return false;
    }

    @Override
    public void execute() {
        setStatus("Depositing Hides...");
        NPC banker = ctx.getNpcs().singleFilter(ctx.getNpcs().getAll(), (Filter<NPC>) npc -> npc.getName().equalsIgnoreCase("Banker"));
        banker.interact("Bank");
        new Sleep(() -> true, 5000);
        if (ctx.getBank().isOpen()) {
            ctx.getBank().depositAll("Cowhide");
        }
    }
}
