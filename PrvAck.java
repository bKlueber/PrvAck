
public class PrvAck{
//right now nothing to add here
    public static void main(String[] args) { //none of this nneeds to be here in the end
        DataIO dataIO = new DataIO();

        //test loading items
        dataIO.loadItems("configFiles/playerInventory.txt", "playerInventory");
        System.out.println("Loaded player inventory: " + dataIO.itemMasterList.get("playerInventory"));

        //test transferring an item
        dataIO.transferItem("playerInventory", "npcInventory_001", "sword_001");
        System.out.println("After transfer: " + dataIO.itemMasterList.get("npcInventory_001"));

        //test saving inventories
        dataIO.saveInventories("configFiles/playerInventory.txt");
        System.out.println("Inventory saved successfully.");
    }
}
    

