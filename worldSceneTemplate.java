import java.util.List;

public class WorldSceneTemplate {
    private String sceneID;
    private String description;
    private List<ActionOption> availableActions;

    public WorldSceneTemplate(String sceneID, String description, List<ActionOption> availableActions) {
        this.sceneID = sceneID;
        this.description = description;
        this.availableActions = availableActions;
    }

    public String getSceneID() {
        return sceneID;
    }

    public List<ActionOption> getAvailableActions() {
        return availableActions;
    }

    public void printScene() {
        System.out.println(description);
        for (int i = 0; i < availableActions.size(); i++) {
            System.out.println((i + 1) + ". " + availableActions.get(i).getLabel());
        }
    }
}

