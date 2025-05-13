import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExploringController {
    private HashMap<String, WorldSceneTemplate> sceneMap = new HashMap<>();
    private ArrayList<String> visitedScenes = new ArrayList<>();

    public void loadScenes(String filePath){ //make sure to denote filepath in final controller 
        try (BufferedReader sceneReader = new BufferedReader(new FileReader(filePath))) { //this just trys to create a file reader that can parse whole lines
            String sceneEntry;
            
            while ((sceneEntry = sceneReader.readLine()) != null) {
                String[] sceneParts = sceneEntry.split("\\|"); //splits on each | char in the .txt
                
                if (sceneParts.length >= 3) {//these 3 parts are the 3 categories outlined in the .txt but if neccesary we can add to it with how I wrote this code
                    String sceneID = sceneParts[0];
                    String description = sceneParts[1];
                    List<String> actions = Arrays.asList(sceneParts[2].split(","));

                    sceneMap.put(sceneID, new WorldSceneTemplate(sceneID, description, actions));
                }
            }
            
            System.out.println("Scenes loaded successfully!");//this does not need to be show in the gui im just putting this here to make sure the code actually loads properly 
        }

        catch (IOException e) {
            System.err.println("Error loading scenes: " + e.getMessage()); //without this the game could crash if for some reason it cant read a scene
        
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
}
