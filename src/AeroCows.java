import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import tasks.AttackCow;
import tasks.Bank;
import tasks.CollectHide;
import tasks.DepositHides;
import tasks.Task;
import tasks.WalkToCows;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ScriptManifest(author = "Aerodude30", name = "AeroCows", info = "Kills cows and banks hides in Lumbridge for quick cash", version = 1.0, logo = "https://i.imgur.com/CVhwm8l.png")
public final class AeroCows extends Script {
	private List<Task> tasks = new ArrayList<>();
	private GUI gui = new GUI();
	private String status = "Initializing Script"; // Script status
	private int cowHidesBanked = 0;
	private long startTime;
	private int cowhidePrice = 0;
	private static final Skill[] skills = { Skill.ATTACK, Skill.DEFENCE, Skill.STRENGTH, Skill.RANGED, Skill.MAGIC };
	private static final int COWHIDE_ID = 1739;

	@Override
	public final void onStart() {
		startTime = System.currentTimeMillis();
		for(final Skill skill : skills) {
			getExperienceTracker().start(skill);
		}

		// Cache current price of cowhide
		Optional<Integer> price = Util.getPrice(COWHIDE_ID);
		price.ifPresent(integer -> cowhidePrice = integer);

		log("Cow hide price: " + cowhidePrice);

		// Add all our tasks to the task list
		tasks.addAll(Arrays.asList(
				// The order of these items tasks matter as the activation conditions will be evaluated
				// in a loop in this order.
				new DepositHides(this, "Depositing Hides"),
				new Bank(this, "Banking"),
				new CollectHide(this, "Collect Hide"),
				new AttackCow(this,"Attack Cow"),
				new WalkToCows(this, "Walk to Cows", gui.getSelectedLocation())
		));

		try {
			SwingUtilities.invokeAndWait(() -> {
				gui = new GUI();
				gui.open();
				status = "Waiting for GUI Input";
			});
		} catch (InterruptedException | InvocationTargetException e) {
			e.printStackTrace();
			stop();
		}

		// Stop the script if the user never clicked the start button
		// but closed the GUI
		if (!gui.isStarted()) {
			stop();
		}
	}

	@Override
	public final int onLoop() {
		try {
			for (Task task : tasks) {
				if (task.activate()) {
					status = task.getStatus();
					// Update the count with hides deposited if the task was to deposit the hides
					if(task.getName().equalsIgnoreCase("Depositing Hides"))  {
						cowHidesBanked += 28 - getInventory().getEmptySlotCount();
						log("Cowhides banked: " + cowHidesBanked);
					}
					task.execute();
				}
			}
		} catch(InterruptedException e) {
			log("There has been an error!");
			e.printStackTrace();
		}
		status = "Waiting...";
		return random(150, 200);
	}

	@Override
	public final void onExit() {
		if(gui.isOpen()) gui.close();
		log("Closing Script...");
	}

	@Override
	public final void onMessage(final Message message) {
		// TODO: Impl if needed
	}

	@Override
	public void onPaint(final Graphics2D g) {
		final long runTime = System.currentTimeMillis() - startTime;

		// Fill the background color rectangle
		g.setColor(new Color(120, 111, 100, 150));
		g.fillRect(0,0,200,150);
		g.setColor(Color.RED);

		// Create a border
		g.setColor(Color.CYAN);
		g.drawRect(0, 0, 200, 150);

		g.setFont(g.getFont().deriveFont(12.0f));

		// Draw the main text and space is out bc its blurry
		g.drawString("A e r o  C o w s", 10, 20);

		g.setColor(Color.WHITE);
		g.drawString("Status: " + status, 10, 40);
		g.drawString("Runtime: " + Util.formatTime(runTime), 10, 60);
		g.drawString("Cowhides Banked: " + cowHidesBanked, 10, 80);
		g.drawString("Gold Earned: " + Util.formatValue(cowhidePrice * cowHidesBanked), 10, 100);
		g.drawString("Gold/Hour: " + Util.goldPerHour(cowhidePrice * cowHidesBanked, runTime), 10, 120);
		g.drawString( "TTL: " + Util.formatTime(getExperienceTracker().getTimeToLevel(Skill.DEFENCE)), 10, 140);

		// Draw the mouse cursor
		Point pos = getMouse().getPosition();

		g.drawLine(pos.x - 5, pos.y + 5, pos.x + 5, pos.y - 5);
		g.drawLine(pos.x + 5, pos.y + 5, pos.x - 5, pos.y - 5);


		// Paint the tiles around the player only if they want to see it
		// it could be annoying for some players
		if(gui.shouldShowOutline()) {
			Area nearby = myPlayer().getArea(7);

			// Find all the cow positions
			List<Position> cowPositions = getNpcs().filter(getNpcs().getAll(),
					(Filter<NPC>) cow -> cow.getName().equalsIgnoreCase("Cow"))
					.stream().map(cow -> cow.getPosition()).collect(Collectors.toList());

			// Cowhide positions near the player
			List<Position> cowhides = getGroundItems().filter(getGroundItems().getAll(), (Filter<GroundItem>) item -> item.getName().equalsIgnoreCase("Cowhide"))
					.stream().map(hide -> hide.getPosition()).collect(Collectors.toList());

			for (Position p : nearby.getPositions()) {
				// Paint cows cyan
				if (cowPositions.contains(p)) {
					g.setColor(Color.CYAN);
					g.drawPolygon(p.getPolygon(bot));
				}

				// Paint cowhides red
				if (cowhides.contains(p)) {
					g.setColor(Color.RED);
					g.drawPolygon(p.getPolygon(bot));
				}
			}
		}
	}
}
