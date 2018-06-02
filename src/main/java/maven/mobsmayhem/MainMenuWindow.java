package maven.mobsmayhem;

import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuWindow {

	private final Group rootGroup;
	private final Text t;
	Image img;
	ImagePattern pattern;

	public MainMenuWindow(Scene main, Stage primaryStage)
	{
		//Background Image		
		img = new Image("assets/start_screen.jpg");
				
		pattern = new ImagePattern(img);
		main.setFill(pattern);
			
		//Declarations
	    Font font = Font.loadFont(getClass().getResourceAsStream("assets/godfather.ttf"), 150);		
	    AudioClip clickPlayer = new AudioClip(this.getClass().getResource("assets/click.mp3").toExternalForm());
		AudioClip click2Player = new AudioClip(this.getClass().getResource("assets/click2.mp3").toExternalForm());  	
		Button startGame;
		Button howToPlay;
		Button settings;
		
		String style1 = "-fx-background-color: transparent; -fx-text-fill: #ff0000; -fx-font-size: 20px";
		String style2 = "-fx-background-color: transparent; -fx-text-fill: #f8f8ff; -fx-font-size: 20px";
				 	
	    //Main Title
	    rootGroup = new Group();
		t = new Text(225,160,"Mob Mayhem");
	    t.setFont(font);
		t.setFill(Color.RED);
				    	
		//Buttons
		startGame = new Button("Start Game");
	    startGame.setFont(font);
	    howToPlay = new Button("How To Play");
		howToPlay.setFont(font);
		settings = new Button("Settings");
		settings.setFont(font);
				    	
		menuLayout(startGame, howToPlay, settings, style1, style2, clickPlayer);	        				        
				        
	    rootGroup.getChildren().addAll(startGame, howToPlay, settings, t);
				        
				        
		//Button Actions
		startGame.setOnAction(e -> {	    		
			StoryBoard1 sb1 = new StoryBoard1(main,primaryStage);
    		primaryStage.getScene().setRoot(sb1.getRootGroup());
    		click2Player.play();	    		
		});
				    	
		settings.setOnAction(e -> {
		    SettingsWindow set = new SettingsWindow(main,primaryStage);
			primaryStage.getScene().setRoot(set.getRootGroup());
			click2Player.play();
		});

		howToPlay.setOnAction( e -> {
			InstructionsWindow instruct = new InstructionsWindow(main, primaryStage);
			primaryStage.getScene().setRoot(instruct.getRootGroup());
			click2Player.play();
		});

	}

	public Group getRootGroup() {
		return rootGroup;
	} 
	
	public static void menuLayout(Button startGame, Button howToPlay, Button settings, String style1, String style2, AudioClip clickPlayer) {
		//Button Positioning and Sizing
		howToPlay.setLayoutX(250);
		howToPlay.setLayoutY(400);
		settings.setLayoutX(375);
		settings.setLayoutY(400);
		startGame.setLayoutX(500);
		startGame.setLayoutY(400);
				        
		startGame.setPrefSize(150, 100);
		howToPlay.setPrefSize(150, 100);
		settings.setPrefSize(150, 100);
				        
		//Mouse on Button Actions
		settings.setStyle(style1);
		settings.setOnMouseEntered(e -> {
			settings.setStyle(style2);
			clickPlayer.play();
		});
		settings.setOnMouseExited(e -> settings.setStyle(style1));
				        
		startGame.setStyle(style1);
		startGame.setOnMouseEntered(e -> {
			startGame.setStyle(style2);
			clickPlayer.play();
		});
		startGame.setOnMouseExited(e -> startGame.setStyle(style1));
				        
		howToPlay.setStyle(style1);
		howToPlay.setOnMouseEntered(e -> {
			howToPlay.setStyle(style2);
			clickPlayer.play();
		});
		howToPlay.setOnMouseExited(e -> howToPlay.setStyle(style1));

	}
}


