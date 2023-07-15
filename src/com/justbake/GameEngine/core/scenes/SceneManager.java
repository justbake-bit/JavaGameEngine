package com.justbake.GameEngine.core.scenes;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {
    private static SceneManager instance;
    private Scene currentScene;

    public static SceneManager getInstance() {
        if(instance == null)
            instance = new SceneManager();
        return instance;
    }

    public static void changeScene(Scene scene){
        if(getInstance().currentScene != null)
            getInstance().currentScene.dispose();
        System.out.println("Changing scene from " + getInstance().currentScene + " to " + scene);
        getInstance().currentScene = scene;
        getInstance().currentScene.init();
    }

    public static void update(){
        getInstance().currentScene.update();
    }

    public static void render(){
        getInstance().currentScene.render();
    }

    public static void dispose() {
        Scene current = getInstance().currentScene;
        if(current != null)
            current.dispose();
    }
}
