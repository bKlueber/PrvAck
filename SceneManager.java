import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SceneManager {
    private HashMap<String, WorldSceneTemplate> sceneMap = new HashMap<>();
    private ArrayList<String> visitedScenes = new ArrayList<>();

    public void loadScenes(String filePath){
    try (BufferedReader sceneReader = new BufferedReader(new FileReader(filePath))) {
        String sceneEntry;
        while ((sceneEntry = sceneReader.readLine()) != null) {
            String[] sceneParts = sceneEntry.split("\\|");
            if (sceneParts.length >= 3) {
                String sceneID = sceneParts[0];
                String description = sceneParts[1];
                String actionsPart = sceneParts[2];
                String[] actionStrings = actionsPart.split(",");
                List<ActionOption> actions = new ArrayList<>();
                for (String actionStr : actionStrings) {
                    // Remove extra quotes and whitespace:
                    actionStr = actionStr.replace("\"", "").trim();
                    // Expecting format "Label->Trigger"
                    String[] parts = actionStr.split("->");
                    if(parts.length == 2){
                        actions.add(new ActionOption(parts[0].trim(), parts[1].trim()));
                    } else {
                        System.err.println("Invalid action format for scene " + sceneID + ": " + actionStr);
                    }
                }
                sceneMap.put(sceneID, new WorldSceneTemplate(sceneID, description, actions));
            }
        }
        System.out.println("Scenes loaded successfully!");
    } catch (IOException e) {
        System.err.println("Error loading scenes: " + e.getMessage());
    }
}

    public void enterScene(String sceneID) {
        if (!visitedScenes.contains(sceneID)) { 
            visitedScenes.add(sceneID); 
            System.out.println("New area discovered: " + sceneID); //we can display this if we want, i thought it would be a nice touch but doesnt really matter
        }
        transitionToScene(sceneID);
    }

         public void transitionToScene(String sceneID) {
        if (sceneMap.containsKey(sceneID)) {
            sceneMap.get(sceneID).printScene();
        } 
        else {
            System.out.println("Scene not found.");
        }
    }

    public WorldSceneTemplate getScene(String sceneID) {
    return sceneMap.get(sceneID);
    }
}

class GameState {
    private WorldStateEnum worldState;
    private WorldSceneTemplate currentScene;
    private SceneManager sceneManager;

    public GameState(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.worldState = WorldStateEnum.EXPLORING;
    }

    public void setWorldState(WorldStateEnum state) {
        this.worldState = state;
    }

    public void loadScene(String sceneID) {
        WorldSceneTemplate nextScene = sceneManager.getScene(sceneID);
        if (nextScene != null) {
            currentScene = nextScene;
            currentScene.printScene();
        } else {
            System.err.println("Scene ID " + sceneID + " not found.");
        }
    }
}
