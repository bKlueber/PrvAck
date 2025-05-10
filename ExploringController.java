import java.util.ArrayList;
import java.util.HashMap;

public class ExplorerController {
    private HashMap<String, GameScene> sceneMap = new HashMap<>();
    private ArrayList<String> visitedScenes = new ArrayList<>();

    public void enterScene(String sceneID) {
        if (!visitedScenes.contains(sceneID)) { // Check if this scene has been explored
            visitedScenes.add(sceneID); // Mark scene as visited
            System.out.println("New area discovered: " + sceneID);
        }
        transitionToScene(sceneID);
    }
}

    public void transitionToScene(String sceneID) {
    if (visitedScenes.contains(sceneID)) { // Only transition if the scene is unlocked
        if (sceneMap.containsKey(sceneID)) {
            sceneMap.get(sceneID).printScene();
        } else {
            System.out.println("Scene not found.");
        }
    } else {
        System.out.println("You haven't discovered this area yet.");
    }
}