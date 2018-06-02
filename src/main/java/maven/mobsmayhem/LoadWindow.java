package maven.mobsmayhem;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LoadWindow
{
	private final Group rootGroup;	
	int i = 0;
	double endLine = 25;
    
	public LoadWindow(Scene game, Stage primaryStage){
				
		String url = "assets/blackscreen.png";
		Image img = new Image(url);
		ImagePattern pattern = new ImagePattern(img);
		Font font = Font.loadFont(getClass().getResourceAsStream("assets/godfather.ttf"), 150);		
		Text t;
		Text progress;
		Text tip;
		Line l;
		
		rootGroup = new Group();
		
		l = new Line(25,535, endLine, 535);
		l.setStroke(Color.RED);
		l.setStrokeWidth(5);
		
		
		
		t = new Text(30,565,"Loading");
    	fontAndFill(t, font);
    	
    	progress = new Text(300,565, "0");
    	fontAndFill(progress, font);
    	progress.setStyle("-fx-font-size: 75");
    	
    	tip = new Text(350,275,"Tip: Avoid police to avoid going to jail");
    	fontAndFill(tip, font);
    	tip.setStyle("-fx-font-size: 50");
    
		game.setFill(pattern);
		
		
		
		
		
		Timeline loading = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		       progress.setText(Integer.toString(i));
		       l.setEndX(endLine);
		       i++;
		       endLine += 2.65;
		    }
		}));
		loading.setCycleCount(101);
		loading.play();
		
		
		
		
		
		Task<Void> sleeper = sleepTimer();
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
            	GameWindow gameScreen = new GameWindow(game,primaryStage);
        		primaryStage.getScene().setRoot(gameScreen.getRootGroup());
            }
        });
        new Thread(sleeper).start();
        
        
        rootGroup.getChildren().addAll(t,progress,tip,l);
		
		
	
	}

    public Group getRootGroup() {
    	return rootGroup;
    }
    
    private void fontAndFill(Text t, Font font) {
    	t.setFont(font);
    	t.setFill(Color.WHITE);
	}
    
    public static Task<Void> sleepTimer() {
		return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                	//Sleep for 100 * timeline length
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                	// Restore interrupted state...
                    Thread.currentThread().interrupt();
                }
                return null;
            }
        };
	}

}

