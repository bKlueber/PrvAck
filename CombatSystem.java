import java.util.*;

class CombatSystem {
    Random diceRoll = new Random();

    public String combatEngaged(PlayerValues player, GenericNPC defaultNPC, GenericItem weapon, HashMap<String, GenericItem> playerInventory, DataIO dataIO, Scanner scanner) {
        if (!defaultNPC.npcCanCombat) {
            System.out.println("You can't start a fight with " + defaultNPC.npcName + ".");
            return null;
        }

        String[] combatStartMessages = {
            "%s feels the tension rising. Fight with %s is beginning!",
            "%s squares up against %s. Let the battle begin!",
            "%s locks eyes with %s, ready for combat!",
            "Tension rises as %s prepares to fight %s.",
            "%s faces off against %s—only one will stand victorious!",
            "The duel begins: %s versus %s!",
            "A fierce battle is about to unfold: %s challenges %s!"
        };

        String combatIntro = String.format(combatStartMessages[diceRoll.nextInt(combatStartMessages.length)], player.playerName, defaultNPC.npcName);
        System.out.println(combatIntro);

        int playerStunsLeft = 3;
        int npcStunsLeft = 3;
        int playerStunCounter = 0;
        int npcStunCounter = 0;

        while (player.playerHealth > 0 && defaultNPC.npcHealth > 0) {
            System.out.println("\n--- Status ---");
            System.out.println(player.playerName + " HP: " + player.playerHealth + " | Stuns left: " + playerStunsLeft);
            System.out.println(defaultNPC.npcName + " HP: " + defaultNPC.npcHealth + " | Stuns left: " + npcStunsLeft);

            boolean skipNpcTurn = false;

            // Player turn
            if (playerStunCounter > 0) {
                System.out.println(player.playerName + " is stunned and loses their turn!");
                playerStunCounter--;
            } else {
                int prevHealth = player.playerHealth;
                boolean stunned = playerTurn(player, defaultNPC, weapon, playerInventory, dataIO, scanner, playerStunsLeft, npcStunCounter > 0, npcStunsLeft);
                //if defend was chosen, player's health will have changed, but no need to run npcTurn this round
                if (player.playerHealth != prevHealth) {
                    skipNpcTurn = true;
                }
                if (stunned) {
                    playerStunsLeft--;
                    npcStunCounter = 2; //NPC skips next round
                }
            }
            if (defaultNPC.npcHealth <= 0) break;

            //NPC turn
            if (!skipNpcTurn) {
                if (npcStunCounter > 0) {
                    System.out.println(defaultNPC.npcName + " is stunned and loses their turn!");
                    npcStunCounter--;
                } else {
                    boolean stunned = npcTurn(player, defaultNPC, weapon, playerStunsLeft, npcStunsLeft);
                    if (stunned) {
                        npcStunsLeft--;
                        playerStunCounter = 2; //Player skips next round
                    }
                }
            }
        }

        //end of combat
        String[] victoryMessages = {
            "%s stands victorious! %s falls, their defeat undeniable.",
            "Through sheer skill, %s overcomes %s. The battle is won!",
            "%s wipes the sweat from their brow—%s has been defeated.",
            "A final strike lands! %s emerges as the champion over %s.",
            "%s prevails, leaving %s broken and defeated.",
            "The dust settles. %s remains standing, while %s is no more."
        };

        String[] defeatMessages = {
            "%s collapses, unable to withstand %s's assault.",
            "This time, victory slips away. %s falls at the hands of %s.",
            "%s's body falters—the fight is lost. %s stands victorious.",
            "A brutal final blow lands! %s has been bested by %s.",
            "The battle is over. %s was not strong enough to defeat %s.",
            "A crushing defeat... %s fades as %s claims victory."
        };

        String combatResult;
        if (player.playerHealth > 0) {
            combatResult = String.format(victoryMessages[diceRoll.nextInt(victoryMessages.length)], player.playerName, defaultNPC.npcName);
            System.out.println(combatResult);
            //give player random money
            int reward = diceRoll.nextInt(21) + 10; // 10-30 currency
            player.currency += reward;
            System.out.println("You loot " + reward + " credits from " + defaultNPC.npcName + "!");

            //prompt player for post-combat random loot
            System.out.print("Would you like to loot " + defaultNPC.npcName + "? (y/n): ");
            String lootNpcChoice = scanner.nextLine().trim();
            if (lootNpcChoice.equalsIgnoreCase("y")) {
                HashMap<String, GenericItem> globalItems = dataIO.itemMasterList.get("global");
                if (globalItems == null || globalItems.isEmpty()) {
                    System.out.println("No global items available for loot.");
                } else {
                    List<GenericItem> allItems = new ArrayList<>(globalItems.values());
                    int lootCount = diceRoll.nextInt(6) + 2; // 2-7 items
                    List<GenericItem> loot = new ArrayList<>();
                    for (int i = 0; i < lootCount && !allItems.isEmpty(); i++) {
                        int idx = diceRoll.nextInt(allItems.size());
                        loot.add(allItems.remove(idx));
                    }
                    HashMap<String, GenericItem> playerInv = playerInventory;
                    while (!loot.isEmpty()) {
                        System.out.println("You find the following items:");
                        for (int i = 0; i < loot.size(); i++) {
                            GenericItem item = loot.get(i);
                            System.out.println((i + 1) + ". " + item.itemName + " (Damage: " + item.baseDamage + ", Armor: " + item.baseArmor + ")");
                        }
                        System.out.print("Enter the number of the item to take (or 0 to stop looting): ");
                        String input = scanner.nextLine().trim();
                        int choice;
                        try {
                            choice = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                            continue;
                        }
                        if (choice == 0) {
                            System.out.println("You stop looting.");
                            break;
                        }
                        if (choice >= 1 && choice <= loot.size()) {
                            GenericItem item = loot.remove(choice - 1);
                            playerInv.put(item.itemID, item);
                            System.out.println("You looted: " + item.itemName);
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    }
                    if (loot.isEmpty()) {
                        System.out.println("There's nothing left to loot.");
                    }
                }
            } else {
                System.out.println("You skip looting " + defaultNPC.npcName + ".");
            }
        } else {
            combatResult = String.format(defeatMessages[diceRoll.nextInt(defeatMessages.length)], player.playerName, defaultNPC.npcName);
            System.out.println(combatResult);
            //game over options, if player dies in combat
            System.out.println("Game Over! 1. Quit  2. Restart Fight");
            int goChoice = scanner.nextInt();
            scanner.nextLine();
            if (goChoice == 2) {
                //reset health and restart fight, can maybe make it so they can choose to restart from last save instead
                player.playerHealth = 100; 
                defaultNPC.npcHealth = 100; 
                combatEngaged(player, defaultNPC, weapon, playerInventory, dataIO, scanner);
            } else {
                System.out.println("Quitting game...");
                System.exit(0);
            }
        }
        return null; //next scene handled by caller
    }

    //returns true if player used stun
    private boolean playerTurn(PlayerValues player, GenericNPC defaultNPC, GenericItem weapon, HashMap<String, GenericItem> playerInventory, DataIO dataIO, Scanner scanner, int playerStunsLeft, boolean npcStunned, int npcStunsLeft) {
        System.out.println("Choose an action:");
        System.out.println("1. Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item");
        if (playerStunsLeft > 0) System.out.println("4. Stun (remaining: " + playerStunsLeft + ")");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                attack(player, defaultNPC, weapon);
                return false;
            case 2:
                defend(player, defaultNPC, weapon);
                //prevent NPC from attacking again this round after defend
                //indicate to main loop that NPC's turn should be skipped
                return false; //add a flag if you want to skip NPC turn, but for now, just return false
            case 3:
                useItem(player, playerInventory, dataIO, scanner);
                return false;
            case 4:
                if (playerStunsLeft > 0) {
                    System.out.println(player.playerName + " attempts to stun " + defaultNPC.npcName + "!");
                    if (diceRoll.nextInt(100) < 60) { // 60% chance to succeed
                        System.out.println("Stun successful! " + defaultNPC.npcName + " will miss their next turn.");
                        return true;
                    } else {
                        System.out.println("Stun failed!");
                    }
                } else {
                    System.out.println("No stuns left!");
                }
                return false;
            default:
                System.out.println("Invalid input.");
                return false;
        }
    }

