import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class PrvAck{
//right now nothing to add here
    public static void main(String[] args) {
        DataIO dataIO = new DataIO();

        // Test loading items
        dataIO.loadItems("configFiles/playerInventory.txt", "playerInventory");
        System.out.println("Loaded player inventory: " + dataIO.itemMasterList.get("playerInventory"));

        // Test transferring an item
        dataIO.transferItem("playerInventory", "npcInventory_001", "sword_001");
        System.out.println("After transfer: " + dataIO.itemMasterList.get("npcInventory_001"));

        // Test saving inventories
        dataIO.saveInventories("configFiles/playerInventory.txt");
        System.out.println("Inventory saved successfully.");
    }
}
    
class GenericItem{ //this is the class that is used for every single item in the game. See items.txt for more information

    String itemName;
    String itemID;
    String itemDescription;
    double itemValue;
    double itemWeight; //not neccessary but in my (Brady) opinion i think it adds depth to our systems
    int baseDamage; //will not be applicable to every item but by defining it here, and then assigning it in the txt we will save a lot of time and effort
    int baseArmor; //same as baseDamage, as in may be null for some items but will make adjusting and balancing worlds easier
    
        public GenericItem(String  itemName, String itemID, String itemDescription, double itemValue, double itemWeight, int itemDamage, int itemArmor) {  //this is matching the paremeter to instances of each variable
            this.itemName = itemName;
            this.itemID = itemID;
            this.itemDescription = itemDescription;
            this.itemValue = itemValue;
            this.itemWeight = itemWeight;
            this.baseDamage = itemDamage;
            this.baseArmor = itemArmor;
        }

}

class GenericNPC{

    String npcName;
    String npcID;
    String npcDescription; //not neccessary but including it for now, I (Brady) was thinkng haveng like a codex on npcs that populates af you interact with them for the first time
    int npcHealth;
    boolean npcCanCombat; //this will be a check to see if npc can be attacked by player or not, well one check of many, probably
    String npcFaction; //can be null but if, and big if we want to have reputation based on faction this will help

        public GenericNPC( String npcName, String npcID, String npcDescription, int npcHealth, boolean npcCanCombat, String npcFaction) {
            this.npcName = npcName;
            this.npcID = npcID;
            this.npcDescription = npcDescription;
            this.npcHealth = npcHealth;
            this.npcCanCombat = npcCanCombat;
            this.npcFaction = npcFaction;
        }

}

class WorldContainers{ //this is a class built off of hashmaps, these hashmaps will track inventories of both NPC's, the player and world containers all through npcID's

     HashMap<String, HashMap <String, Integer>> inventoryContents; //this is a 2d data structure starts with who owns the inventory and then iterates through contents

    public WorldContainers() { 
        inventoryContents = new HashMap<>();
    }

}

class ContainerType{ //realized not all containers need the information an npc would have assigned to them, so this class checks if chest or npc and then if npc assigns proper attributes

    String containerID;
    String containerCategory;

    public ContainerType(String containerID, String containerCategory) {

        this.containerID = containerID;
        this.containerCategory= containerCategory;
    }

}

class DataIO {//this classs calls the corrosponding txt files, parses, writes and reads corrosponding values
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
            
                if (itemData.length == 7) { //this is just making suere there are 7 fields avalaible, the amouint outlined in item info}
                   GenericItem item = new GenericItem( 
                    itemData[0], itemData[1], itemData[2], Double.parseDouble(itemData[3]), Double.parseDouble(itemData[4]), 
                    Integer.parseInt(itemData[5]), Integer.parseInt(itemData[6])
                   );

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

        public void loadNPCs(String filePath) { //access the file path of items master list
            try (BufferedReader npcReader = new BufferedReader(new FileReader(filePath))) { //just says try to create a new file parser/reader
            String line; //starting a new string line

            while ((line = npcReader.readLine()) != null) { //says as long as current line is not null then continue
                String[] npcData = line.split("\\|"); //simply saying at each | the line is broken and continue to next value

                if (npcData.length == 6) { //ensures that all npcs have information for each category, 6, some can be null but must be present
                    GenericNPC npc = new GenericNPC(
                        npcData[0], npcData[1], npcData[2], Integer.parseInt(npcData[3]), Boolean.parseBoolean(npcData[4]), npcData[5]);
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
 /*
class PlayerValues{ //this classs is going to control player base stats, including health, invnetory sizee, base damage (from strength rolls, for example)

    String playerName;
    int playerHealth;
    PlayerClasses selectedClass;
    BuildPlayerRolls playerBaseStats;




}

class BuildPlayerRolls { //this will be the class that takes all player info and compiles it for the game. stats, class, gender, etc

}

class PlayerClasses { /*i was thinking keeep it  simple with just three classes, hacking/tech, strength/tank/meleee, and stealth/rranged, was cosidering having unique ability for each
                        but don't know if i should make class effect base stats? Maybe thats a stretch goal. Was planning on using bolean check and then looping to give access to unique
                        skills per class
    StrengthBuild userChoseSTR;
    DexterityBuild userChoseDEX;
    IntelligenceBuild userChoseINTL;



}

class diceStatRoller {

    String playerVitality = null; //these are just place holders obviously tied to health
    String playerStrength = null; //need to research how to make it so the player can like roll for these or adjust base stats constrained by class, need to consult story team and then come back
    String playerCharisma = null;
    String playerIntellegence = null;
    String playerDexterity = null;
    String playerEndurance = null; //figured this stat can be tied to stamina 
}

class StrengthBuild {
    
    int baseDamageSTR;
    double baseCarryWeightSTR;
    int baseHealthSTR;
    boolean specialAbilitySTR; 

}

class DexterityBuild{ 
    
    int baseDamageDEX;
    double baseCarryWeightDEX;
    int baseHealthDEX;
    boolean specialAbilityDEX; 
}

class IntelligenceBuild{

    int baseDamageINTL;
    double baseCarryWeightINTL;
    int baseHealthINTL;
    boolean specialAbilityINTL; 

}
*/

}
