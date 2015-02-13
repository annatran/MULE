package data;

import javafx.application.Platform;
import driver.GameController;
import driver.GameUI;

/**
 * Task for adding delay after choosing to Gamble.
 * 
 * @author Team12
 */
public class GambleDelayRunnableTask implements Runnable{
	Timer timer;
	
	/**
	 * Creates a task with 1 second of delay.
	 */
	public GambleDelayRunnableTask(){
		timer = new Timer(1);
	}

	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer.decrementTimer();
		while(timer.getTime()>0){
			timer.decrementTimer();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("```````````````````````````````````````````````");
		Platform.runLater(new Runnable() {
			public void run() {
				GameController.getInstance().getRound().nextTurn();
				GameUI.getInstance().startGame();
			}
		});
	}
}