    //returns true if NPC used stun
    private boolean npcTurn(PlayerValues player, GenericNPC enemy, GenericItem weapon, int playerStunsLeft, int npcStunsLeft) {
        //25% chance to try stun if stuns left, had it as 10% before and it rarely would trigger
        if (npcStunsLeft > 0 && diceRoll.nextInt(100) < 25) {
            System.out.println(enemy.npcName + " attempts to stun " + player.playerName + "!");
            if (diceRoll.nextInt(100) < 60) { //60% chance to succeed stunning player
                System.out.println("Stun successful! " + player.playerName + " will miss their next turn.");
                return true;
            } else {
                System.out.println("Stun failed!");
            }
            return false;
        }

        //5% miss chance
        if (diceRoll.nextInt(100) < 5) {
            System.out.println(enemy.npcName + " puts their all into the attack but misses!");
            return false;
        }

        //critical hit chance
        boolean crit = diceRoll.nextInt(100) < 5;
        int enemyDamage = diceRoll.nextInt(enemy.maxDamage - enemy.minDamage + 1) + enemy.minDamage;
        enemyDamage = Math.max(enemyDamage - player.playerBaseStats.playerEndurance / 2, 1);
        if (crit) {
            enemyDamage *= 2;
            System.out.println("Critical hit! " + enemy.npcName + " deals " + enemyDamage + " damage!");
        } else {
            System.out.println(enemy.npcName + " deals " + enemyDamage + " damage!");
        }
        player.playerHealth = Math.max(player.playerHealth - enemyDamage, 0);
        return false;
    }

