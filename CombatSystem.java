import java.util.Scanner;
import java.util.Random;

class CombatSystem { //the beggining of something great
    Random diceRoll = new Random();

    public void combatEngaged(PlayerValues player, GenericNPC defaultNPC, GenericItem weapon) { 
        if(!defaultNPC.npcCanCombat) {
            System.out.println("You can't start a fight with " + defaultNPC.npcName + "."); //this check should keep the player from starting fights with NPCs we dont want them too
            return; //this should end the combat world state, once we get that coded in that is
        }

    String[] combatStartMessages = { //this is just a simple array of strings for the combat intro message choices
        "%s feels the tension rising. Fight with %s is beggining!", 
        "%s squares up against %s. Let the battle begin!",
        "%s locks eyes with %s, ready for combat!",
        "Tension rises as %s prepares to fight %s.",
        "%s faces off against %s—only one will stand victorious!",
        "The duel begins: %s versus %s!",
        "A fierce battle is about to unfold: %s challenges %s!" };

    String combatIntro = String.format(combatStartMessages[diceRoll.nextInt(combatStartMessages.length)], player.playerName, defaultNPC.npcName); //this should print out a random intro when combat is intiated
    System.out.println(combatIntro);

    while(player.playerHealth > 0 && defaultNPC.npcHealth > 0) {
        playerTurn(player, defaultNPC, weapon);
        if(defaultNPC.npcHealth > 0 )  {
            enemyTurn(player, defaultNPC, weapon);
        }
    }

    String[] victoryMessages = {
        "%s stands victorious! %s falls, their defeat undeniable.",
        "Through sheer skill, %s overcomes %s. The battle is won!",
        "%s wipes the sweat from their brow—%s has been defeated.",
        "A final strike lands! %s emerges as the champion over %s.",
        "%s prevails, leaving %s broken and defeated.",
        "The dust settles. %s remains standing, while %s is no more."};

    String[] defeatMessages = {
        "%s collapses, unable to withstand %s's assault.",
        "This time, victory slips away. %s falls at the hands of %s.",
        "%s's body falters—the fight is lost. %s stands victorious.",
        "A brutal final blow lands! %s has been bested by %s.",
        "The battle is over. %s was not strong enough to defeat %s.",
        "A crushing defeat... %s fades as %s claims victory."};

        String combatResult;

    if (player.playerHealth > 0) { // Player Wins
        combatResult = String.format(victoryMessages[diceRoll.nextInt(victoryMessages.length)], player.playerName, defaultNPC.npcName);
        } 
    else { // Player Loses
        combatResult = String.format(defeatMessages[diceRoll.nextInt(defeatMessages.length)], player.playerName, defaultNPC.npcName);
        }

        System.out.println(combatResult);
        }

    private void playerTurn(PlayerValues player, GenericNPC defaultNPC, GenericItem weapon) {
        System.out.println("Choose an action");
        System.out.println("\n1. Attack");
        System.out.println("\n2. Defend");
        System.out.println("\3.Use Item");

        Scanner userCombatChoice = new Scanner(System.in);
        int choice = userCombatChoice.nextInt();
        userCombatChoice.nextLine(); //this makes sure the scanner doesnt wig out like ive had it do to me with other int inputs seemingly randomly

        switch(choice) {
            case 1:

                attack(player, defaultNPC, weapon);
                break;

            case 2:
                
                defend(player, defaultNPC, weapon);
                break;

            case 3:

                useItem(player, defaultNPC);
                break;
            
            default: 
                System.out.println("Somehow you managed to enter an inccorect input, please try again"); 
                break;
        }
    }

    private void enemyTurn(PlayerValues player, GenericNPC enemy) {
        System.out.println("\n" + enemy.npcName + " prepares to attack...");

        int missChance = diceRoll.nextInt(101); //keep as 101 do not change to 100
        if (missChance <= 5) { //5 out of 100 chance for NPC to miss, i feel like this could be done more...elegantly but for now this will do
            System.out.println(enemy.npcName + " puts their all into the attack but misses!");
        return;
    }


        //Randomize damage within min/max range
        int enemyDamage = diceRoll.nextInt(enemy.maxDamage - enemy.minDamage + 1) + enemy.minDamage;

        //reduces damage based on equiped armor
        enemyDamage = Math.max(enemyDamage - player.playerBaseStats.playerEndurance / 2, 1); //this should keep the attack from ever being just 0 damage

        player.playerHealth -= enemyDamage;
        System.out.println(enemy.npcName + " deals " + enemyDamage + " damage!");
    }

    private void attack(PlayerValues player, GenericNPC defaultNPC, GenericItem weapon) {

        int missChance = diceRoll.nextInt(101); //same as above, set to 101 because it actually rolls between 1-100 this way
        if (missChance <= 5) {
            System.out.println(player.playerName + "'s attack missed!");
            return; //this should skip the damage calculations 
        
        }

        int baseDamage = weapon.baseDamage;
        int statMultiplier = getAffinityMultiplier(player, weapon);
        int enemyArmor = defaultNPC.baseArmor;

        int totalDamage = (int) ((baseDamage + statMultiplier) * 1.2) - (enemyArmor/10); //this is just a placeholder formula , the structure is what i want but the values NEED TO BE BALANCED
        totalDamage = Math.max(totalDamage, 0); //this just makes sure the damage doesnt go into negative values

        System.out.println(player.playerName + " attacked with " + weapon.itemName + " dealing " + totalDamage + " damage!");
        defaultNPC.npcHealth -= totalDamage; //this actually makes the damage take effect on the npcs

    }

    private int getAffinityMultiplier(PlayerValues player, GenericItem weapon) {
        int bonusDamage = 0;

        switch(weapon.itemAffinity) {

            case "STR":

            bonusDamage = player.playerBaseStats.playerStrength / 2;
            break;

            case "DEX":

            bonusDamage = player.playerBaseStats.playerDexterity / 2;
            break;

            case "INT":

            bonusDamage = player.playerBaseStats.playerIntelligence / 2;
            break;
            }

        return bonusDamage;
        }

    private void defend(PlayerValues player, GenericNPC defaultNPC, GenericItem weapon) {
        int reduceDamage = diceRoll.nextInt(11) + 5; //this set the default defend value to 5 plus rolled int 1-10
        System.out.println(player.playerName + " steels their nerves and braces to be hit!");

        int enemyDamage = diceRoll.nextInt(defaultNPC.maxDamage - defaultNPC.minDamage + 1) + defaultNPC.minDamage;
        enemyDamage = Math.max(enemyDamage - reduceDamage, 1); // Ensure minimum damage is at least 1

        player.playerHealth -= enemyDamage;
        System.out.println(defaultNPC.npcName + " strikes, but only deals " + enemyDamage + " damage due to your defense!");
    }

}

    private void useItem(PlayerValues player, GenericNPC enemy) {
        //this needs to be made funcitonal but first i want to finish the rest of the combat system and make sure that all I have a good list of items made in the master
    }


