import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class DataIO {//this classs calls the corrosponding txt files, parses, writes and reads corrosponding values
    public HashMap<String, HashMap<String, GenericItem>> itemMasterList = new HashMap<>(); //creating a 2d hashmap to partition inventories seperately so then they can all be managed within one .txt
    public HashMap<String, GenericNPC> npcMasterList = new HashMap<>();

        public void transferItem(String fromInventory, String toInventory, String itemID) {
            if (!itemMasterList.containsKey(fromInventory)) {
                System.err.println("Parent inventory does not exist." + fromInventory); //this checks if the inventory exists and if not prints err
                return;
            }

            itemMasterList.computeIfAbsent(toInventory, k -> new HashMap<>()); //so if for some reason the inventory doesnt exist it will create a new one in its place
            
                HashMap<String, GenericItem> sourceInventory = itemMasterList.get(fromInventory);
                HashMap<String, GenericItem> targetInventory = itemMasterList.get(toInventory);
                
                if(sourceInventory.containsKey(itemID)) { //this is checking to see if item actually exist in the inventory
                    targetInventory.put(itemID, sourceInventory.remove(itemID)); //this code actually moves the item and removes it from source npc/location
                }
                else  {
                    System.err.println("Error, item cannot be located in inventory you are moving from.");
                }
        }
        

        public void loadItems(String filePath, String inventoryID) { //access the file path of items master list
            
            if (inventoryID == null || inventoryID.isEmpty()) {
                System.err.println("Inventory id does not exist, therefore cannot load item.");
                return;
                }
            itemMasterList.put(inventoryID, new HashMap<>()); //if the inventory txt is completely empty (like start of a new game) it cresates an empty inventory. w/o this it will break   

            try (BufferedReader itemReader = new BufferedReader(new FileReader(filePath))) { //just says try to create a new file parser/reader
            String line; //starting a new string line

            while ((line = itemReader.readLine()) != null) {//says as long as current line is not null then continue
                String[] itemData = line.split("\\|"); //simply saying at each | the line is broken and continue to next value
            
                if (itemData.length == 8) { //this is just making suere there are 7 fields avalaible, the amouint outlined in item info}
                   GenericItem item = new GenericItem( 
                    itemData[0], itemData[1], itemData[2], Double.parseDouble(itemData[3]), Double.parseDouble(itemData[4]), 
                    Integer.parseInt(itemData[5]), Integer.parseInt(itemData[6]), itemData[7]);

                   itemMasterList.computeIfAbsent(inventoryID, k -> new HashMap<String, GenericItem>()).put(item.itemID, item); //this actually stores the item
                    }
                
                }    
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error loading item.\nDetails: " + e.getMessage() + "\nPlease ensure data hasnt corrupted");
            }
        }

        public void writeItems(String filePath) {
            
        try (BufferedWriter  itemWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (HashMap<String, GenericItem> inventory : itemMasterList.values()) { //this loops through the inventories
                for (GenericItem item : inventory.values()) { //this loops through items in the inventories 
                    itemWriter.write(String.format("%s|%s|%s|%.2f|%.2f|%d|%d%n",
                    item.itemName, item.itemID, item.itemDescription, item.itemValue, item.itemWeight, item.baseDamage, item.baseArmor));
                }
            }
        } catch (IOException e) {
           System.err.println("Error creating item: "  + e.getMessage());
        } 
        }

        public void saveInventories(String filePath) {
            try (BufferedWriter inventoryWriter = new BufferedWriter(new FileWriter(filePath))) {
                for (String inventoryID : itemMasterList.keySet()) {
                    inventoryWriter.write(inventoryID + ": "); //need to label each inventory
                    for (GenericItem item : itemMasterList.get(inventoryID).values()) {
                        inventoryWriter.write(item.itemID + "|");
                    }
                    inventoryWriter.newLine();
                }
            } catch (IOException e) {
                System.err.println("There was an issue while trying to save inventories. " + e.getMessage());
            }
        }

        public void loadContainerItems(String filePath, HashMap<String, Container> containerMap) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length == 2) {
                String containerID = parts[0];
                String[] itemIDs = parts[1].split(",");
                containerMap.putIfAbsent(containerID, new Container(containerID));
                Container container = containerMap.get(containerID);

                //add to itemMasterList for transferItem to work
                itemMasterList.putIfAbsent(containerID, new HashMap<>());

                for (String itemID : itemIDs) {
                    itemID = itemID.trim();
                    if (itemMasterList.containsKey("global") && itemMasterList.get("global").containsKey(itemID)) {
                        GenericItem item = itemMasterList.get("global").get(itemID);
                        container.addItem(item);
                        itemMasterList.get(containerID).put(itemID, item); // Add item to the specific container's inventory in itemMasterList
                    } else {
                        System.err.println("Warning: Item " + itemID + " not found in ItemMasterList!");
                    }
                }
            }
        }
        System.out.println("Containers prepopulated with real items!");
    } catch (IOException e) {
        System.err.println("Error loading container items: " + e.getMessage());
    }
}

        public void loadNPCs(String filePath) { //access the file path of npc master list
            try (BufferedReader npcReader = new BufferedReader(new FileReader(filePath))) { //just says try to create a new file parser/reader
            String line; //starting a new string line

            while ((line = npcReader.readLine()) != null) { //says as long as current line is not null then continue
                String[] npcData = line.split("\\|"); //simply saying at each | the line is broken and continue to next value

                if (npcData.length == 9) { //ensures that all npcs have information for each category, 6, some can be null but must be present
                    GenericNPC npc = new GenericNPC(
                        npcData[0], npcData[1], npcData[2], Integer.parseInt(npcData[3]), 
                        Boolean.parseBoolean(npcData[4]), npcData[5], Integer.parseInt(npcData[6])
                        , Integer.parseInt(npcData[7]), Integer.parseInt(npcData[8]));
                    npcMasterList.put(npc.npcID, npc);
                }
                else {
                    System.err.println("Incorrect NPC data format: " + line);
                }
            }
        }   catch (IOException | NumberFormatException e) {
            System.err.println("Problem while loading npcs. \nMore information: " + e.getMessage());
            }
        }

    
        public void loadNPCInventories(String filePath) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
        
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
           
                if (parts.length == 2) {
                    String npcID = parts[0];
                    String[] itemIDs = parts[1].split(",");

                    if (npcMasterList.containsKey(npcID)) {
                        GenericNPC npc = npcMasterList.get(npcID);
                        for (String itemID : itemIDs) {
                        // Assuming that "global" contains all items (loaded via loadItems)
                        
                            if (itemMasterList.containsKey("global") && itemMasterList.get("global").containsKey(itemID)) {
                                npc.addItem(itemMasterList.get("global").get(itemID));
                            } 
                            else {
                                System.err.println("Warning: Item " + itemID + " not found in ItemMasterList!");
                            }
                        }
                    }
                }
            }
                System.out.println("NPC inventories successfully loaded!");
            } catch (IOException e) {
        
            System.err.println("Error loading NPC inventories: " + e.getMessage()); //if for some reason it has an issue reading or writing then this will print the error for us
            }
        }

    //add this method for GameState.java
    public GenericItem getOrCreateStrangeToken() {
        String tokenID = "token_001";
        if (itemMasterList.containsKey("global") && itemMasterList.get("global").containsKey(tokenID)) {
            return itemMasterList.get("global").get(tokenID);
        }
        GenericItem token = new GenericItem("Strange Token", tokenID, "A mysterious token given by the Old Lady.", 0.0, 0.1, 0, 0, "None");
        itemMasterList.computeIfAbsent("global", k -> new HashMap<>()).put(tokenID, token);
        return token;
    }
}
