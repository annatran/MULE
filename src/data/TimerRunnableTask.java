package data;

import javafx.application.Platform;
import driver.GameController;
import driver.GameUI;

public class TimerRunnableTask implements Runnable {

	private Timer timer;
	private int initialTime;
	private volatile boolean running = true;
	/*
	 * Sets running to false, terminating the task
	 */
	public void terminate() {
		running = false;
	}
	/*
	 * Creates a new runnable task based on a timer
	 */
	public TimerRunnableTask(Timer timer) {
		super();
		this.timer = timer;
		initialTime = timer.getTime();
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		GameController.getInstance().updateTime(timer.getTime(), initialTime);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.decrementTimer();
		while (timer.getTime() > 0 && running) {
			timer.decrementTimer();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();			
			}
			if (running)
				GameController.getInstance().updateTime(timer.getTime(), initialTime);
		}
		if (running)
			Platform.runLater(new Runnable() {
				public void run() {
					GameController.getInstance().timeIsUp();
				}
			});
			
	}
	
}
