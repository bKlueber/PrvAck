import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title
        Text title = new Text("My Game");
        title.setFont(Font.font("Arial", 36));

        // Buttons
        Button continueBtn = new Button("Continue Game");
        Button loadBtn = new Button("Load Save");
        Button settingsBtn = new Button("Settings");
        Button newGameBtn = new Button("New Game");

        // Button Actions
        continueBtn.setOnAction(_ -> System.out.println("Continuing game..."));
        loadBtn.setOnAction(_ -> System.out.println("Loading save..."));
        settingsBtn.setOnAction(_ -> System.out.println("Opening settings..."));
        newGameBtn.setOnAction(_ -> System.out.println("Starting new game..."));

        // Layout
        VBox layout = new VBox(15); // spacing between elements
        layout.getChildren().addAll(title, continueBtn, loadBtn, settingsBtn, newGameBtn);
        layout.setStyle("-fx-padding: 50; -fx-alignment: center; -fx-background-color: #1e1e1e;");
        continueBtn.setStyle("-fx-font-size: 16; -fx-min-width: 200;");
        loadBtn.setStyle("-fx-font-size: 16; -fx-min-width: 200;");
        settingsBtn.setStyle("-fx-font-size: 16; -fx-min-width: 200;");
        newGameBtn.setStyle("-fx-font-size: 16; -fx-min-width: 200;");
        title.setStyle("-fx-fill: white;");

        // Scene setup
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setTitle("Game Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}