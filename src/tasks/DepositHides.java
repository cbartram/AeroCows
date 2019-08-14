package tasks;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

/**
 * DepositHides.java
 * Deposits all the cowhides into the bank
 * Created by cbartram on 2019-08-13.
 * http://github.com/cbartram
 */
public class DepositHides extends Task {
    private static final int BANK_BOOTH_ID = 27291;
    private int cowhidesBanked;

    public DepositHides(MethodProvider ctx, String name, int cowhidesBanked) {
        super(ctx, name);
        this.cowhidesBanked = cowhidesBanked;
    }

    @Override
    public boolean activate() {
        RS2Object bankBooth = ctx.getObjects().closest(BANK_BOOTH_ID);
        if(bankBooth != null) {
            return bankBooth.isVisible(); // TODO perhaps && Banks.LUMBRIDGE_UPPER.contains(ctx.myPlayer());
        }
        return false;
    }

    @Override
    public void execute() throws InterruptedException {
        setStatus("Depositing Hides...");
        RS2Object bankBooth = ctx.getObjects().closest(BANK_BOOTH_ID);
        bankBooth.interact("Bank");
        MethodProvider.sleep(5000);
        if (ctx.getBank().isOpen()) {
            cowhidesBanked += 28 - ctx.getInventory().getEmptySlotCount(); // TODO Assumes every item in inv is cowhide
            ctx.getBank().depositAll("Cowhide");
        }
    }
}
