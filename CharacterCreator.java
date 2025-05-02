import java.util.Random;

public class CharacterCreator { //this will be the class that takes all player info and compiles it for the game. stats, class, gender, etc

}

class PlayerValues{ //this classs is going to control player base stats, including health, invnetory sizee, base damage (from strength rolls, for example)

    String playerName;
    int playerHealth;
    PlayerClasses selectedClass;
    DiceStatRoller playerBaseStats;

}


class PlayerClasses { /*i was thinking keeep it  simple with just three classes, hacking/tech, strength/tank/meleee, and stealth/rranged, was cosidering having unique ability for each
                        but don't know if i should make class effect base stats? Maybe thats a stretch goal. Was planning on using bolean check and then looping to give access to unique
                        skills per class */
    StrengthBuild userChoseSTR;
    DexterityBuild userChoseDEX;
    IntelligenceBuild userChoseINTL;



}

class DiceStatRoller {

    int playerVitality; //intialize variables for each stat applicable to players
    int playerStrength;
    int playerCharisma;
    int playerIntelligence;
    int playerDexterity;
    int playerEndurance; //figured this stat can be tied to HP in some capacity

    int baseVitality = 5; //these are not permenant, eventuallly will inherit these values from the charcter creator
    int baseStrength = 5;
    int baseCharisma = 5;
    int baseIntelligence = 5;
    int baseDexterity = 5;
    int baseEndurance = 5;

    Random randDice = new Random(); //creates new dice 

    private int rollDie() {
        return randDice.nextInt(20) + 1; //this rolls a dice between 1 and 20, says 20+1 because of how Random works in java, if just did 20 would only range 1-19
    }

    public void rollStats() {
        playerVitality = rollDie();
        playerStrength = rollDie();
        playerCharisma = rollDie();
        playerIntelligence = rollDie();
        playerDexterity = rollDie();
        playerEndurance = rollDie();
    }
    public String toString() {
        return "Vitality: " + (playerVitality + baseVitality) + "\nStrength: " + (playerStrength + baseStrength) + "\nCharisma: " + ((playerCharisma + baseCharisma) + "\nIntelligence: "
        + (playerIntelligence + baseIntelligence) + "\nDexterity: " + (playerDexterity + baseDexterity) + "\nEndurance: " + (playerEndurance + baseEndurance));
    }

}

class StrengthBuild {
    
    int baseVitalitySTR;
    int baseCharismaSTR;
    int baseEnduranceSTR;
    int baseStrengthSTR;
    int baseDexteritySTR;
    int baseIntelligenceSTR;
    boolean specialAbilitySTR; 

    public StrengthBuild() {
        baseVitalitySTR = 150;
        baseCharismaSTR = 70;
        baseEnduranceSTR = 100;
        baseStrengthSTR = 150;
        baseDexteritySTR = 60;
        baseIntelligenceSTR = 70; 
        specialAbilitySTR = true;

    }

}

class DexterityBuild{ 
    
    int baseVitalityDEX;
    int baseCharismaDEX;
    int baseEnduranceDEX;
    int baseStrengthDEX;
    int baseDexterityDEX;
    int baseIntelligenceDEX;
    boolean specialAbilityDEX; 

    public DexterityBuild() {
        baseVitalityDEX = 120;
        baseCharismaDEX = 60;
        baseEnduranceDEX = 120;
        baseStrengthDEX = 90;
        baseDexterityDEX = 150;
        baseIntelligenceDEX = 60; 
        specialAbilityDEX = true;

    }
}

class IntelligenceBuild{

    int baseVitalityINT;
    int baseCharismaINT;
    int baseEnduranceINT;
    int baseStrengthINT;
    int baseDexterityINT;
    int baseIntelligenceINT;
    boolean specialAbilityINT; 

    public IntelligenceBuild() {
        baseVitalityINT = 100;
        baseCharismaINT = 120;
        baseEnduranceINT = 80;
        baseStrengthINT = 70;
        baseDexterityINT = 80;
        baseIntelligenceINT = 150; 
        specialAbilityINT = true;

    }

}
