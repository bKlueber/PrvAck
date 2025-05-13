import java.util.List;

public class WorldSceneTemplate {
    private String sceneID;
    private String description;
    private List<String> availableActions;

    public WorldSceneTemplate(String sceneID, String description, List<String> availableActions) {
        this.sceneID = sceneID;
        this.description = description;
        this.availableActions = availableActions;
    }

    public void printScene() {
        System.out.println(description);
        for (int i = 0; i < availableActions.size(); i++) {
            System.out.println((i + 1) + ". " + availableActions.get(i));
        }
    }
}