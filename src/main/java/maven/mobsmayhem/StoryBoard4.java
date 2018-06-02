package maven.mobsmayhem;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
	
public class StoryBoard4 {
	
		private final Group rootGroup;	
		boolean change;
	    
		public StoryBoard4(Scene game, Stage primaryStage){
			rootGroup = new Group();
			Button skip = null;
			Button next = null;

			StoryBoard1.setupStoryBoard(game, "assets/sb4.png", skip, next);
			
			Task<Void> sleeper = LoadWindow.sleepTimer();
	        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            @Override
	            public void handle(WorkerStateEvent event) {
	            	if(change) {
		            	LoadWindow load = new LoadWindow(game,primaryStage);
		        		primaryStage.getScene().setRoot(load.getRootGroup());
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
	    		
		    	LoadWindow load = new LoadWindow(game,primaryStage);
	    		primaryStage.getScene().setRoot(load.getRootGroup());
	    		change = false;
	    		
	    	});
		    
		    rootGroup.getChildren().addAll(skip,next);
			
		
		}

	    public Group getRootGroup() {
	    	return rootGroup;
	    }
	    
	    

}