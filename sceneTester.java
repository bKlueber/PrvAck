import java.util.Scanner;

public class sceneTester {
    public static void main(String[] args) {
        DataIO dataIO = new DataIO();
        dataIO.loadItems("configFiles/itemMaster.txt", "global");
        dataIO.loadItems("configFiles/playerInventory.txt", "playerInventory");
        dataIO.loadNPCs("configFiles/masterNPC.txt");
        dataIO.loadNPCInventories("configFiles/npcInventories.txt");
        SceneManager sceneManager = new SceneManager();
        sceneManager.setDataIO(dataIO);
        dataIO.loadContainerItems("configFiles/containerMaster.txt", sceneManager.getContainerMap());

        CharacterCreator creator = new CharacterCreator();
        Scanner scanner = new Scanner(System.in);
        PlayerValues player = creator.createCharacter();

        System.out.println("\nAre you ready " + player.playerName + " to begin your adventure?");
        System.out.println("Enter 1 to begin the journey...");
        while (!scanner.nextLine().trim().equals("1")) {
            System.out.println("Please enter 1 to begin.");
        }

        sceneManager.loadScenes("configFiles/sceneBank.txt");

        GameState gameState = new GameState(sceneManager);
        gameState.loadScene("001");
        gameState.mainGameLoop(player, dataIO, scanner); 
    }
}