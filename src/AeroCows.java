import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.util.GraphicUtilities;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import tasks.AttackCow;
import tasks.Bank;
import tasks.CollectHide;
import tasks.DepositHides;
import tasks.Task;
import tasks.WalkToCows;
import util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ScriptManifest(author = "Aerodude30", name = "AeroCows", info = "Kills cows and banks hides in Lumbridge", version = 1.0, logo = "")
public final class AeroCows extends Script {
	private List<Task> tasks = new ArrayList<>();
	private String status = "Initializing Script"; // Script status
	private long startTime;
	private Skill[] skills = { Skill.ATTACK, Skill.DEFENCE, Skill.STRENGTH, Skill.RANGED, Skill.MAGIC };

	@Override
	public final void onStart() {
		startTime = System.currentTimeMillis();
		for(final Skill skill : skills) {
			getExperienceTracker().start(skill);
		}

		// Add all our tasks to the task list
		tasks.addAll(Arrays.asList(
				new DepositHides(this, "Depositing Hides"),
				new Bank(this, "Banking"),
				new AttackCow(this,"Attack Cow"),
				new CollectHide(this, "Collect Hide"),
				new WalkToCows(this, "Walk to Cows")
		));
	}

	@Override
	public final int onLoop() {
		try {
			for (Task task : tasks) {
				if (task.activate()) {
					status = task.getStatus();
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

		// Draw the main text
		g.drawString("AeroCows", 10, 20);

		g.setColor(Color.WHITE);
		g.drawString("Status: " + status, 10, 40);
		g.drawString("Runtime: " + Util.formatTime(runTime), 10, 60);
		g.drawString("Levels Gained: " + getExperienceTracker().getGainedLevels(Skill.DEFENCE), 10, 80);
		g.drawString("XP Gained: " + getExperienceTracker().getGainedXP(Skill.DEFENCE), 10, 100);
		g.drawString("XP/Hour: " + getExperienceTracker().getGainedXPPerHour(Skill.DEFENCE), 10, 120);
		g.drawString( "TTL: " + Util.formatTime(getExperienceTracker().getTimeToLevel(Skill.DEFENCE)), 10, 140);


		// Draw the mouse cursor
		Point pos = getMouse().getPosition();

		g.drawLine(pos.x - 5, pos.y + 5, pos.x + 5, pos.y - 5);
		g.drawLine(pos.x + 5, pos.y + 5, pos.x - 5, pos.y - 5);


		// Paint the tiles
		g.setColor(Color.CYAN);
		Area nearby = myPlayer().getArea(7);


		Area bank = Banks.LUMBRIDGE_UPPER;
		for(Position p : bank.getPositions()) {
			g.drawPolygon(p.getPolygon(bot));
		}
	}
}
