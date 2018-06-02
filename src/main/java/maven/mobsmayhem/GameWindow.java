package maven.mobsmayhem;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;

public class GameWindow
{
	private static final Logger LOGGER = Logger.getLogger(GameWindow.class.getName());
	private static final int KEYBOARD_MOVEMENT_DELTA = 20;
	private static final int mapHeight = 600;
	private static final int mapWidth = 900;
	
	// Setting image patterns to load images on top of the circles
    private static final Image imCop = new Image("assets/cophead.png");
    private static final ImagePattern ip_cop = new ImagePattern(imCop);        
    private static final Image imCit = new Image("assets/civilianhead.png");
    private static final ImagePattern ip_cit = new ImagePattern(imCit);
	
	ArrayList<Circle> moneys = new ArrayList<>();
	Iterator<Circle> iter = moneys.iterator();
	private final Group rootGroup;	
	private int lives;
	private int score;
	AudioClip clickPlayer;
	Text lifeCounter;
	Text scoreCounter;
	String scoreHeading = "Score: ";
	
	Random ran = new Random();

	public GameWindow(Scene game, Stage primaryStage){
		String url = "assets/cobblestone.jpg";
		Image img = new Image(url);
		ImagePattern pattern = new ImagePattern(img);
		Timeline circMover;
		
		Circle cop = new Circle();
		Circle cit = new Circle();
		Circle player = new Circle();
		
		lives = 3;
		lifeCounter = new Text(10,20,"Lives: " + lives);
		lifeCounter.setFill(Color.RED);
		score = 0;
		scoreCounter = new Text(830,20,scoreHeading + score);
		scoreCounter.setFill(Color.RED);
		
		game.setFill(pattern);
		rootGroup = new Group();

	    //Background
	    GridPane grid = new GridPane();
	    grid.setAlignment(Pos.TOP_LEFT);
	    grid.setHgap(25);
	    grid.setVgap(25);
	    grid.setPadding(new Insets(25, 25, 25, 25));
	     
	    String roofurl = "assets/halloween-house.png";
	    Image roofimg = new Image(roofurl);
	    ImageView roof;
	      
	    for (int i = 0; i < 5; i++) {
	       for (int j = 0; j < 8; j++) {
	          roof = new ImageView(roofimg);
	          roof.setFitHeight(90);
	          roof.setFitWidth(85);
	          grid.add(roof, j, i);
	       }
	    }
	      
	    rootGroup.getChildren().add(grid);
			
		circMover = new Timeline(new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        moveCircleTowardPlayer(cop,player);
		        moveCircleRandomly(cit);
		        checkCollisions(cop, player, game, primaryStage);
		        checkCollisions(cit, player, game, primaryStage);
		        
		        if(!moneys.isEmpty()) {
		        	Circle currMoney = moneys.get(0);
		        	checkCollisions(currMoney, cop, game, primaryStage);
		        }
		    }
		}));
		circMover.setCycleCount(Timeline.INDEFINITE);
		circMover.play();
		
		// Character placements and settings
		Random rand = new Random();
		int pos = rand.nextInt(mapWidth);
        player.setCenterX(mapWidth/2);
        player.setCenterY(mapHeight/2);
        player.setRadius(15);
        Image imPlayer = new Image("assets/mobbosshead.png");
        player.setFill(new ImagePattern(imPlayer));
        
        cop.setCenterX(140);
        cop.setCenterY(140);
        cop.setRadius(10);
        cop.setFill(ip_cop);
        
        cit.setCenterX(pos);
        pos = rand.nextInt(mapHeight);
        cit.setCenterY(pos);
        cit.setRadius(10);
        cit.setFill(ip_cit);
       
        
        rootGroup.getChildren().addAll(player, cop, cit, lifeCounter, scoreCounter);
        moveCircleOnKeyPress(game, player,primaryStage,circMover);
	}

	private void checkCollisions(Circle a, Circle b, Scene game, Stage primaryStage) {
		if(a.getBoundsInParent().intersects(b.getBoundsInParent())) {
			if (a.getFill() == ip_cop) {
				if(lives == 1) {
					GameOverWindow go = new GameOverWindow(game,primaryStage);
		    		primaryStage.getScene().setRoot(go.getRootGroup());
				}
				lives -= 1;
				lifeCounter.setText("Lives: " + lives);
				a.setCenterX(140);
				a.setCenterY(140);
				b.setRadius(15);
				b.setCenterX(mapWidth/2);
		        b.setCenterY(mapHeight/2);
			}
			else if(a.getFill() == Color.GREEN) {
				a.setFill(Color.TRANSPARENT);
				moneys.remove(a);
			}
			else{
				a.setRadius(0);
				rootGroup.getChildren().remove(a);
				score += 10;
				scoreCounter.setText(scoreHeading + score);
				Random rand = new Random();
				int pos = rand.nextInt(mapWidth);
				a.setCenterX(pos);
		        pos = rand.nextInt(mapHeight);
		        a.setCenterY(pos);
		        a.setRadius(10);
		        a.setFill(ip_cit);
		        rootGroup.getChildren().add(a);
			}
		}
			
	}
	
	private void moveCircleRandomly(final Circle cop) {
		int direc = 1;
		Random rand = new Random();
		direc = rand.nextInt(4)+1;	
		
		switch (direc) {
	    	case 1:
	    		if(cop.getCenterY() - KEYBOARD_MOVEMENT_DELTA > 0)
	    			cop.setCenterY(cop.getCenterY() - KEYBOARD_MOVEMENT_DELTA); break;
	    	case 2: 
	    		if(cop.getCenterX() + KEYBOARD_MOVEMENT_DELTA < mapWidth)
	    			cop.setCenterX(cop.getCenterX() + KEYBOARD_MOVEMENT_DELTA); break;
	    	case 3: 
	    		if(cop.getCenterY() + KEYBOARD_MOVEMENT_DELTA < mapHeight)
	    			cop.setCenterY(cop.getCenterY() + KEYBOARD_MOVEMENT_DELTA); break;
	    	case 4:  
	    		if(cop.getCenterX() - KEYBOARD_MOVEMENT_DELTA > 0)
	    			cop.setCenterX(cop.getCenterX() - KEYBOARD_MOVEMENT_DELTA); break;
	    	default:
	    		break;
		}
	}

	private void moveCircleTowardPlayer(final Circle cop,final Circle player) {
		double playerX = player.getCenterX();
		double playerY = player.getCenterY();
		double copX = cop.getCenterX();
		double copY = cop.getCenterY();
		
		if(!moneys.isEmpty()){
			playerX = moneys.get(0).getCenterX();
			playerY = moneys.get(0).getCenterY();
		}
		
		if(Math.abs(playerX - copX) > Math.abs(playerY - copY)){
			//Move horizontally
			if(playerX > copX) {
				cop.setCenterX(cop.getCenterX() + KEYBOARD_MOVEMENT_DELTA);
			}else {
				cop.setCenterX(cop.getCenterX() - KEYBOARD_MOVEMENT_DELTA);
			}
		}
		else {
			//Move vertically
			if(playerY > copY) {
				cop.setCenterY(cop.getCenterY() + KEYBOARD_MOVEMENT_DELTA);
			}else {
				cop.setCenterY(cop.getCenterY() - KEYBOARD_MOVEMENT_DELTA);
			}
		}
	}
    
    private void moveCircleOnKeyPress(Scene scene, final Circle circle, Stage primaryStage, Timeline circMover) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
          @Override public void handle(KeyEvent event) {
            switch (event.getCode()) {
            	case UP:
            		if(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA > 0)
            			circle.setCenterY(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA); break;
            	case RIGHT: 
            		if(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA < mapWidth)
            			circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA); break;
            	case DOWN: 
            		if(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA < mapHeight)
            			circle.setCenterY(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA); break;
            	case LEFT:  
            		if(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA > 0)
            			circle.setCenterX(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA); break;
            	case ESCAPE: 
            		pause(primaryStage, scene,circMover); break;
            	case SPACE: 
            		if(score >= 10) {
            			score = score - 10;
            			scoreCounter.setText(scoreHeading + Integer.toString(score));
	            		Circle money = new Circle();
	            		money.setCenterY(circle.getCenterY());
	            	    money.setCenterX(circle.getCenterX());
	            	    money.setRadius(5);
	            	    money.setFill(Color.GREEN);
	            	    rootGroup.getChildren().add(money);
	            	    moneys.add(money);
            		}
            	    break;
            	default:
            		break;
          
            }
          }
        });
      }

    
      private void pause(Stage primaryStage, Scene scene, Timeline circMover){
    	circMover.pause();
    
		try {
			clickPlayer = new AudioClip(getClass().getResource("assets/click2.mp3").toURI().toString());
			clickPlayer.play();
		} catch (URISyntaxException e1) {
			LOGGER.log(Level.FINE, "context", e1);
		}
    	
     	rootGroup.setEffect(new GaussianBlur());
    	Font font = Font.loadFont(getClass().getResourceAsStream("assets/godfather.ttf"), 150);	
      	
	  	VBox pauseRoot = new VBox(5);
	  	pauseRoot.prefWidthProperty().bind(primaryStage.widthProperty());
	  	pauseRoot.prefHeightProperty().bind(primaryStage.heightProperty());

	  	
	  	Text pauseTitle = new Text("Paused");
	  	pauseTitle.setFont(font);
	  	pauseTitle.setFill(Color.WHITE);
	  	
	  	pauseRoot.getChildren().add(pauseTitle);
	    pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
	    pauseRoot.setAlignment(Pos.CENTER);
	    pauseRoot.setPadding(new Insets(20));
	    
	      
	    Button resume = new Button("RESUME");
	    Button restart = new Button("RESTART");
	    Button quit = new Button("QUIT");
	    pauseRoot.getChildren().addAll(resume,restart,quit);

	    
	    Stage popupStage = new Stage(StageStyle.TRANSPARENT);
	    popupStage.initOwner(primaryStage);
	    popupStage.initModality(Modality.APPLICATION_MODAL);
	    popupStage.setScene(new Scene(pauseRoot, Color.BLACK));
	    
	    
	    resume.setOnAction(e ->{
	    	rootGroup.setEffect(null);
	      	popupStage.hide();
	      	circMover.play();
	    });
	    quit.setOnAction(e -> {
			MainMenuWindow main = new MainMenuWindow(scene, primaryStage);
			primaryStage.getScene().setRoot(main.getRootGroup());
			popupStage.hide();
		});  
	    restart.setOnAction(e -> {
			LoadWindow load = new LoadWindow(scene, primaryStage);
			primaryStage.getScene().setRoot(load.getRootGroup());
			popupStage.hide();
		});  
	    pauseRoot.setOnKeyPressed(new EventHandler<KeyEvent>() {
	          @Override public void handle(KeyEvent event) {
	        	if (event.getCode().equals(KeyCode.ESCAPE)) {
	        		clickPlayer.play();
                 	circMover.play();
            		rootGroup.setEffect(null);
        	      	popupStage.hide();	
				}
	          }
	    });
	      
	    popupStage.show();    
    }
    
    public Group getRootGroup() {
    	return rootGroup;
    }

}