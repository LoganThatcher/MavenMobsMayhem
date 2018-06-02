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
	
public class StoryBoard3 {
	
		private final Group rootGroup;	
		boolean change = true;
	    
		public StoryBoard3(Scene game, Stage primaryStage){
			rootGroup = new Group();
			Button skip = null;
			Button next = null;

			StoryBoard1.setupStoryBoard(game, "assets/sb3.png", skip, next);
			
			Task<Void> sleeper = LoadWindow.sleepTimer();
	        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            @Override
	            public void handle(WorkerStateEvent event) {
	            	if(change) {
	            	StoryBoard4 sb4 = new StoryBoard4(game,primaryStage);
	        		primaryStage.getScene().setRoot(sb4.getRootGroup());
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
	    		
		    	StoryBoard4 sb4 = new StoryBoard4(game,primaryStage);
	    		primaryStage.getScene().setRoot(sb4.getRootGroup());
	    		change = false;
	    		
	    	});
		
		    rootGroup.getChildren().addAll(skip,next);
		}

	    public Group getRootGroup() {
	    	return rootGroup;
	    }
	    
	    

}