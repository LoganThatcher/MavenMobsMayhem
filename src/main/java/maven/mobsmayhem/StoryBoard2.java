package maven.mobsmayhem;

import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
	
public class StoryBoard2 {
	
	private final Group rootGroup;	
	boolean change = true; 
	
	public StoryBoard2(Scene game, Stage primaryStage){
		rootGroup = new Group();
		Button skip = null;
		Button next = null;

		StoryBoard1.setupStoryBoard(game, "assets/sb2.png", skip, next);
		
		Task<Void> sleeper = LoadWindow.sleepTimer();
	    sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	        @Override
	        public void handle(WorkerStateEvent event) {
	        		if(change) {
		            	StoryBoard3 sb3 = new StoryBoard3(game,primaryStage);
		        		primaryStage.getScene().setRoot(sb3.getRootGroup());
	        		}
	            }
	    });
	    new Thread(sleeper).start();
	    
	    skip.setOnAction(e -> {
    		
	    	LoadWindow load = new LoadWindow(game,primaryStage);
    		primaryStage.getScene().setRoot(load.getRootGroup());
    		change = false;
    		
    	});
	    next.setOnAction(e -> {
    		
	    	StoryBoard3 sb3 = new StoryBoard3(game,primaryStage);
    		primaryStage.getScene().setRoot(sb3.getRootGroup());
    		change = false;
    	});
	    
		
		rootGroup.getChildren().addAll(skip,next);
		
	
	}
	
	public Group getRootGroup() {
		return rootGroup;
	}
	
	

}

