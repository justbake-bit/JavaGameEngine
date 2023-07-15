package com.justbake.GameEngine.core.scenes;


import com.justbake.GameEngine.core.scenes.gameObject.GameObject;

import java.util.ArrayList;
import java.util.List;

public final class Scene {

    private List<GameObject> gameObjects;
    private final String name;
    public Scene(String name) {
        this.name = name;
        gameObjects = new ArrayList<>();
    }

    public Scene addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
        return this;
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

    @Override
    public String toString() {
        return "Scene{" +
                "name='" + name + '\'' +
                ", gameObjects=" + gameObjects +
                '}';
    }
}
