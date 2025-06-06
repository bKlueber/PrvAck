import java.util.Scanner;

public class CharacterCreator { //this will be the class that takes all player info and compiles it for the game. stats, class, gender, etc
    
    public PlayerValues createCharacter () {
        PlayerValues player = new PlayerValues(); //creating a new player populated with attributes from PlayerValues
        player.currency = 200; //ensure player starts with 200 credits, for some reason kept spawning with 0 credits, this fixes it
        Scanner userInput = new Scanner(System.in);

        System.out.println("Please choose a name for your character: ");
        String playerName = userInput.nextLine();
        player.playerName = playerName; 

        PlayerClasses classes = new PlayerClasses();
        boolean classConfirmed = false; //setting these to control the loop that will prompt if the user is sure of their choice
        int classChoice = 0;

        while(!classConfirmed) {
            System.out.println("Please choose a class");
            System.out.println("1. Guard (Strengthfocused)");
            System.out.println("2. Ranger (Dexterity focused)");
            System.out.println("3. Technician (Intelligence focused)");
            System.out.println("");
            System.out.println("**Please note final stats will incoporate both base class stats and rolled stats**");

            classChoice = userInput.nextInt();
            userInput.nextLine();

            switch (classChoice) {
                case 1:
                    classes.userChoseSTR = new StrengthBuild();
                    classes.userChoseDEX = null; 
                    classes.userChoseINT = null;
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
                    classes.userChoseSTR = null;
                    classes.userChoseINT = null; 
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
                    classes.userChoseINT = new IntelligenceBuild();
                    classes.userChoseSTR = null;
                    classes.userChoseDEX = null; 
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
                
                default:
                    System.out.println("Invalid selection please try again");
                    continue; //this makes the loop continue/ restart/reprompt however you want to put it if the input was invalid (in the final product this shouldnt matter on user side but just in case)
            }

            System.out.println("Would you like to confirm this class? (Y/N)");
            String confirm = userInput.nextLine().toUpperCase().trim(); //looks complex but just says take the next line, force it upper case and trim and trailing characters

            if(confirm.equals("Y")) {
                classConfirmed = true;
            }
            else {
                System.out.print("Please choose your class again.");
            }
        }

        player.selectedClass = classes; //this actually maps the class the player selected to the player                                                                                                                            
        
        boolean statsConfirmed = false; //give the player the option to reroll stats as many times as they want, if we ressstrict it players will just restart the game
        DiceStatRoller bonusRoller = null; //this will store the bonus roll values

        while (!statsConfirmed) { //this just says as long as the stats arent confirmed keep this loop going
            System.out.println("Please enter 1 to roll your stats"); //eventaully this will be tied to a button once the gui is ready
            int rollStats = userInput.nextInt();
            userInput.nextLine(); 

            bonusRoller = new DiceStatRoller();
            bonusRoller.rollStats(); //this actually "rolls the die" aka chooses random int from 1-20

            System.out.println("\nStats for " + playerName + ":\n"); //adding extra padding but when the gui comes into play shouldnt really matter i think?

            System.out.println(bonusRoller.toString()); //using toString to display the combined stats for the chosen player class + stats rolled. Showing both not just rolled because combined is whats relevant

            if (classes.userChoseSTR != null) { //this is the check for what class was actually selected by player will repeat for other two classes as well
                System.out.println("\nVitality: " + (classes.userChoseSTR.baseVitalitySTR + bonusRoller.playerVitality));//now take this and repeat it for all the other stats and all the other classes, ill come back to this
                System.out.println("\nCharisma: " + (classes.userChoseSTR.baseCharismaSTR + bonusRoller.playerCharisma));
                System.out.println("\nEndurance: " + (classes.userChoseSTR.baseEnduranceSTR + bonusRoller.playerEndurance));
                System.out.println("\nStrength: " + (classes.userChoseSTR.baseStrengthSTR + bonusRoller.playerStrength));
                System.out.println("\nDexterity: " + (classes.userChoseSTR.baseDexteritySTR + bonusRoller.playerDexterity));
                System.out.println("\nIntelligence: " + (classes.userChoseSTR.baseIntelligenceSTR + bonusRoller.playerIntelligence));
            }

            if (classes.userChoseINT != null) { //this is the check for what class was actually selected by player will repeat for other two classes as well
                System.out.println("\nVitality: " + (classes.userChoseINT.baseVitalityINT + bonusRoller.playerVitality));//now take this and repeat it for all the other stats and all the other classes, ill come back to this
                System.out.println("\nCharisma: " + (classes.userChoseINT.baseCharismaINT + bonusRoller.playerCharisma));
                System.out.println("\nEndurance: " + (classes.userChoseINT.baseEnduranceINT + bonusRoller.playerEndurance));
                System.out.println("\nStrength: " + (classes.userChoseINT.baseStrengthINT + bonusRoller.playerStrength));
                System.out.println("\nDexterity: " + (classes.userChoseINT.baseDexterityINT + bonusRoller.playerDexterity));
                System.out.println("\nIntelligence: " + (classes.userChoseINT.baseIntelligenceINT + bonusRoller.playerIntelligence));
            }

            if (classes.userChoseDEX != null) { //this is the check for what class was actually selected by player will repeat for other two classes as well
                System.out.println("\nVitality: " + (classes.userChoseDEX.baseVitalityDEX + bonusRoller.playerVitality));//now take this and repeat it for all the other stats and all the other classes, ill come back to this
                System.out.println("\nCharisma: " + (classes.userChoseDEX.baseCharismaDEX + bonusRoller.playerCharisma));
                System.out.println("\nEndurance: " + (classes.userChoseDEX.baseEnduranceDEX + bonusRoller.playerEndurance));
                System.out.println("\nStrength: " + (classes.userChoseDEX.baseStrengthDEX + bonusRoller.playerStrength));
                System.out.println("\nDexterity: " + (classes.userChoseDEX.baseDexterityDEX + bonusRoller.playerDexterity));
                System.out.println("\nIntelligence: " + (classes.userChoseDEX.baseIntelligenceDEX + bonusRoller.playerIntelligence));
            }

            System.out.println("Please confirm these stats: (Y/N)"); 
            System.out.println("Be aware class and stats cannot be changed.");
            String statsConfirm = userInput.nextLine().toUpperCase().trim();

            if(statsConfirm.equals("Y")) {
                statsConfirmed = true; 
            }
            else{
                System.out.print("Re-rolling stats...");
            }
        }
        player.playerBaseStats = bonusRoller; //assigning base stats to player object

        if (classes.userChoseSTR != null) {
            player.playerHealth = classes.userChoseSTR.baseVitalitySTR + bonusRoller.playerVitality;
        } 
        else if (classes.userChoseDEX != null) {
            player.playerHealth = classes.userChoseDEX.baseVitalityDEX + bonusRoller.playerVitality;
        }

        else if (classes.userChoseINT != null) {
            player.playerHealth = classes.userChoseINT.baseVitalityINT + bonusRoller.playerVitality;
        }

        player.currency = 200; //player start with 200 credits(currency)

        return player; //this is returning all these values the player rolled as an obect "player" this will make working with these stats (in theory, i hope) easier
    }

