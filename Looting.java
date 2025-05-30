import java.util.HashMap;
import java.util.Scanner;

class Looting { //this controls the player actually being prompted/ interacting with the looting enum
    private Container container;
    private String playerInventoryID;
    private DataIO dataIO;

    public Looting(Container container, String playerInventoryID, DataIO dataIO) {
        this.container = container;
        this.playerInventoryID = playerInventoryID;
        this.dataIO = dataIO;
    }

    public void startLooting() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            HashMap<String, GenericItem> lootContents = container.getLootContents();
            if (lootContents.isEmpty()) {
                System.out.println("This container is empty.");
                break;
            }
            System.out.println("Looting " + container.getContainerID() + "... Available items:");
            int index = 1;
            HashMap<Integer, String> selectionMap = new HashMap<>();
            for (String itemID : lootContents.keySet()) {
                GenericItem item = lootContents.get(itemID);
                System.out.println(index + ". " + item.itemName + " (Damage: " + item.baseDamage + ", Armor: " + item.baseArmor + ")");
                selectionMap.put(index, itemID);
                index++;
            }
            System.out.print("Choose an item to take (0 to stop looting): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (choice > 0 && selectionMap.containsKey(choice)) {
                container.lootItem(selectionMap.get(choice), playerInventoryID, dataIO);
            } else {
                System.out.println("Looting session ended.");
                break;
            }
        }
    }
}