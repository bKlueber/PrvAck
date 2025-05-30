package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    public App() {
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the Character Creator scene instead of the main menu
            Parent root = FXMLLoader.load(getClass().getResource("CharacterCreatorScene.fxml"));
            ChoiceBox<String> choiceBox = new ChoiceBox<>();

// 2) Add three items
choiceBox.getItems().addAll("Guard (Strengthfocused)", "Ranger (Dexterity focused)", "Technician (Intelligence focused)");

// 3) (Optional) Select the first item by default
choiceBox.getSelectionModel().selectFirst();

// 4) Position it within the pane
choiceBox.setLayoutX(491);
choiceBox.setLayoutY(48);

// 5) Add it to your root layout (must be a Pane or subclass)
if (root instanceof Pane) {
    ((Pane) root).getChildren().add(choiceBox);
} else {
    System.err.println("Cannot add ChoiceBox: root is not a Pane");
}

// Set up and show the stage
primaryStage.setTitle("Character Creator");
primaryStage.setScene(new Scene(root));
primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
