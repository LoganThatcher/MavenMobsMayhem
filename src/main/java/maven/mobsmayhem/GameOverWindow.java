package maven.mobsmayhem;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


public class GameOverWindow {
	static final Group root = new Group();
	static MediaPlayer mp;
	boolean muted;	
	Image unmuteImage;
	Image muteImage;
	ImageView unmuteIv;
	ImageView muteIv;

	public GameOverWindow (Scene go, Stage primaryStage)
    {	
		//Declarations
		Font font = Font.loadFont(getClass().getResourceAsStream("assets/godfather.ttf"), 90);
		Font font2 = Font.loadFont(getClass().getResourceAsStream("assets/godfather.ttf"), 150);
		AudioClip clickPlayer = new AudioClip(this.getClass().getResource("assets/click.mp3").toExternalForm());
		AudioClip click2Player = new AudioClip(this.getClass().getResource("assets/click2.mp3").toExternalForm());
    	Button newGame;
    	Button highScores;
    	Button menu;
    	
    	Text t;
    	Text g;
    	Text eText;
    	Image img;
    	ImagePattern pattern;
    	String style1 = "-fx-background-color: transparent; -fx-text-fill: #ff0000; -fx-font-size: 20px";
    	String style2 = "-fx-background-color: transparent; -fx-text-fill: #f8f8ff; -fx-font-size: 20px";

    	
    	//Main Title
    	t = new Text(225,160,"Game Over");
    	t.setFont(font2);
    	t.setFill(Color.RED);
    	
    	g = new Text(225,300,"You Have Failed To");
    	g.setFont(font);
    	g.setFill(Color.WHITE);
    	
    	eText = new Text(225, 400, "Become The Godfather");
    	eText.setFont(font);
    	eText.setFill(Color.WHITE);
    	
  
    	//Buttons
    	newGame = new Button("New Game");
    	newGame.setFont(font);
    	highScores = new Button("High Scores");
    	highScores.setFont(font);
    	menu = new Button("Main Menu");
    	menu.setFont(font);
    	
   	
		img = new Image("assets/blackscreen.png");
		
		pattern = new ImagePattern(img);
		go.setFill(pattern);
        
        newGame.setLayoutX(215);
        newGame.setLayoutY(450);
        highScores.setLayoutX(360);
        highScores.setLayoutY(450);
        menu.setLayoutX(515);
        menu.setLayoutY(450);
        
        newGame.setPrefSize(150, 100);
        highScores.setPrefSize(150, 100);
        menu.setPrefSize(150, 100);
        
        
        newGame.setStyle(style1);
        newGame.setOnMouseEntered(e -> {
        	newGame.setStyle(style2);
        	clickPlayer.play();
        });
        newGame.setOnMouseExited(e -> newGame.setStyle(style1));
        
               
        highScores.setStyle(style1);
        highScores.setOnMouseEntered(e -> {
        	highScores.setStyle(style2);
        	clickPlayer.play();
        });
        highScores.setOnMouseExited(e -> highScores.setStyle(style1));
        
         
        menu.setStyle(style1);
		menu.setOnMouseEntered(e -> {
			menu.setStyle(style2);
			clickPlayer.play();
		});
		menu.setOnMouseExited(e -> menu.setStyle(style1));
				        
			
        root.getChildren().addAll(newGame, highScores, menu, t, g, eText);
        
        
    	newGame.setOnAction(e -> {
    		
    		LoadWindow load = new LoadWindow(go, primaryStage);
    		primaryStage.getScene().setRoot(load.getRootGroup());
    		click2Player.play();
    		
    	});
    	
    	highScores.setOnAction(e -> {
    		HighScoreWindow hs = new HighScoreWindow(go, primaryStage);
    		primaryStage.getScene().setRoot(hs.getRootGroup());
    		click2Player.play();
    	});
    	
    	menu.setOnAction(e -> {
    		MainMenuWindow main = new MainMenuWindow(go, primaryStage);
    		primaryStage.getScene().setRoot(main.getRootGroup());
    		click2Player.play();
    	});
    	
    }
	
	public Group getRootGroup() {
		return root;
	}  


}
