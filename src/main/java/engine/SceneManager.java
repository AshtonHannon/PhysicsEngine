package engine;

import scenes.Scene;

public class SceneManager
{
    private static SceneManager instance;
    private static Scene currentScene;


    private SceneManager()
    { }


    public static SceneManager get()
    {
        // Ensure that the scene passed through is not null
        if (SceneManager.instance == null)
            SceneManager.instance = new SceneManager();
        return SceneManager.instance;
    }


    public void setCurrentScene(Scene newScene)
    {
        if (newScene == null)
            throw new NullPointerException("[SceneManager] Tried to setCurrentScene() with null scene!");
        System.out.println("[SceneManager] Changing to new scene: " + newScene);
        SceneManager.currentScene = newScene;
        SceneManager.currentScene.init();
    }


    public Scene getCurrentScene() { return SceneManager.currentScene; }
}
