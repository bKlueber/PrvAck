import java.util.Random;
import java.util.Scanner;

public class CharacterCreator { //this will be the class that takes all player info and compiles it for the game. stats, class, gender, etc
    
    public PlayerValues createCharacter () {
        PlayerValues player = new PlayerValues(); //creating a new player populated with attributes from PlayerValues
        Scanner userInput = new Scanner(System.in);

        System.out.println("Please choose a name for your character: ");
        String playerName = userInput.nextLine();

        PlayerClasses classes = new PlayerClasses();
        boolean classConfirmed = false; //setting these to control the loop that will prompt if the user is sure of their choice
        int classChoice = 0;

        while(!classConfirmed) { //simply says as long as classConfirmed is false to keep looping
            System.out.println("Please choose a class");
            System.out.println("1. Guard (Strengthfocused)");
            System.out.println("2. Ranger (Dexterity focused)");
            System.out.println("3. Technician (Intelligence focused)");

            classChoice = userInput.nextInt();
            userInput.nextLine();

            switch (classChoice) { //so this will display the class info and ask user to confrim before it will continue

                case 1:
                    classes.userChoseSTR = new StrengthBuild(); 
                    System.out.println("");
                    System.out.println("Guard Selected - Strength Build -");
                    System.out.println("Stats are:");
                    System.out.println("Vitality: " + classes.userChoseSTR.baseVitalitySTR);
                    System.out.println("Charisma: " + classes.userChoseSTR.baseCharismaSTR);
                    System.out.println("Endurance: " + classes.userChoseSTR.baseEnduranceSTR);
                    System.out.println("Strength: " + classes.userChoseSTR.baseStrengthSTR);
                    System.out.println("Dexterity: " + classes.userChoseSTR.baseDexteritySTR);
                    System.out.println("Intelligence: " + classes.userChoseSTR.baseIntelligenceSTR);
                    break;

                case 2: 
                    classes.userChoseDEX = new DexterityBuild(); 
                    System.out.println("");
                    System.out.println("Ranger Selected - Dexterity Build -");
                    System.out.println("Stats are:");
                    System.out.println("Vitality: " + classes.userChoseDEX.baseVitalityDEX);
                    System.out.println("Charisma: " + classes.userChoseDEX.baseCharismaDEX);
                    System.out.println("Endurance: " + classes.userChoseDEX.baseEnduranceDEX);
                    System.out.println("Strength: " + classes.userChoseDEX.baseStrengthDEX);
                    System.out.println("Dexterity: " + classes.userChoseDEX.baseDexterityDEX);
                    System.out.println("Intelligence: " + classes.userChoseDEX.baseIntelligenceDEX);
                    break;


                case 3: 
                    classes.userChoseINTL = new IntelligenceBuild();
                    System.out.println("");
                    System.out.println("Technician Selected - Intelligence Build -");
                    System.out.println("Stats are:");
                    System.out.println("Vitality: " + classes.userChoseINT.baseVitalityINT);
                    System.out.println("Charisma: " + classes.userChoseINT.baseCharismaINT);
                    System.out.println("Endurance: " + classes.userChoseINT.baseEnduranceINT);
                    System.out.println("Strength: " + classes.userChoseINT.baseStrengthINT);
                    System.out.println("Dexterity: " + classes.userChoseINT.baseDexterityINT);
                    System.out.println("Intelligence: " + classes.userChoseINT.baseIntelligenceINT);
                    break;
            }

        }
    }

}

class PlayerValues{ //this classs is going to control player base stats, including health, invnetory sizee, base damage (from strength rolls, for example)

    String playerName;
    int playerHealth;
    PlayerClasses selectedClass;
    DiceStatRoller playerBaseStats;
    playerHealth = playerVitality + baseVitality;

}


class PlayerClasses { /*i was thinking keeep it  simple with just three classes, hacking/tech, strength/tank/meleee, and stealth/rranged, was cosidering having unique ability for each
                        but don't know if i should make class effect base stats? Maybe thats a stretch goal. Was planning on using bolean check and then looping to give access to unique
                        skills per class */
    StrengthBuild userChoseSTR;
    DexterityBuild userChoseDEX;
    IntelligenceBuild userChoseINT;



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
