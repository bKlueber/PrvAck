import java.util.Scanner;
import java.util.Random;

public class CombatSystem { //the beggining of something great
    Random diceRoll = new Random();

    public void combatEngaged(PlayerValues player, GenericNPC defaultNPC, GenericItem weapon) { 
        if(!enemy.npcCanCombat) {
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

    String combatIntro = String.format(combatStartMessages[diceRoll.nextInt(combatStartMessages.length)], player.playerName, defaultNPC.npcName); //this should print out a random into when combat is intiated
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

            case 2:
                
                defend(player, defaultNPC);

            case 3:

                useItem(player, defaultNPC);
            
            default: 
                System.out.println("Somehow you managed to enter an inccorect input, please try again"); //right now all these are just meaningless plaecholders but soon they will actually do things
        }
    }

    private void enemyTurn(PlayerValues player, GenericNPC enemy) {
        System.out.println("\n" + enemy.npcName + " prepares to attack...");

        int missChance = diceRoll.nextInt(100);
        if (missChance < 5) { // 5% chance for NPC to miss, i feel like this could be done more...elegantly but for now this will do
            System.out.println(enemy.npcName + " lunges forward but misses!");
        return;
    }


        // Randomize damage within min/max range
        int enemyDamage = diceRoll.nextInt(enemy.maxDamage - enemy.minDamage + 1) + enemy.minDamage;

        // Apply armor reduction
        enemyDamage = Math.max(enemyDamage - player.playerBaseStats.playerEndurance / 2, 1); //this should keep the attack from ever being just 0 damage

        player.playerHealth -= enemyDamage;
        System.out.println(enemy.npcName + " deals " + enemyDamage + " damage!");
    }


}

