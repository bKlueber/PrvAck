import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CombatVictoryController implements Initializable {

    @FXML private Label victoryMessageLable;
    @FXML private Label rewardMessageLable;
    @FXML private TextField itemLootingInput;
    @FXML private Button btnYesLoot;
    @FXML private Button btnNoLoot;
    @FXML private Label playerLootedItems;
    @FXML private Label nothingLeft;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: init visibility, text, etc.
    }

    @FXML
    private void handleYesLoot() {
        // TODO: process looting input
    }

    @FXML
    private void handleNoLoot() {
        // TODO: skip looting, proceed onward
    }
}
