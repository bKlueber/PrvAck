public enum WorldStateEnum { //these should be the only four states becuase even in Exposition from the game it can just be dialogue modified
    EXPLORING,   // Default state when moving between scenes
    COMBAT,      // Triggers only during combat encounters
    DIALOGUE,    // Activates only when speaking to NPCs
    LOOTING      // Activates only when interacting with lootable containers
}