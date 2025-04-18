import java.util.HashMap;

public class PrvAck{
//right now nothing to add here
    
} 

class GenericItem{ //this is the class that is used for every single item in the game. See items.txt for more information

    String itemName;
    String itemID;
    String itemDescription;
    double itemValue;
    double itemWeight; //not neccessary but in my (Brady) opinion i think it adds depth to our systems
    int baseDamage; //will not be applicable to every item but by defining it here, and then assigning it in the txt we will save a lot of time and effort
    int baseArmor; //same as baseDamage, as in may be null for some items but will make adjusting and balancing worlds easier
    
        public GenericItem genericItem (String  itemName, String itemID, String itemDescription, double itemValue, double itemWeight, int itemDamage, int itemArmor) {  //this is matching the paremeter to instances of each variable
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

        public GenericNPC genericNPC ( String npcName, String npcID, String npcDescription, int npcHealth, boolean npcCanCombat, String npcFaction) {
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