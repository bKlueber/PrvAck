package com.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CharacterCreatorController implements Initializable {

    @FXML
    private ChoiceBox<String> myChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate with three items
        myChoiceBox.getItems().addAll(
            "Item 1",
            "Item 2",
            "Item 3"
        );
        // (Optional) set a default selected value
        myChoiceBox.setValue("Item 1");
    }

}
