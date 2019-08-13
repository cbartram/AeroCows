package tasks;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Entity;
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
       return false;
    }

    @Override
    public void execute() {

    }
}
