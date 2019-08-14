package tasks;

import org.osbot.rs07.script.MethodProvider;

/**
 * Created by christianbartram on 2019-08-09.
 * <p>
 * http://github.com/cbartram
 */
public abstract class Task{
    private String name;
    private String status; // The status of any action within this task used to update GUI
    MethodProvider ctx;

    public Task(MethodProvider ctx, String name) {
        this.ctx = ctx;
        this.name = name;
        this.status = name; // Default Status
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract boolean activate();
    public abstract void execute();
}
