import java.util.List;
import java.util.Scanner;

public class GameState {
    private WorldStateEnum worldState;
    private WorldSceneTemplate currentScene;
    private SceneManager sceneManager;

    public GameState(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.worldState = WorldStateEnum.EXPLORING;
    }

    public void setWorldState(WorldStateEnum state) {
        this.worldState = state;
    }

    public void loadScene(String sceneID) {
        WorldSceneTemplate nextScene = sceneManager.getScene(sceneID);
        if (nextScene != null) {
            currentScene = nextScene;
        } else {
            System.err.println("Scene ID " + sceneID + " not found.");
        }
    }

    public void mainGameLoop(PlayerValues player, DataIO dataIO, Scanner scanner) {
        String lastSceneID = null;
        CombatSystem combatSystem = new CombatSystem();
        while (true) {
            if (currentScene == null) {
                System.out.println("No scene loaded. Exiting game.");
                break;
            }
            // Only print if scene has changed
            if (lastSceneID == null || !currentScene.getSceneID().equals(lastSceneID)) {
                currentScene.printScene(player);
                lastSceneID = currentScene.getSceneID();
            }
            System.out.println("-1. Pause Game");
            System.out.print("Choose an action: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (choice == -1) {
                //simulate pause menu
                System.out.println("Game paused. Press Enter to open pause menu...");
                scanner.nextLine();
                boolean inPauseMenu = true;
                while (inPauseMenu) {
                    System.out.println("Pause Menu:");
                    System.out.println("1. View Inventory");
                    System.out.println("0. Resume Game");
                    System.out.print("Choose an option: ");
                    int pauseChoice;
                    try {
                        pauseChoice = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                        continue;
                    }
                    switch (pauseChoice) {
                        case 1:
                            sceneManager.displayInventory(player, dataIO.itemMasterList.get("playerInventory"), player.equippedWeaponID, player.equippedArmorID);
                            System.out.print("Would you like to equip an item? (y/n): ");
                            String equip = scanner.nextLine().trim();
                            if (equip.equalsIgnoreCase("y")) {
                                sceneManager.equipItem(player, dataIO.itemMasterList.get("playerInventory"), scanner);
                            }
                            break;
                        case 0:
                            inPauseMenu = false;
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
                // Reprint scene after unpausing
                currentScene.printScene(player);
                lastSceneID = currentScene.getSceneID();
                continue;
            }

            List<ActionOption> actions = currentScene.getAvailableActions();
            if (choice >= 1 && choice <= actions.size()) {
                ActionOption selectedAction = actions.get(choice - 1);
                String trigger = selectedAction.getTrigger();
                String labelLower = selectedAction.getLabel().toLowerCase();

                // --- Old Lady gives strange token in scene 037 ---
                if (currentScene.getSceneID().equals("037") &&
                    (labelLower.contains("ask what it is") || labelLower.contains("thank her and leave"))) {
                    GenericItem token = dataIO.getOrCreateStrangeToken();
                    if (!dataIO.itemMasterList.get("playerInventory").containsKey(token.itemID)) {
                        dataIO.itemMasterList.get("playerInventory").put(token.itemID, token);
                        System.out.println("You received: " + token.itemName);
                    }
                    if (trigger != null && !trigger.trim().isEmpty()) {
                        loadScene(trigger);
                        lastSceneID = currentScene.getSceneID();
                        currentScene.printScene(player);
                        continue;
                    }
                }

                //ligart takes strange token in scene 042 or 044
                if ((currentScene.getSceneID().equals("042") || currentScene.getSceneID().equals("044")) &&
                    (labelLower.contains("offer token") || labelLower.contains("token"))) {
                    GenericItem token = dataIO.getOrCreateStrangeToken();
                    if (dataIO.itemMasterList.get("playerInventory").containsKey(token.itemID)) {
                        dataIO.itemMasterList.get("playerInventory").remove(token.itemID);
                        System.out.println("You gave the Strange Token to Ligart.");
                    } else {
                        System.out.println("You do not have the Strange Token to give.");
                    }
                    if (trigger != null && !trigger.trim().isEmpty()) {
                        loadScene(trigger);
                        lastSceneID = currentScene.getSceneID();
                        currentScene.printScene(player); //ensure next scene prints
                        continue;
                    }
                }

                if (currentScene.getSceneID().equals("VICARFAVOUR")) {
                    GenericItem sword = dataIO.itemMasterList.get("global").get("ultimatesidesword_001");
                    if (sword != null && !dataIO.itemMasterList.get("playerInventory").containsKey(sword.itemID)) {
                        dataIO.itemMasterList.get("playerInventory").put(sword.itemID, sword);
                        System.out.println("You received: " + sword.itemName + "!");
                    }
                    if (trigger != null && !trigger.trim().isEmpty()) {
                        loadScene(trigger);
                        lastSceneID = currentScene.getSceneID();
                        currentScene.printScene(player);
                        continue;
                    }
                }

                if (currentScene.getSceneID().equals("116")) {
                    GenericItem plasma = dataIO.itemMasterList.get("global").get("plasma_pistol_001");
                    if (plasma != null && !dataIO.itemMasterList.get("playerInventory").containsKey(plasma.itemID)) {
                        dataIO.itemMasterList.get("playerInventory").put(plasma.itemID, plasma);
                        System.out.println("You received: " + plasma.itemName + "!");
                    }
                    if (trigger != null && !trigger.trim().isEmpty()) {
                        loadScene(trigger);
                        lastSceneID = currentScene.getSceneID();
                        currentScene.printScene(player);
                        continue;
                    }
                }

                if (currentScene.getSceneID().equals("100") && player.playerHealth > 0) {
                    GenericItem schematic = dataIO.itemMasterList.get("global").get("schematic_001");
                    if (schematic != null && !dataIO.itemMasterList.get("playerInventory").containsKey(schematic.itemID)) {
                        dataIO.itemMasterList.get("playerInventory").put(schematic.itemID, schematic);
                        System.out.println("You received: " + schematic.itemName + "!");
                    }
                }

                if (currentScene.getSceneID().equals("100_POSTCOMBAT")) {
                    GenericItem schematic = dataIO.itemMasterList.get("global").get("schematic_001");
                    if (schematic != null && !dataIO.itemMasterList.get("playerInventory").containsKey(schematic.itemID)) {
                        dataIO.itemMasterList.get("playerInventory").put(schematic.itemID, schematic);
                        System.out.println("You received: " + schematic.itemName + "!");
                    }
                }

                if (currentScene.getSceneID().equals("101") && labelLower.contains("share with engineers")) {
                    if (dataIO.itemMasterList.get("playerInventory").containsKey("schematic_001")) {
                        dataIO.itemMasterList.get("playerInventory").remove("schematic_001");
                        System.out.println("You gave the schematic to the engineers.");
                    } else {
                        System.out.println("You do not have the schematic to share.");
                    }
                    if (trigger != null && !trigger.trim().isEmpty()) {
                        loadScene(trigger);
                        lastSceneID = currentScene.getSceneID();
                        currentScene.printScene(player);
                        continue;
                    }
                }

                if (trigger.equalsIgnoreCase("LOOTING")) {
                    String containerID = sceneManager.findContainerForScene(currentScene.getSceneID());
                    if (containerID != null) {
                        sceneManager.handleLooting(containerID);
                        //after looting, reprint the scene
                        currentScene.printScene(player);
                        lastSceneID = currentScene.getSceneID();
                    } else {
                        System.out.println("There's no container to loot here.");
                    }
                } else if (trigger.startsWith("COMBAT(")) {
                    setWorldState(WorldStateEnum.COMBAT);
                    try {
                        //debug: Print the raw trigger string, this can be removed before production
                        System.out.println("DEBUG: Raw trigger: '" + trigger + "'");
                        if (!trigger.contains("(") || !trigger.contains(")")) {
                            System.out.println("ERROR: COMBAT trigger missing parentheses: " + trigger);
                            setWorldState(WorldStateEnum.EXPLORING);
                            continue;
                        }
                        int openIdx = trigger.indexOf('(');
                        int closeIdx = trigger.lastIndexOf(')');
                        if (openIdx < 0 || closeIdx < 0 || closeIdx <= openIdx + 1) {
                            System.out.println("ERROR: COMBAT trigger parentheses positions invalid: " + trigger);
                            setWorldState(WorldStateEnum.EXPLORING);
                            continue;
                        }
                        String params = trigger.substring(openIdx + 1, closeIdx).trim();
                        System.out.println("DEBUG: Extracted params: '" + params + "'");
                        String[] parts = params.split(",", 2);
                        if (parts.length < 1) {
                            System.out.println("ERROR: COMBAT trigger missing npcID: " + trigger);
                            setWorldState(WorldStateEnum.EXPLORING);
                            continue;
                        }
                        String npcID = parts[0].trim();
                        String nextSceneID = (parts.length > 1) ? parts[1].trim() : null;

                        System.out.println("DEBUG: Loaded NPCs: " + dataIO.npcMasterList.keySet());
                        System.out.println("DEBUG: Parsed npcID: '" + npcID + "'");

                        //try to find the NPC (case-insensitive)
                        GenericNPC npc = null;
                        for (String key : dataIO.npcMasterList.keySet()) {
                            if (key.trim().equalsIgnoreCase(npcID)) {
                                npc = dataIO.npcMasterList.get(key);
                                break;
                            }
                        }
                        if (npc == null) {
                            System.out.println("Error: NPC not found for combat. (Tried: '" + npcID + "')");
                            setWorldState(WorldStateEnum.EXPLORING);
                            continue;
                        }
                        //get equipped weapon or fallback to fists
                        GenericItem weapon = null;
                        if (player.equippedWeaponID != null && dataIO.itemMasterList.get("playerInventory").containsKey(player.equippedWeaponID)) {
                            weapon = dataIO.itemMasterList.get("playerInventory").get(player.equippedWeaponID);
                        }
                        //if no weapon, use fists (handled in CombatSystem)
                        combatSystem.combatEngaged(player, npc, weapon, dataIO.itemMasterList.get("playerInventory"), dataIO, scanner);

                        // reset health after combat so player can continue exploring fully healed
                        player.playerHealth = player.playerBaseStats.playerVitality + (
                            player.selectedClass.userChoseSTR != null ? player.selectedClass.userChoseSTR.baseVitalitySTR :
                            player.selectedClass.userChoseDEX != null ? player.selectedClass.userChoseDEX.baseVitalityDEX :
                            player.selectedClass.userChoseINT != null ? player.selectedClass.userChoseINT.baseVitalityINT : 100
                        );
                        npc.npcHealth =  dataIO.npcMasterList.get(npc.npcID).npcHealth; //reset to default if needed, if we want repeated combat with same NPC

                        //set world state back to EXPLORING
                        setWorldState(WorldStateEnum.EXPLORING);

                        if (player.playerHealth > 0 && nextSceneID != null && !nextSceneID.isEmpty()) {
                            loadScene(nextSceneID);
                            lastSceneID = nextSceneID;
                            currentScene.printScene(player); //ensure next scene prints after combat
                        } else if (player.playerHealth > 0) {
                            System.out.println("No next scene specified after combat.");
                        }
                        //if player lost, combat system handles restart/exit
                    } catch (Exception e) {
                        System.out.println("Combat trigger format error: " + e.getMessage());
                        setWorldState(WorldStateEnum.EXPLORING);
                    }
                } else if (trigger.equalsIgnoreCase("HAGGLE")) {
                    //HAGGLE logic: d20 + (charisma/10) >= 15, for act one scenes only
                    int d20 = (int)(Math.random() * 20) + 1;
                    int charisma = player.playerBaseStats.playerCharisma;
                    int total = d20 + (charisma / 10);
                    boolean success = total >= 15;
                    System.out.println(player.playerName + " rolled a " + d20 + " + charisma bonus (" + (charisma / 10) + ") = " + total + " for Haggle.");
                    if (success) {
                        System.out.println("Haggle Success!");
                        loadScene("HaggleSuccess");
                    } else {
                        System.out.println("Haggle Failed!");
                        loadScene("HaggleFail");
                    }
                    lastSceneID = currentScene.getSceneID();
                } else if (trigger.equalsIgnoreCase("INTIMIDATE")) {
                    int d20 = (int)(Math.random() * 20) + 1;
                    int strength = player.playerBaseStats.playerStrength;
                    int total = d20 + (strength / 10);
                    boolean success = total >= 15;
                    System.out.println(player.playerName + " rolled a " + d20 + " + strength bonus (" + (strength / 10) + ") = " + total + " for Intimidate.");
                    if (success) {
                        System.out.println("Intimidate Success!");
                        loadScene("IntimidateSuccess");
                    } else {
                        System.out.println("Intimidate Failed!");
                        loadScene("IntimidateFail");
                    }
                    lastSceneID = currentScene.getSceneID();
                } else if (trigger.equalsIgnoreCase("REPAIR")) {
                    int d20 = (int)(Math.random() * 20) + 1;
                    int intelligence = player.playerBaseStats.playerIntelligence;
                    int total = d20 + (intelligence / 10);
                    boolean success = total >= 15;
                    System.out.println(player.playerName + " rolled a " + d20 + " + intelligence bonus (" + (intelligence / 10) + ") = " + total + " for Repair.");
                    if (success) {
                        System.out.println("Repair Success!");
                        loadScene("REPAIRSUCCESS");
                    } else {
                        System.out.println("Repair Failed!");
                        loadScene("REPAIRFAIL");
                    }
                    lastSceneID = currentScene.getSceneID();
                } else if (trigger.equalsIgnoreCase("REPAIR2")) {
                    int d20 = (int)(Math.random() * 20) + 1;
                    int intelligence = player.playerBaseStats.playerIntelligence;
                    int total = d20 + (intelligence / 10);
                    boolean success = total >= 15;
                    System.out.println(player.playerName + " rolled a " + d20 + " + intelligence bonus (" + (intelligence / 10) + ") = " + total + " for Advanced Repair.");
                    if (success) {
                        System.out.println("Repair2 Success!");
                        loadScene("REPAIR2SUCCESS");
                    } else {
                        System.out.println("Repair2 Failed!");
                        loadScene("REPAIR2FAIL");
                    }
                    lastSceneID = currentScene.getSceneID();
                } else if (trigger.startsWith("WHITEFLOWER_ROLL")) {
                    int d20 = (int)(Math.random() * 20) + 1;
                    int dex = player.playerBaseStats.playerDexterity;
                    int total = d20 + (dex / 10);
                    boolean success = total >= 15;
                    System.out.println(player.playerName + " rolled a " + d20 + " + dexterity bonus (" + (dex / 10) + ") = " + total + " for Whiteflower event.");
                    String rollNum = trigger.replace("WHITEFLOWER_ROLL", "");
                    if (success) {
                        player.breachesSealed++;
                        loadScene("WHITEFLOWER_SUCCESS" + rollNum);
                    } else {
                        loadScene("WHITEFLOWER_FAIL" + rollNum);
                    }
                    lastSceneID = currentScene.getSceneID();
                } else if (trigger.equalsIgnoreCase("PUZZLECHECK")) {
                    int d20 = (int)(Math.random() * 20) + 1;
                    int intelligence = player.playerBaseStats.playerIntelligence;
                    int total = d20 + (intelligence / 10);
                    boolean success = total >= 15;
                    System.out.println(player.playerName + " rolled a " + d20 + " + intelligence bonus (" + (intelligence / 10) + ") = " + total + " for Puzzle.");
                    if (success) {
                        System.out.println("Puzzle solved!");
                        loadScene("106");
                    } else {
                        System.out.println("Puzzle failed!");
                        loadScene("105_FAIL");
                    }
                    lastSceneID = currentScene.getSceneID();
                } else {
                    loadScene(trigger);
                }
            } else {
                System.out.println("Invalid choice.");
            }

            if (currentScene.getSceneID().equals("ACT1_INTAC")) {
                player.currentFaction = "Immortals";
            } else if (currentScene.getSceneID().equals("ACT1_BUGGED")) {
                player.currentFaction = "Shadow Garden";
            } else if (currentScene.getSceneID().equals("ACT1_ENGINEER") || currentScene.getSceneID().equals("ACT1_GANG") || currentScene.getSceneID().equals("ACT1_NEUTRAL")) {
                player.currentFaction = "Mortals";
            }

            if (currentScene.getSceneID().equals("112")) {
                String faction = player.currentFaction;
                String nextScene = null;
                if ("Mortals".equalsIgnoreCase(faction)) {
                    nextScene = "WINDOWEVENTMORTAL";
                } else if ("Immortals".equalsIgnoreCase(faction)) {
                    nextScene = "WINDOWEVENTIMMORTAL";
                } else if ("Shadow Garden".equalsIgnoreCase(faction)) {
                    nextScene = "WINDOWEVENTSHADGARDEN";
                }
                if (nextScene != null) {
                    loadScene(nextScene);
                    lastSceneID = currentScene.getSceneID();
                    currentScene.printScene(player);
                    continue;
                } else {
                    System.out.println("Faction not set. Cannot determine window event.");
                }
            }
            if (currentScene.getSceneID().equals("109")) {
                player.playerBaseStats.playerVitality += 10;
                player.playerBaseStats.playerStrength += 5;
                player.playerBaseStats.playerDexterity += 5;
                player.playerBaseStats.playerIntelligence += 5;
                System.out.println("You feel a surge of power! +10 Vitality, +5 Strength, +5 Dexterity, +5 Intelligence (permanent).");
            }
        }
    }
}