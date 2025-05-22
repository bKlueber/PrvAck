import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SceneManager {
    private HashMap<String, WorldSceneTemplate> sceneMap = new HashMap<>(); //takes all the scenes and stores them, connects scene IDs to their corresponding WorldSceneTemplate objects
    private ArrayList<String> visitedScenes = new ArrayList<>(); //keeps track of what scenes the player has visited before
    private HashMap<String, Container> containerMap = new HashMap<>(); //stores all the containers in the game, connects the container id to the corrosponding container object

    public void loadScenes(String filePath){ //loads all the scenes from sceneBank.txt
        try (BufferedReader sceneReader = new BufferedReader(new FileReader(filePath))) {
            String sceneEntry;

            while ((sceneEntry = sceneReader.readLine()) != null) {
                String[] sceneParts = sceneEntry.split("\\|"); //tells the reader to split on | 
                
                if (sceneParts.length >= 3) { //makes sure all neeeded parts are there
                    String sceneID = sceneParts[0];//scene id
                    String description = sceneParts[1];//scene narrative description/text
                    String actionsPart = sceneParts[2];//actions for player in the scene
                    String[] actionStrings = actionsPart.split(",");
                    List<ActionOption> actions = new ArrayList<>();
                    
                    for (String actionStr : actionStrings) {
                        actionStr = actionStr.replace("\"", "").trim(); // Remove extra quotes and whitespace
                        String[] parts = actionStr.split("->"); //formats as label->trigger
                       
                        if (parts.length == 2) {
                            actions.add(new ActionOption(parts[0].trim(), parts[1].trim()));
                        } 
                        
                        else {
                            System.err.println("Invalid action format for scene " + sceneID + ": " + actionStr);
                        }
                    }
                    sceneMap.put(sceneID, new WorldSceneTemplate(sceneID, description, actions)); //stores the scenes into sceneMap
                }
            }

            System.out.println("Scenes loaded successfully!");
        } catch (IOException e) {
            System.err.println("Error loading scenes: " + e.getMessage());
        }
    }

    public void enterScene(String sceneID) { //player goes into a scene and this marks it as visited
        if (!visitedScenes.contains(sceneID)) {  
            visitedScenes.add(sceneID); 
            System.out.println("New area discovered: " + sceneID);
        }
        transitionToScene(sceneID);
    }

    public void transitionToScene(String sceneID) { //moves the player to the scene and prints its narritive elements
        if (sceneMap.containsKey(sceneID)) {
            sceneMap.get(sceneID).printScene();
        } else {
            System.out.println("Scene not found.");
        }
    }

    public WorldSceneTemplate getScene(String sceneID) { //retraives a scene based on its id
        return sceneMap.get(sceneID);
    }

    public void handleLooting(String containerID) { //handles looting interaction 
        Container container = containerMap.get(containerID);  //gets the container returned from containerID
        if (container == null) {
            System.out.println("There's no lootable container here.");
            return;
        }

        HashMap<String, GenericItem> loot = container.getLootContents(); //gets the items that are in the container
        if (loot.isEmpty()) {
            System.out.println("The container is empty.");
            return;
        }

        System.out.println("Inside " + containerID + ", you find:"); //shows the items that were found

        List<String> itemIDs = new ArrayList<>(loot.keySet());
        for (int i = 0; i < itemIDs.size(); i++) {
            System.out.println((i + 1) + ". " + loot.get(itemIDs.get(i)).itemName);
        }

        System.out.print("Enter the number of the item to loot (or type 'leave'): "); //prompts player to loot or leave
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("leave")) { //this just returns the player to the scene
            System.out.println("You leave the container untouched.");
            return;
        }

        try {
            int choice = Integer.parseInt(input);

            if (choice >= 1 && choice <= itemIDs.size()) {
                String chosenItemID = itemIDs.get(choice - 1);
                container.lootItem(chosenItemID, "playerInventory", new DataIO());
            } 
            else {
                System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    public String findContainerForScene(String sceneID) { //matches sceneID to containers making sure correct one is pulled
        for (String containerID : containerMap.keySet()) {

            if (containerID.startsWith(sceneID)) { // Matches scene ID with containers
                return containerID;
            }
        }
        return null;
    }
}

class GameState {
    private WorldStateEnum worldState; //stores current worldstate
    private WorldSceneTemplate currentScene; //tracks the scene the player is in
    private SceneManager sceneManager;

    public GameState(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.worldState = WorldStateEnum.EXPLORING;
    }

    public void setWorldState(WorldStateEnum state) { //updates the world state between exploring, combat and looting
        this.worldState = state;
    }

    public void loadScene(String sceneID) { //loads in a new scene
        WorldSceneTemplate nextScene = sceneManager.getScene(sceneID);
        if (nextScene != null) {
            currentScene = nextScene;
            currentScene.printScene();
        } else {
            System.err.println("Scene ID " + sceneID + " not found.");
        }
    }

    public void handleSceneInteraction(String sceneID) { //handles the player input for what action to take in the scene
        WorldSceneTemplate scene = sceneManager.getScene(sceneID); // FIX: Now correctly references sceneManager
        if (scene == null) {
            System.out.println("Scene not found.");
            return;
        }

        scene.printScene();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose an action: "); //prompt the player for input
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<ActionOption> actions = scene.getAvailableActions();
        if (choice >= 1 && choice <= actions.size()) {
            ActionOption selectedAction = actions.get(choice - 1);

            if (selectedAction.getTrigger().equals("LOOTING")) { //if looting action is selected, finds the right container and displays contents
                String containerID = sceneManager.findContainerForScene(sceneID); 
                
                if (containerID != null) {
                    sceneManager.handleLooting(containerID);
                } 
                else {
                    System.out.println("There's no container to loot here.");
                }
            } 
            else {
                sceneManager.transitionToScene(selectedAction.getTrigger()); //transition to the selected scene based on player action
            }
        }
        else {
            System.out.println("Invalid choice.");
        }
    }
}