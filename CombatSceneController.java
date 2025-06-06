import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class CombatSceneController implements Initializable {

    @FXML private ProgressBar staminaBar;
    @FXML private ProgressBar healthBar;
    @FXML private Button btnAttack;
    @FXML private ChoiceBox<String> itemBox;
    @FXML private Button btnDefend;
    @FXML private Button btnPause;
    @FXML private Label combatMessageLable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: set up bars/items, add listeners, etc.
    }

    @FXML
    private void handleAttack() {
        // TODO: implement attack logic
    }

    @FXML
    private void handleDefend() {
        // TODO: implement defend logic
    }

    @FXML
    private void handlePause() {
        // TODO: switch to pause menu or scene
    }
}