    public static class PlayerClasses {
        public StrengthBuild userChoseSTR;
        public DexterityBuild userChoseDEX;
        public IntelligenceBuild userChoseINT;
    }

    public static class DiceStatRoller {
        public int playerVitality;
        public int playerStrength;
        public int playerCharisma;
        public int playerIntelligence;
        public int playerDexterity;
        public int playerEndurance;

        public int baseVitality = 0;
        public int baseStrength = 0;
        public int baseCharisma = 0;
        public int baseIntelligence = 0;
        public int baseDexterity = 0;
        public int baseEndurance = 0;

        java.util.Random randDice = new java.util.Random();

        private int rollDie() {
            return randDice.nextInt(20) + 1;
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
            return "Vitality: " + (playerVitality + baseVitality) + "\nStrength: " + (playerStrength + baseStrength) + "\nCharisma: " + (playerCharisma + baseCharisma) +
                   "\nIntelligence: " + (playerIntelligence + baseIntelligence) + "\nDexterity: " + (playerDexterity + baseDexterity) + "\nEndurance: " + (playerEndurance + baseEndurance);
        }
    }

    public static class StrengthBuild {
        public int baseVitalitySTR = 150;
        public int baseCharismaSTR = 70;
        public int baseEnduranceSTR = 100;
        public int baseStrengthSTR = 150;
        public int baseDexteritySTR = 60;
        public int baseIntelligenceSTR = 70;
    }

    public static class DexterityBuild {
        public int baseVitalityDEX = 120;
        public int baseCharismaDEX = 60;
        public int baseEnduranceDEX = 120;
        public int baseStrengthDEX = 90;
        public int baseDexterityDEX = 150;
        public int baseIntelligenceDEX = 60;
    }

    public static class IntelligenceBuild {
        public int baseVitalityINT = 100;
        public int baseCharismaINT = 120;
        public int baseEnduranceINT = 80;
        public int baseStrengthINT = 70;
        public int baseDexterityINT = 80;
        public int baseIntelligenceINT = 150;
    }
}
