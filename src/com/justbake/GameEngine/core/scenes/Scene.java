package com.justbake.GameEngine.core.scenes;


import com.justbake.GameEngine.core.Window;
import com.justbake.GameEngine.core.scenes.gameObject.GameObject;
import com.justbake.GameEngine.core.scenes.gameObject.components.CameraComponent;

import java.util.ArrayList;
import java.util.List;

public final class Scene {

    private final List<GameObject> gameObjects;
    private final String name;
    private Window window;
    private CameraComponent activeCamera;

    public Scene(String name) {
        this.name = name;
        gameObjects = new ArrayList<>();
    }

    public Scene addGameObject(GameObject gameObject){
        gameObject.setScene(this);
        gameObjects.add(gameObject);
        return this;
    }

    public GameObject createGameObject(String name)
    {
        GameObject object = new GameObject(name);
        addGameObject(object);
        return object;
    }

    public void init() {
        for (GameObject go : gameObjects) {
            if (go.isActive) {
                go.init();
            }
        }
    }

    public void update()
    {
        for (GameObject go : gameObjects) {
            if (go.isActive) {
                go.update();
            }
        }
    }

    public void render()
    {
        for (GameObject go : gameObjects) {
            if (go.isActive) {
                go.render();
            }
        }
    }

    public void dispose() {
        for (GameObject go : gameObjects) {
            go.dispose();
        }
    }
    
    public Window getWindow()
    {
        return this.window;
    }

    public void setWindow(Window window)
    {
        if(this.window != null)
            throw new IllegalStateException("Window already initialized!!");
        this.window = window;
    }

    public void setActiveCamera(CameraComponent cameraComponent){
        this.activeCamera = cameraComponent;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "name='" + name + '\'' +
                ", gameObjects=" + gameObjects +
                '}';
    }
}
