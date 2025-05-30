import java.util.HashMap;

class Container { //this is actually the containers, chests, barrels, etc that stores items to be looted, DataIO moves items between .txt and looting prompts player
    private String containerID;
    private HashMap<String, GenericItem> lootContents = new HashMap<>();

    public Container(String containerID) {
        this.containerID = containerID;
    }

    public String getContainerID() {
    return containerID;
    }

    public void addItem(GenericItem item) {
        lootContents.put(item.itemID, item);
    }

    public void lootItem(String itemID, String playerInventoryID, DataIO dataIO) {
        if (lootContents.containsKey(itemID)) {
            dataIO.transferItem(containerID, playerInventoryID, itemID); //move item to player
            lootContents.remove(itemID);
            System.out.println("You looted " + itemID);
        } else {
            System.err.println("Item not found in container.");
        }
    }

    public HashMap<String, GenericItem> getLootContents() {
        return lootContents;
    }
}