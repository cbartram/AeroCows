import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import tasks.AttackCow;
import tasks.Task;
import tasks.Wait;
import util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ScriptManifest(author = "Aerodude30", name = "AeroCows", info = "Kills cows and banks hides in Lumbridge", version = 1.0, logo = "")
public final class AeroCows extends Script {
	private List<Task> tasks = new ArrayList<>();
	private String status; // Script status
	private long startTime;

	@Override
	public final void onStart() {
		startTime = System.currentTimeMillis();
		for(final Skill skill : new Skill[] { Skill.ATTACK, Skill.DEFENCE, Skill.STRENGTH, Skill.RANGED, Skill.MAGIC }) {
			getExperienceTracker().start(skill);
		}

		// Add all our tasks to the task list
		tasks.addAll(Arrays.asList(
				new AttackCow(this,"Attack Cow")
//				new Wait(this,"In Combat")
		));
	}

	@Override
	public final int onLoop() throws InterruptedException {
		for (Task task : tasks) {
				if (task.activate()) {
					status = task.getStatus();
					task.execute();
				}
		}
		status = "In combat waiting...";
		return random(150, 200);
	}

	@Override
	public final void onExit() {
		log("Closing Script...");
	}

	@Override
	public final void onMessage(final Message message) {
		if (message.getMessage().equalsIgnoreCase("Someone else is fighting that.")) {
			// We need to find a different cow
		}
	}

	@Override
	public void onPaint(final Graphics2D g) {
		final long runTime = System.currentTimeMillis() - startTime;

		g.setColor(new Color(120, 111, 100, 70));
		g.drawRect(0, 0, 140, 140);
		g.setColor(Color.white);

		g.drawString("Status: " + status, 10, 10);
		g.drawString("Runtime: " + Util.formatTime(runTime), 10, 30);
		g.drawString("Levels Gained: " + getExperienceTracker().getGainedLevels(Skill.DEFENCE), 10, 50);
		g.drawString("XP Gained: " + getExperienceTracker().getGainedXP(Skill.DEFENCE), 10, 70);
		g.drawString("XP/Hour: " + getExperienceTracker().getGainedXPPerHour(Skill.DEFENCE), 10, 90);
		g.drawString( "TTL: " + getExperienceTracker().getTimeToLevel(Skill.DEFENCE), 10, 120);

		Point pos = getMouse().getPosition();

		g.drawLine(pos.x - 5, pos.y + 5, pos.x + 5, pos.y - 5);
		g.drawLine(pos.x + 5, pos.y + 5, pos.x - 5, pos.y - 5);
	}
}
