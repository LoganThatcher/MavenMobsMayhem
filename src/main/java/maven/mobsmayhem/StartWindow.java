package maven.mobsmayhem;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
 
public class StartWindow extends Application 
{
	private static final int MAPHEIGHT = 600;
	private static final int MAPWIDTH = 900;
	static final Group root = new Group();
	static MediaPlayer mp;
	boolean muted;	
	Scene home = new Scene(root, MAPWIDTH, MAPHEIGHT);
	Image img;
	ImagePattern pattern;
	static Stage stage1;
	
	public static void main(String[] args) 
    {
        launch(args);
    }
		
	@Override
    public void start(Stage stage) throws Exception
    {	   
		
		//Background Image
		img = new Image("assets/start_screen.jpg");
		
		pattern = new ImagePattern(img);
		home.setFill(pattern);
        stage.setTitle("Mob Mayhem");
	
        //Declarations
		Font font = Font.loadFont(getClass().getResourceAsStream("assets/godfather.ttf"), 150);		
		AudioClip clickPlayer = new AudioClip(getClass().getResource("assets/click.mp3").toURI().toString());
		AudioClip click2Player = new AudioClip(getClass().getResource("assets/click2.mp3").toURI().toString());	    	
		Button startGame;
		Button howToPlay;
		Button settings;
	    Text t;
	   	Media m;
	   	String style1 = "-fx-background-color: transparent; -fx-text-fill: #ff0000; -fx-font-size: 20px";
	   	String style2 = "-fx-background-color: transparent; -fx-text-fill: #f8f8ff; -fx-font-size: 20px";
	   	
		 	
    	//Main Title
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
    	
    	//Music
    	m = new Media(getClass().getResource("assets/godfather_theme.mp3").toURI().toString());
	    mp = new MediaPlayer(m);
	    mp.setVolume(0.4);
	    mp.play(); 
	    muted = false;
        
	    MainMenuWindow.menuLayout(startGame, howToPlay, settings, style1, style2, clickPlayer);
        
        root.getChildren().addAll(startGame, howToPlay, settings, t);
        
        
        //Button Actions
    	startGame.setOnAction(e -> {
    		
    		StoryBoard1 sb1 = new StoryBoard1(home,stage);
    		stage.getScene().setRoot(sb1.getRootGroup());
    		click2Player.play();
    		
    	});
    	
    	settings.setOnAction(e -> {
    		SettingsWindow set = new SettingsWindow(home, stage);
    		stage.getScene().setRoot(set.getRootGroup());
    		click2Player.play();
    	});

    	howToPlay.setOnAction( e -> {
    		InstructionsWindow instruct = new InstructionsWindow(home, stage);
    		stage.getScene().setRoot(instruct.getRootGroup());
    		click2Player.play();
    	});
    	
    	stage.setScene(home);
    	stage.show();
    	
    	stage1 = stage;

    }
	
	public Group getRootGroup() {
		return root;
	}
	
	public static Stage getStage() {
		return stage1;
	}
}