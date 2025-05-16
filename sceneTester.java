public class sceneTester {

public static void main(String[] args) {
    SceneManager sceneManager = new SceneManager();
    sceneManager.loadScenes("configFiles/sceneBank.txt");
    sceneManager.enterScene("001");

}

}