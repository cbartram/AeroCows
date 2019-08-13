package util;

import org.osbot.rs07.utility.ConditionalSleep;

import java.util.function.BooleanSupplier;

public final class Sleep extends ConditionalSleep {

	private final BooleanSupplier condition;

	public Sleep(final BooleanSupplier condition, final int timeout) {
		super(timeout);
		this.condition = condition;
	}

	@Override
	public final boolean condition() throws InterruptedException {
		return condition.getAsBoolean();
	}

	/**
	 * Sleeps for a specified number of ms
	 * @param ms Integer number of milliseconds to sleep for
	 * @return new Sleep object
	 */
	public static boolean sleepFor(final int ms) {
		return new Sleep(() -> true, ms).sleep();
	}


	/**
	 * Sleeps a specified duration until a specific condition is met
	 * @param condition BooleanSupplier Can be a lambda function to specify a condition to sleep until
	 * @param timeout Integer the amount of time to sleep in between checks for the given condition
	 * @return new Sleep object
	 */
	public static boolean sleepUntil(final BooleanSupplier condition, final int timeout) {
		return new Sleep(condition, timeout).sleep();
	}
}
