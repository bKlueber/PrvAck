public class PrvAck{
//right now nothing to add here
    
} 

class GenericItem{ //this is the class that is used for every single item in the game. See items.txt for more information

    String itemName;
    String itemDescription;
    double itemValue;
    double itemWeight; //not neccessary but in my (Brady) opinion i think it adds depth to our systems
    int baseDamage; //will not be applicable to every item but by defining it here, and then assigning it in the txt we will save a lot of time and effort
    int baseArmor; //same as baseDamage, as in may be null for some items but will make adjusting and balancing worlds easier
    
        public GenericItem genericItem (String  name, String description, double value, double weight, int damage, int armor) {  //this is matching the paremeter to instances of each variable
            this.itemName = name;
            this.itemDescription = description;
            this.itemValue = value;
            this.itemWeight = weight;
            this.baseDamage = damage;
            this.baseArmor = armor;
        }

}