import java.util.HashMap;

class GenericNPC{

    String npcName;
    String npcID;
    String npcDescription; //not neccessary but including it for now, I (Brady) was thinkng haveng like a codex on npcs that populates af you interact with them for the first time
    int npcHealth;
    boolean npcCanCombat; //this will be a check to see if npc can be attacked by player or not, well one check of many, probably
    String npcFaction; //can be null but if, and big if we want to have reputation based on faction this will help
    int baseArmor;
    int minDamage;
    int maxDamage; //these will create ranges for npc damage output to hopefully make it so not all npcs feel the same
    HashMap<String, GenericItem> npcInventory = new HashMap<>(); //addingg this in so we can assign inventories to npcs, that way they can drop, give items etc

    public GenericNPC( String npcName, String npcID, String npcDescription, int npcHealth, boolean npcCanCombat, String npcFaction,
        int baseArmor, int minDamage, int maxDamage) {
        this.npcName = npcName;
        this.npcID = npcID;
        this.npcDescription = npcDescription;
        this.npcHealth = npcHealth;
        this.npcCanCombat = npcCanCombat;
        this.npcFaction = npcFaction;
        this.baseArmor = baseArmor;
        this.minDamage = minDamage; //these are added to be dificulty controllers, like we can make for example tutorial npcs easier and end game ones harder by changing their ranges
        this.maxDamage = maxDamage; //this seemed like the easiest way to control difficulty spikes
        }

    public void addItem(GenericItem item) {
        npcInventory.put(item.itemID, item);
    }

     public void displayInventory() {
        System.out.println(npcName + "'s Inventory:");
        
        for (GenericItem item : npcInventory.values()) {
            System.out.println("- " + item.itemName);
        }
    }
}