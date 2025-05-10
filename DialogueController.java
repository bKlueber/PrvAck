import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

class DialogueController {
    private HashMap<String, String[]> dialogueMap = new HashMap<>();

    public void loadDialogue(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    dialogueMap.put(parts[3], parts); //this maket he dialogue id the key to the map
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading dialogue: " + e.getMessage());
        }
    }

    public void startDialogue(String dialogueID) {
        if (dialogueMap.containsKey(dialogueID)) {
            String[] dialogueData = dialogueMap.get(dialogueID);
            System.out.println(dialogueData[1] + ": " + dialogueData[4]); // NPC_Name: Dialogue_Text
        }
        else {
            System.out.println("Dialogue ID '" + dialogueID + "' not found. Please check your input.");
        }
    }
}
