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

public class HighScoreWindow {

	private final Group rootGroup;
	private final Text t;
	private final Text t2;
	private final Text t3;
	private final Text t4;
	private final Text t5;
	private final Text t6;

	public HighScoreWindow(Scene hs, Stage primaryStage)
	{
		//Upload music and fonts
		AudioClip clickPlayer = new AudioClip(this.getClass().getResource("assets/click.mp3").toExternalForm());
		AudioClip click2Player = new AudioClip(this.getClass().getResource("assets/click2.mp3").toExternalForm());
		Font font = Font.loadFont(getClass().getResourceAsStream("assets/godfather.ttf"), 120);
		
		//Declarations
		rootGroup = new Group();
		t = new Text();
		t2 = new Text();
		t3 = new Text();
		t4 = new Text();
		t5 = new Text();
		t6 = new Text();
		Button back = new Button("Back To Main Menu");
		String url = "assets/blackscreen.png";
		Image img = new Image(url);
		ImagePattern pattern = new ImagePattern(img);
		hs.setFill(pattern);

    	//Settings Title and Mute Title
		setText(t,"High Scores", 300.0, 100.0, font);
		font = Font.loadFont(getClass().getResourceAsStream("assets/godfather.ttf"), 40);
		setText(t2,"Jason        300", 375.0, 200.0, font);
		setText(t3,"Danica       280", 375.0, 245.0, font);
		setText(t4,"Anshula      270", 375.0, 290.0, font);
		setText(t5,"Logan        250", 375.0, 335.0, font);
		setText(t6,"Lindsey      200", 375.0, 380.0, font);
		
		//Button Positioning and Size
		back.setLayoutX(600);
		back.setLayoutY(500);
		back.setPrefSize(300, 100);
		
		//Mouse Actions for Back Button
		back.setStyle("-fx-background-color: transparent; -fx-text-fill: #ff0000; -fx-font-size: 20px");
		back.setOnMouseEntered(e -> {
        	back.setStyle("-fx-background-color: transparent; -fx-text-fill: #f8f8ff; -fx-font-size: 20px");
        	clickPlayer.play();
        });
		back.setOnMouseExited(e -> back.setStyle("-fx-background-color: transparent; -fx-text-fill: #ff0000; -fx-font-size: 20px"));
		rootGroup.getChildren().addAll(back, t, t2, t3, t4, t5, t6);
		
		//Button Actions
		back.setOnAction(e -> {
			click2Player.play();
			MainMenuWindow main = new MainMenuWindow(hs, primaryStage);
			primaryStage.getScene().setRoot(main.getRootGroup());
		});  
	}

	public Group getRootGroup() {
		return rootGroup;
	}  
	
	private void setText(Text t, String s, double x, double y, Font f) {
		t.setFont(f);
		t.setText(s);
		t.setFill(Color.WHITE);
		t.setX(x);
		t.setY(y);
	}

}