    private void attack(PlayerValues player, GenericNPC defaultNPC, GenericItem weapon) {
        //5% miss chance
        if (diceRoll.nextInt(100) < 5) {
            System.out.println(player.playerName + "'s attack missed!");
            return;
        }
        //critical hit chance
        boolean crit = diceRoll.nextInt(100) < 5;
        int totalDamage = 0;
        String weaponName;
        if (weapon == null) {
            //fist fighting: damage = strength/10, this happens if player has no weapon equipped
            totalDamage = Math.max(player.playerBaseStats.playerStrength / 10, 1);
            weaponName = "their fists";
        } else {
            int baseDamage = weapon.baseDamage;
            int statMultiplier = getAffinityMultiplier(player, weapon);
            int enemyArmor = defaultNPC.baseArmor;
            totalDamage = (int) ((baseDamage + statMultiplier) * 1.2) - (enemyArmor / 10);
            totalDamage = Math.max(totalDamage, 0);
            weaponName = weapon.itemName;
        }
        if (crit) {
            totalDamage *= 2;
            System.out.println("Critical hit! " + player.playerName + " attacked with " + weaponName + " dealing " + totalDamage + " damage!");
        } else {
            System.out.println(player.playerName + " attacked with " + weaponName + " dealing " + totalDamage + " damage!");
        }
        defaultNPC.npcHealth = Math.max(defaultNPC.npcHealth - totalDamage, 0);
    }

    private int getAffinityMultiplier(PlayerValues player, GenericItem weapon) {
        int bonusDamage = 0;
        switch (weapon.itemAffinity) {
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
        //defend: reduce incoming damage by player's endurance + random 5-15
        int reduceDamage = player.playerBaseStats.playerEndurance + (diceRoll.nextInt(11) + 5);
        System.out.println(player.playerName + " steels their nerves and braces to be hit!");
        int enemyDamage = diceRoll.nextInt(defaultNPC.maxDamage - defaultNPC.minDamage + 1) + defaultNPC.minDamage;
        int finalDamage = Math.max(enemyDamage - reduceDamage, 0);
        player.playerHealth = Math.max(player.playerHealth - finalDamage, 0);
        System.out.println(defaultNPC.npcName + " strikes, but only deals " + finalDamage + " damage due to your defense!");
    }

    private void useItem(PlayerValues player, HashMap<String, GenericItem> playerInventory, DataIO dataIO, Scanner scanner) {
        // List healing items
        List<String> healingItems = new ArrayList<>();
        int idx = 1;
        for (String itemID : playerInventory.keySet()) {
            GenericItem item = playerInventory.get(itemID);
            if (item.itemName.toLowerCase().contains("potion") || item.itemDescription.toLowerCase().contains("heal")) {
                System.out.println(idx + ". " + item.itemName + " (Heals 25 HP)");
                healingItems.add(itemID);
                idx++;
            }
        }
        if (healingItems.isEmpty()) {
            System.out.println("No healing items available!");
            return;
        }
        System.out.print("Choose a healing item to use (or 0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice > 0 && choice <= healingItems.size()) {
            String healID = healingItems.get(choice - 1);
            player.playerHealth += 25; //potions heal 25 HP
            playerInventory.remove(healID);
            dataIO.itemMasterList.get("playerInventory").remove(healID); //remove from DataIO as well, this way the item actually is used
            System.out.println("You used " + healID + " and healed 25 HP!");
        } else {
            System.out.println("Cancelled.");
        }
    }
}


