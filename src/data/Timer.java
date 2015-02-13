package data;

public class Timer {

	private int time;
	
	/**
	 * Constructs a timer with the specified seconds.
	 * 
	 * @param seconds
	 */
	public Timer(int seconds) {
		time = seconds;
	}
	
	/**
	 * Decrements the timer by one second and returns the amount of time left in the timer 
	 * after the decrement.
	 * 
	 * @return time
	 */
	public int decrementTimer() {
		time--;
		
		return time;
	}
	
	/**
	 * Returns the amount of time left in the timer.
	 * 
	 * @return time
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * Sets the timer to the specified number of seconds and returns the new time on the timer.
	 * @return time
	 */
	public int setTime(int newTime) {
		time = newTime;
		return time;
	}
	
}
