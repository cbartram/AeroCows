import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(author = "Aerodude30", name = "AeroCows", info = "Kills cows and banks hides in Lumbridge", version = 1.0, logo = "")
public final class AeroCows extends Script {

	@Override
	public final void onStart() {
		log("This will be printed to the logger when the script starts");
	}

	@Override
	public final int onLoop() throws InterruptedException {
		return 0;
	}

	@Override
	public final void onExit() {
		log("This will be printed to the logger when the script exits");
	}

	@Override
	public final void onMessage(final Message message) {
		log("A message arrived in the chatbox: " + message.getMessage());
	}

	@Override
	public void onPaint(final Graphics2D g) {
		g.drawString("Some text", 10, 10);
	}
}