package data;

import java.util.Random;

import driver.GameController;
import driver.GameUI;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Holds information for Event
 *
 */
public abstract class Event {
	/*
	 * @return boolean true if the event is a bad event
	 */
	public abstract boolean isBadEvent();
	/*
	 * performs event
	 * @return String representing the description of the event
	 */
	public abstract String performEvent();
	final private static double EVENT_CHANCE = 0.27;
	final private static Event[] events = {new ReceivedPackageEvent(),new MuseumBoughtYourComputerEvent(),new FoundDeadMooseRatEvent(),
											new FlyingCatBugsEvent(), new WanderingStudentEvent(),new UgaStudentEvent(), new SpaceGypsyEvent()};
	final private int[] roundFactors = {25,25,25,50,50,50,50,75,75,75,75,100};
	/*
	 * @return int the round factor that determines event weights
	 */
	public int getRoundFactor(){
		return roundFactors[(GameController.getInstance().getRound().getCurrentRound()-1)%12];
	}
	
	private static boolean getIfEvent(){
		Random rand = new Random();
		double randomDouble = rand.nextDouble();
		return randomDouble<=EVENT_CHANCE;
	}
	
	private static Event getRandomEvent(){
		Random rand = new Random();
		Event output =  events[rand.nextInt(events.length)];
		if(output.isBadEvent()&&GameController.getInstance().getRound().getIfCurrentPlayerHasLowestScore()) return null;
		return output;
	}
	/*
	 * Randomly selects and performs an event. If the event is bad and it is the lowest score player's turn, the event does not occur.
	 */
	public static void performRandomEvent(){
		if(!getIfEvent()) return;
		Event toDo = getRandomEvent();
		if(toDo == null) return;
		String message = toDo.performEvent();
		final Stage dialogStage = new Stage();
		Button button = new Button("OK");
		button.setOnMouseClicked(new EventHandler(){
			@Override
			public void handle(javafx.event.Event arg0) {
				dialogStage.close();	
			}
		});
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.setScene(new Scene(VBoxBuilder.create().
		    children(new Text(message), button).
		    alignment(Pos.CENTER).padding(new Insets(5)).build()));
		dialogStage.show();
	}
}
