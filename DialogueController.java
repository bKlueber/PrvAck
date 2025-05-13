import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class DialogueController {
    private HashMap<String, String[]> dialogueMap = new HashMap<>(); 

public void loadDialogue(String filePath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length >= 7) { // Ensure enough parts exist, see the  .txt or future documentation on how these work
                dialogueMap.put(parts[3], parts); // Use dialogue ID as key
            }
        }
        System.out.println("Dialogue loaded successfully!"); // Debugging confirmation, can be removed later
    } catch (IOException e) {
        System.err.println("Error loading dialogue: " + e.getMessage());
    }
}

public void startDialogue(String dialogueID) {
    if (dialogueMap.containsKey(dialogueID)) {
        String[] dialogueData = dialogueMap.get(dialogueID);
        System.out.println(dialogueData[1] + ": " + dialogueData[4]); // NPC_Name: Dialogue_Text are the fields that print
        
        // Store possible choices
        List<String> choices = new ArrayList<>();
        List<String> nextDialogueIDs = new ArrayList<>();
        
        for (int i = 5; i < dialogueData.length; i += 2) { // Iterate through responses & IDs
            choices.add(dialogueData[i]); // Add response as possible choice
            nextDialogueIDs.add(dialogueData[i + 1]); // Add corresponding next dialogue ID
        }
        
        for (int i = 0; i < choices.size(); i++) {// Display choices to player
            System.out.println((i + 1) + ". " + choices.get(i));
        }
        
     
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();// Get player's choice
        
        if (choice > 0 && choice <= nextDialogueIDs.size()) {
            String nextDialogueID = nextDialogueIDs.get(choice - 1);
            if (!nextDialogueID.equals("0")) {
                startDialogue(nextDialogueID); // Move to selected branch
            } 
            else {
                System.out.println("Conversation ended.");
            }
        } 
        else {
            System.out.println("Invalid choice. Try again.");
            startDialogue(dialogueID);
        }
    } 
    else {
        System.out.println("Dialogue ID '" + dialogueID + "' not found. Please check your input.");
        }
    }
}