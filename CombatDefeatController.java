import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CombatDefeatController implements Initializable {

    @FXML private Button btnQuit;
    @FXML private Button btnRestartFight;
    @FXML private Label defeatMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: set defeatMessage text, disable buttons until ready, etc.
    }

    @FXML
    private void handleQuit() {
        // TODO: Platform.exit() or return to main menu
    }

    @FXML
    private void handleRestartFight() {
        // TODO: reload CombatScene1.fxml or reset stats
    }
}
