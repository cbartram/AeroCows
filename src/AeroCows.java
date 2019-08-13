import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.util.GraphicUtilities;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import tasks.AttackCow;
import tasks.CollectHide;
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
				new AttackCow(this,"Attack Cow"),
				new CollectHide(this, "Collect Hide"),
				new WalkToCows(this, "Walk to Cows")
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
		// TODO: Impl if needed
	}

	@Override
	public void onPaint(final Graphics2D g) {
		final long runTime = System.currentTimeMillis() - startTime;

		// Fill the background color rectangle
		g.setColor(new Color(120, 111, 100, 150));
		g.fillRect(0,0,200,140);
		g.setColor(Color.RED);

		// Create a border
		g.setColor(Color.CYAN);
		g.drawRect(0, 0, 200, 140);

		g.setColor(Color.WHITE);
		g.drawString("Status: " + status, 10, 30);
		g.drawString("Runtime: " + Util.formatTime(runTime), 10, 50);
		g.drawString("Levels Gained: " + getExperienceTracker().getGainedLevels(Skill.DEFENCE), 10, 70);
		g.drawString("XP Gained: " + getExperienceTracker().getGainedXP(Skill.DEFENCE), 10, 90);
		g.drawString("XP/Hour: " + getExperienceTracker().getGainedXPPerHour(Skill.DEFENCE), 10, 110);
		g.drawString( "TTL: " + Util.formatTime(getExperienceTracker().getTimeToLevel(Skill.DEFENCE)), 10, 130);

		Point pos = getMouse().getPosition();

		g.drawLine(pos.x - 5, pos.y + 5, pos.x + 5, pos.y - 5);
		g.drawLine(pos.x + 5, pos.y + 5, pos.x - 5, pos.y - 5);

//		Polygon p = myPosition().getPolygon(bot);
		g.setColor(Color.CYAN);
		Area a = myPlayer().getArea(4);
		for(Position p : a.getPositions()) {
			g.drawPolygon(p.getPolygon(bot));
		}
	}
}
