public class CharacterCreatorTest {
    public static void main(String[] args) {
        CharacterCreator creator = new CharacterCreator();
        //create a character by prompting the user for input
        PlayerValues player = creator.createCharacter();
        
        //display the created character's information for verification
        System.out.println("\nCharacter Created!");
        System.out.println("Name: " + player.playerName);
        System.out.println("Health: " + player.playerHealth);
        System.out.println("Rolled Stats:\n" + player.playerBaseStats);
    }
}