import java.io.BufferedReader;
import java.io.Reader;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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
    public HashMap<String, GenericItem> itemMasterList = new HashMap();
    public HashMap<String, GenericNPC> npcMasterList = new HashMap();

        public void loadItems(String filePath) { //access the file path of items master list
            try (BufferedReader itemReader = new BufferedReader(new FileReader(filePath))) { //just says try to create a new file parser/reader
            String Line; //starting a new string line

            while ((Line = itemReader.readLine()) != null) //says as long as current line is not null then continue
                String[] data = Line.split("\\|"); //simply saying at each | the line is broken and continue to next value

            }

        }

        public void loadNPCs(String filePath) { //access the file path of items master list
            try (BufferedReader itemReader = new BufferedReader(new FileReader(filePath))) { //just says try to create a new file parser/reader
            String Line; //starting a new string line

            while ((Line = itemReader.readLine()) != null) //says as long as current line is not null then continue
                String[] data = Line.split("\\|"); //simply saying at each | the line is broken and continue to next value
            }

        }

    }   catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void writeItems() {
            
        try (BufferedWriter  itemWriter = new BufferedWriter(new FileWriter("resources/items.txt"))) {
            itemWriter.write("This is a test.");
            itemWriter.close();
        } catch (IOException e) {
            e.printStackTrace(); //this is just saying try to write to this file and if you cant print where the error occured in the stack
        }
    } 

}

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
                        skills per class*/
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


