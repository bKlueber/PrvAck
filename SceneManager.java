import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SceneManager {
    private HashMap<String, WorldSceneTemplate> sceneMap = new HashMap<>();
    private ArrayList<String> visitedScenes = new ArrayList<>();
    private HashMap<String, Container> containerMap = new HashMap<>();
    private DataIO dataIO;

    public void setDataIO(DataIO dataIO) {
        this.dataIO = dataIO;
    }

    public void loadScenes(String filePath) {
        // Loads scenes from the sceneBank.txt file
        try (BufferedReader sceneReader = new BufferedReader(new FileReader(filePath))) {
            String sceneEntry;
            while ((sceneEntry = sceneReader.readLine()) != null) {
                String[] sceneParts = sceneEntry.split("\\|");
                if (sceneParts.length >= 3) {
                    String sceneID = sceneParts[0];
                    String description = sceneParts[1];
                    String actionsPart = sceneParts[2];
                    // --- FIX: Split actions only at commas not inside parentheses ---
                    String[] actionStrings = actionsPart.split(",(?![^()]*\\))");
                    List<ActionOption> actions = new ArrayList<>();
                    for (String actionStr : actionStrings) {
                        actionStr = actionStr.replace("\"", "").trim();
                        String[] parts = actionStr.split("->");
                        if (parts.length == 2) {
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

    public void enterScene(String sceneID, PlayerValues player) {
        if (!visitedScenes.contains(sceneID)) {
            visitedScenes.add(sceneID);
            System.out.println("New area discovered: " + sceneID);
        }
        transitionToScene(sceneID, player);
    }

    public void transitionToScene(String sceneID, PlayerValues player) {
        if (sceneMap.containsKey(sceneID)) {
            sceneMap.get(sceneID).printScene(player);
        } else {
            System.out.println("Scene not found.");
        }
    }

    public WorldSceneTemplate getScene(String sceneID) {
        return sceneMap.get(sceneID);
    }

    public void handleLooting(String containerID) {
        //handles the looting process for a given container
        Container container = containerMap.get(containerID);
        if (container == null) {
            System.out.println("There's no lootable container here.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            HashMap<String, GenericItem> loot = container.getLootContents();
            if (loot.isEmpty()) {
                System.out.println("The container is empty.");
                break;
            }
            System.out.println("Inside " + containerID + ", you find:");
            List<String> itemIDs = new ArrayList<>(loot.keySet());
            for (int i = 0; i < itemIDs.size(); i++) {
                GenericItem item = loot.get(itemIDs.get(i));
                System.out.println((i + 1) + ". " + item.itemName + " (Damage: " + item.baseDamage + ", Armor: " + item.baseArmor + ")");
            }
            System.out.print("Enter the number of the item to loot (or type 'leave'): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("leave")) {
                System.out.println("You leave the container.");
                break;
            }
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= itemIDs.size()) {
                    String chosenItemID = itemIDs.get(choice - 1);
                    container.lootItem(chosenItemID, "playerInventory", this.dataIO);
                } else {
                    System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public String findContainerForScene(String sceneID) {
        //finds a container that matches the current scene
        for (String containerID : containerMap.keySet()) {
            if (containerID.startsWith(sceneID)) {
                return containerID;
            }
        }
        return null;
    }

    public void displayInventory(PlayerValues player, HashMap<String, GenericItem> inventory, 
    String equippedWeaponID, String equippedArmorID) {
        //displays the player's inventory, showing equipped items
        System.out.println("Your Inventory:");
        System.out.println("Credits: " + player.currency); // Show credits
        int index = 1;
        for (String itemID : inventory.keySet()) {
            GenericItem item = inventory.get(itemID);
            String equipped = "";
            if (itemID.equals(equippedWeaponID)) equipped = " [EQUIPPED WEAPON]";
            if (itemID.equals(equippedArmorID)) equipped = " [EQUIPPED ARMOR]";
            System.out.println(index + ". " + item.itemName + equipped +
                " (Damage: " + item.baseDamage + ", Armor: " + item.baseArmor + ")");
            index++;
        }
    }

    public void equipItem(PlayerValues player, HashMap<String, GenericItem> inventory, Scanner scanner) {
        //allows the player to equip a weapon or armor from inventory
        displayInventory(player, inventory, player.equippedWeaponID, player.equippedArmorID);
        System.out.println("Enter the number of the item to equip (or 0 to cancel):");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice <= 0 || choice > inventory.size()) {
            System.out.println("Cancelled.");
            return;
        }
        int index = 1;
        for (String itemID : inventory.keySet()) {
            if (index == choice) {
                GenericItem item = inventory.get(itemID);
                if (item.baseDamage > 0) {
                    player.equippedWeaponID = itemID;
                    System.out.println(item.itemName + " equipped as weapon.");
                } else if (item.isArmorSet) {
                    player.equippedArmorID = itemID;
                    System.out.println(item.itemName + " equipped as armor.");
                } else {
                    System.out.println("Item cannot be equipped.");
                }
                return;
            }
            index++;
        }
    }

    public HashMap<String, Container> getContainerMap() {
        return containerMap;
    }
}
