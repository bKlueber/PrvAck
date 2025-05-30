public class GenericItem{ //this is the class that is used for every single item in the game. See items.txt for more information

    String itemName;
    String itemID;
    String itemDescription;
    double itemValue;
    double itemWeight; //not neccessary but in my (Brady) opinion i think it adds depth to our systems
    int baseDamage; //will not be applicable to every item but by defining it here, and then assigning it in the txt we will save a lot of time and effort
    int baseArmor; //same as baseDamage, as in may be null for some items but will make adjusting and balancing worlds easier
    String itemAffinity; //this is going to help so i can make damage scale per class for certain types of items, right now just going to apply to weapons not armor
    boolean isArmorSet = false; //this is a boolean that will be used to determine if the item is an armor set or not, so it can be equipped properly

        public GenericItem(String  itemName, String itemID, String itemDescription, double itemValue, double itemWeight, int itemDamage, int itemArmor, String itemAffinity) {  //this is matching the paremeter to instances of each variable
            this.itemName = itemName;
            this.itemID = itemID;
            this.itemDescription = itemDescription;
            this.itemValue = itemValue;
            this.itemWeight = itemWeight;
            this.baseDamage = itemDamage;
            this.baseArmor = itemArmor;
            this.itemAffinity  = itemAffinity;
            this.isArmorSet = (itemArmor > 0 && itemDamage == 0);//this checks if the item is an armor set by checking if it has armor but no damage
        }

}