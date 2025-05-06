import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class MainMenuApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("PauseMenuScene.fxml"));
            primaryStage.setTitle("Pause Menu");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

             // Setup Background Music
            String musicFile = "background.mp3"; // Make sure this file is in the right place
            Media sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     @Override
     public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
