package tasks;

import org.osbot.rs07.script.MethodProvider;

/**
 * Abstract class which is used to execute various tasks the script must perform.
 * A subclass of this must implement the activate() and execution methods. When activate is true
 * for this task execute is called. Logic pertaining to the execution of this task should be in the #execute() method
 * while the logic to tell the script when this should execute should be stored in #activate(). This class
 * is evaluated in the primary loop for the script.
 * Created by cbartram on 2019-08-09.
 * http://github.com/cbartram
 */
public abstract class Task {
    private String name;
    private String status; // The status of any action within this task used to update GUI
    MethodProvider ctx; // Provides the primary instance by which subclasses can access Runescape game resources for the script

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

    public abstract boolean activate() throws InterruptedException;
    public abstract void execute() throws InterruptedException; // Throws exception so we can non conditionally sleep within the script
}
