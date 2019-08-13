package tasks;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.script.MethodProvider;

/**
 * Created by christianbartram on 2019-08-13.
 * <p>
 * http://github.com/cbartram
 */
public class Bank extends Task {
    public Bank(MethodProvider ctx, String name) {
        super(ctx, name);
    }

    @Override
    public boolean activate() {
//        Item[] items = ctx.getInventory().getItems();
//        for(Item i : items) {
//            i.
//        }
       return ctx.getInventory().contains("Cowhide") && ctx.getInventory().getEmptySlotCount() < 20;
    }

    @Override
    public void execute() {
        setStatus("Walking to bank");
        ctx.getWalking().webWalk(Banks.LUMBRIDGE_UPPER);
    }
}
