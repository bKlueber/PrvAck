
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
